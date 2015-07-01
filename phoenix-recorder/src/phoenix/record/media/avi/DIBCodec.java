package phoenix.record.media.avi;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.OutputStream;

import phoenix.record.media.AbstractVideoCodec;
import phoenix.record.media.Buffer;
import phoenix.record.media.Format;
import phoenix.record.media.VideoFormat;
import phoenix.record.media.io.SeekableByteArrayOutputStream;

public class DIBCodec extends AbstractVideoCodec {

	@Override
	public Format setInputFormat(Format f) {
		if (f instanceof VideoFormat) {
			VideoFormat vf = (VideoFormat) f;
			int depth = vf.getDepth();
			if (depth <= 4) {
				depth = 4;
			} else if (depth <= 8) {
				depth = 8;
			} else {
				depth = 24;
			}
			if (BufferedImage.class.isAssignableFrom(vf.getDataClass())) {
				return super.setInputFormat(new VideoFormat(VideoFormat.IMAGE, vf.getDataClass(), vf.getWidth(), vf.getHeight(), depth));
			}
		}
		return super.setInputFormat(null);
	}

	@Override
	public Format setOutputFormat(Format f) {
		if (f instanceof VideoFormat) {
			VideoFormat vf = (VideoFormat) f;
			int depth = vf.getDepth();
			if (depth <= 4) {
				depth = 4;
			} else if (depth <= 8) {
				depth = 8;
			} else {
				depth = 24;
			}
			return super.setOutputFormat(new VideoFormat(VideoFormat.AVI_DIB, byte[].class, vf.getWidth(), vf.getHeight(), depth));
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
		SeekableByteArrayOutputStream tmp;
		if (out.data instanceof byte[]) {
			tmp = new SeekableByteArrayOutputStream((byte[]) out.data);
		} else {
			tmp = new SeekableByteArrayOutputStream();
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
		try {
			switch (vf.getDepth()) {
			case 4: {
				byte[] pixels = getIndexed8(in);
				if (pixels == null) {
					out.flags = Buffer.FLAG_DISCARD;
					return;
				}
				writeKey4(tmp, pixels, r.width, r.height, r.x + r.y * scanlineStride, scanlineStride);
				break;
			}
			case 8: {
				byte[] pixels = getIndexed8(in);
				if (pixels == null) {
					out.flags = Buffer.FLAG_DISCARD;
					return;
				}
				writeKey8(tmp, pixels, r.width, r.height, r.x + r.y * scanlineStride, scanlineStride);
				break;
			}
			case 24: {
				int[] pixels = getRGB24(in);
				if (pixels == null) {
					out.flags = Buffer.FLAG_DISCARD;
					return;
				}
				writeKey24(tmp, pixels, r.width, r.height, r.x + r.y * scanlineStride, scanlineStride);
				break;
			}
			default:
				out.flags = Buffer.FLAG_DISCARD;
				return;
			}
			out.flags = Buffer.FLAG_KEY_FRAME;
			out.data = tmp.getBuffer();
			out.offset = 0;
			out.length = (int) tmp.getStreamPosition();
			return;
		} catch (IOException ex) {
			ex.printStackTrace();
			out.flags = Buffer.FLAG_DISCARD;
			return;
		}
	}

	public void writeKey4(OutputStream out, byte[] pixels, int width, int height, int offset, int scanlineStride) throws IOException {
		byte[] bytes = new byte[width];
		for (int y = (height - 1) * scanlineStride; y >= 0; y -= scanlineStride) { 
			for (int x = offset, xx = 0, n = offset + width; x < n; x += 2, ++xx) {
				bytes[xx] = (byte) (((pixels[y + x] & 0xf) << 4) | (pixels[y + x + 1] & 0xf));
			}
			out.write(bytes);
		}
	}

	public void writeKey8(OutputStream out, byte[] pixels, int width, int height, int offset, int scanlineStride) throws IOException {
		for (int y = (height - 1) * scanlineStride; y >= 0; y -= scanlineStride) {
			out.write(pixels, y + offset, width);
		}
	}

	public void writeKey24(OutputStream out, int[] pixels, int width, int height, int offset, int scanlineStride) throws IOException {
		int w3 = width * 3;
		byte[] bytes = new byte[w3]; 
		for (int xy = (height - 1) * scanlineStride + offset; xy >= offset; xy -= scanlineStride) { 
			for (int x = 0, xp = 0; x < w3; x += 3, ++xp) {
				int p = pixels[xy + xp];
				bytes[x] = (byte) (p); 
				bytes[x + 1] = (byte) (p >> 8); 
				bytes[x + 2] = (byte) (p >> 16); 
			}
			out.write(bytes);
		}
	}
}
