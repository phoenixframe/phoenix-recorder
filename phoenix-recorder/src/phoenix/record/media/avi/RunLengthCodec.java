package phoenix.record.media.avi;

import static java.lang.Math.min;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

import javax.imageio.stream.ImageOutputStream;

import phoenix.record.media.AbstractVideoCodec;
import phoenix.record.media.Buffer;
import phoenix.record.media.Format;
import phoenix.record.media.VideoFormat;
import phoenix.record.media.io.ByteArrayImageOutputStream;

/**
 * 
 * @author mengfeiyang
 *
 */
public class RunLengthCodec extends AbstractVideoCodec {

	private byte[] previousPixels;

	@Override
	public Format setInputFormat(Format f) {
		if (f instanceof VideoFormat) {
			VideoFormat vf = (VideoFormat) f;
			if (BufferedImage.class.isAssignableFrom(vf.getDataClass())) {
				return super.setInputFormat(new VideoFormat(VideoFormat.IMAGE, vf.getDataClass(), vf.getWidth(), vf.getHeight(), 8));
			}
		}
		return super.setInputFormat(null);
	}

	@Override
	public Format setOutputFormat(Format f) {
		if (f instanceof VideoFormat) {
			VideoFormat vf = (VideoFormat) f;
			return super.setOutputFormat(new VideoFormat(VideoFormat.AVI_RLE, byte[].class, vf.getWidth(), vf
					.getHeight(), 8));
		}
		return super.setOutputFormat(null);
	}

	@Override
	public void process(Buffer in, Buffer out) {
		if ((in.flags & Buffer.FLAG_DISCARD) != 0) {
			out.flags = Buffer.FLAG_DISCARD;
			return;
		}
		out.format = outputFormat;
		ByteArrayImageOutputStream tmp;
		if (out.data instanceof byte[]) {
			tmp = new ByteArrayImageOutputStream((byte[]) out.data);
		} else {
			tmp = new ByteArrayImageOutputStream();
		}
		VideoFormat vf = (VideoFormat) outputFormat;
		Rectangle r;
		int scanlineStride;
		if (in.data instanceof BufferedImage) {
			BufferedImage image = (BufferedImage) in.data;
			WritableRaster raster = image.getRaster();
			scanlineStride = raster.getSampleModel().getWidth();
			r = raster.getBounds();
			r.x -= raster.getSampleModelTranslateX();
			r.y -= raster.getSampleModelTranslateY();
		} else {
			r = new Rectangle(0, 0, vf.getWidth(), vf.getHeight());
			scanlineStride = vf.getWidth();
		}
		int offset = r.x + r.y * scanlineStride;
		try {
			byte[] pixels = getIndexed8(in);
			if (pixels == null) {
				throw new UnsupportedOperationException("Can not process buffer " + in);
			}
			if ((in.flags & Buffer.FLAG_KEY_FRAME) != 0 || previousPixels == null) {
				writeKey8(tmp, pixels, r.width, r.height, offset, scanlineStride);
				out.flags = Buffer.FLAG_KEY_FRAME;
			} else {
				writeDelta8(tmp, pixels, previousPixels, r.width, r.height, offset, scanlineStride);
				out.flags = 0;
			}
			out.data = tmp.getBuffer();
			out.offset = 0;
			out.length = (int) tmp.getStreamPosition();
			if (previousPixels == null) {
				previousPixels = pixels.clone();
			} else {
				System.arraycopy(pixels, 0, previousPixels, 0, pixels.length);
			}
			return;
		} catch (IOException ex) {
			ex.printStackTrace();
			out.flags = Buffer.FLAG_DISCARD;
			return;
		}
	}

	public void writeKey8(OutputStream out, byte[] data, int width, int height, int offset, int scanlineStride)
			throws IOException {
		ByteArrayImageOutputStream buf = new ByteArrayImageOutputStream(data.length);
		writeKey8(buf, data, width, height, offset, scanlineStride);
		buf.toOutputStream(out);
	}

	public void writeKey8(ImageOutputStream out, byte[] data, int width, int height, int offset, int scanlineStride) throws IOException {
		out.setByteOrder(ByteOrder.LITTLE_ENDIAN);
		int ymax = offset + height * scanlineStride;
		int upsideDown = ymax - scanlineStride + offset;
		for (int y = offset; y < ymax; y += scanlineStride) {
			int xy = upsideDown - y;
			int xymax = xy + width;
			int literalCount = 0;
			int repeatCount = 0;
			for (; xy < xymax; ++xy) {
				byte v = data[xy];
				for (repeatCount = 0; xy < xymax && repeatCount < 255; ++xy, ++repeatCount) {
					if (data[xy] != v) {
						break;
					}
				}
				xy -= repeatCount;
				if (repeatCount < 3) {
					literalCount++;
					if (literalCount == 254) {
						out.write(0);
						out.write(literalCount); 
						out.write(data, xy - literalCount + 1, literalCount);
						literalCount = 0;
					}
				} else {
					if (literalCount > 0) {
						if (literalCount < 3) {
							for (; literalCount > 0; --literalCount) {
								out.write(1); 
								out.write(data[xy - literalCount]);
							}
						} else {
							out.write(0);
							out.write(literalCount);
							out.write(data, xy - literalCount, literalCount);
							if (literalCount % 2 == 1) {
								out.write(0);
							}
							literalCount = 0;
						}
					}
					out.write(repeatCount); 
					out.write(v);
					xy += repeatCount - 1;
				}
			}
			if (literalCount > 0) {
				if (literalCount < 3) {
					for (; literalCount > 0; --literalCount) {
						out.write(1); 
						out.write(data[xy - literalCount]);
					}
				} else {
					out.write(0);
					out.write(literalCount);
					out.write(data, xy - literalCount, literalCount);
					if (literalCount % 2 == 1) {
						out.write(0); 
					}
				}
				literalCount = 0;
			}
			out.write(0);
			out.write(0x0000);
		}
		out.write(0);
		out.write(0x0001);
	}

	public void writeDelta8(OutputStream out, byte[] data, byte[] prev, int width, int height, int offset, int scanlineStride) throws IOException {
		ByteArrayImageOutputStream buf = new ByteArrayImageOutputStream(data.length);
		writeDelta8(buf, data, prev, width, height, offset, scanlineStride);
		buf.toOutputStream(out);
	}

	public void writeDelta8(ImageOutputStream out, byte[] data, byte[] prev, int width, int height, int offset, int scanlineStride) throws IOException {
		out.setByteOrder(ByteOrder.LITTLE_ENDIAN);
		int ymax = offset + height * scanlineStride;
		int upsideDown = ymax - scanlineStride + offset;
		int verticalOffset = 0;
		for (int y = offset; y < ymax; y += scanlineStride) {
			int xy = upsideDown - y;
			int xymax = xy + width;
			int skipCount = 0;
			for (; xy < xymax; ++xy, ++skipCount) {
				if (data[xy] != prev[xy]) {
					break;
				}
			}
			if (skipCount == width) {
				++verticalOffset;
				continue;
			}
			while (verticalOffset > 0 || skipCount > 0) {
				if (verticalOffset == 1 && skipCount == 0) {
					out.write(0x00);
					out.write(0x00);
					verticalOffset = 0;
				} else {
					out.write(0x00);
					out.write(0x02); 
					out.write(min(255, skipCount)); 
					out.write(min(255, verticalOffset));
					skipCount -= min(255, skipCount);
					verticalOffset -= min(255, verticalOffset);
				}
			}
			int literalCount = 0;
			int repeatCount = 0;
			for (; xy < xymax; ++xy) {
				for (skipCount = 0; xy < xymax; ++xy, ++skipCount) {
					if (data[xy] != prev[xy]) {
						break;
					}
				}
				xy -= skipCount;
				byte v = data[xy];
				for (repeatCount = 0; xy < xymax && repeatCount < 255; ++xy, ++repeatCount) {
					if (data[xy] != v) {
						break;
					}
				}
				xy -= repeatCount;
				if (skipCount < 4 && xy + skipCount < xymax && repeatCount < 3) {
					literalCount++;
				} else {
					while (literalCount > 0) {
						if (literalCount < 3) {
							out.write(1);
							out.write(data[xy - literalCount]);
							literalCount--;
						} else {
							int literalRun = min(254, literalCount);
							out.write(0);
							out.write(literalRun);
							out.write(data, xy - literalCount, literalRun);
							if (literalRun % 2 == 1) {
								out.write(0);
							}
							literalCount -= literalRun;
						}
					}
					if (xy + skipCount == xymax) {
						xy += skipCount - 1;
					} else if (skipCount >= repeatCount) {
						while (skipCount > 0) {
							out.write(0);
							out.write(0x0002);
							out.write(min(255, skipCount));
							out.write(0);
							xy += min(255, skipCount);
							skipCount -= min(255, skipCount);
						}
						xy -= 1;
					} else {
						out.write(repeatCount);
						out.write(v);
						xy += repeatCount - 1;
					}
				}
			}
			while (literalCount > 0) {
				if (literalCount < 3) {
					out.write(1);
					out.write(data[xy - literalCount]);
					literalCount--;
				} else {
					int literalRun = min(254, literalCount);
					out.write(0);
					out.write(literalRun);
					out.write(data, xy - literalCount, literalRun);
					if (literalRun % 2 == 1) {
						out.write(0);
					}
					literalCount -= literalRun;
				}
			}
			out.write(0);
			out.write(0x0000);
		}
		out.write(0);
		out.write(0x0001);
	}
}
