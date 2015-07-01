package phoenix.record.media.avi;

import static java.lang.Math.max;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import phoenix.record.media.Buffer;
import phoenix.record.media.MovieWriter;
import phoenix.record.media.VideoFormat;
import phoenix.record.media.jpeg.JPEGCodec;
import phoenix.record.media.png.PNGCodec;

/**
 * AVI格式的视频写入，根据AVI格式的视频编码，<br>
 * 将内存中暂存的图片字节流按AVI格式编码写入，生成AVI格式视频文件<br>
 * <em>编写日期：2014年2月10日 13:21</em>
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7 及以上
 *
 */
public class AVIWriter extends AbstractAVIStream implements MovieWriter {

	public final static VideoFormat VIDEO_RAW = new VideoFormat(VideoFormat.AVI_DIB);
	public final static VideoFormat VIDEO_JPEG = new VideoFormat(VideoFormat.AVI_MJPG);
	public final static VideoFormat VIDEO_SCREEN_CAPTURE = new VideoFormat(VideoFormat.AVI_TECHSMITH_SCREEN_CAPTURE);

	private static enum States {
		STARTED, FINISHED, CLOSED;
	}

	private States state = States.FINISHED;
	private CompositeChunk aviChunk;
	private CompositeChunk moviChunk;
	FixedSizeDataChunk avihChunk;

	public AVIWriter(File file) throws IOException {
		if (file.exists()) {
			file.delete();
		}
		this.out = new FileImageOutputStream(file);
		this.streamOffset = 0;
	}

	public AVIWriter(ImageOutputStream out) throws IOException {
		this.out = out;
		this.streamOffset = out.getStreamPosition();
	}

	public int addVideoTrack(VideoFormat format, long timeScale, long frameRate, int width, int height, int depth,
			int syncInterval) throws IOException {
		return addVideoTrack(format.getEncoding(), timeScale, frameRate, width, height, depth, syncInterval);
	}

	public int addVideoTrack(VideoFormat format, long timeScale, long frameRate, int syncInterval) throws IOException {
		return addVideoTrack(format.getEncoding(), timeScale, frameRate, format.getWidth(), format.getHeight(),
				format.getDepth(), syncInterval);
	}

	public int addVideoTrack(VideoFormat format, long timeScale, long frameRate, int width, int height)
			throws IOException {
		return addVideoTrack(format.getEncoding(), timeScale, frameRate, width, height, 24, 24);
	}

	public int addVideoTrack(String fourCC, long timeScale, long frameRate, int width, int height, int depth,
			int syncInterval) throws IOException {
		VideoTrack vt = new VideoTrack(tracks.size(), fourCC);
		vt.videoFormat = new VideoFormat(fourCC, byte[].class, width, height, depth);
		vt.timeScale = timeScale;
		vt.frameRate = frameRate;
		vt.syncInterval = syncInterval;
		vt.rcFrame = new Rectangle(0, 0, width, height);

		vt.samples = new LinkedList<Sample>();

		if (vt.videoFormat.getDepth() == 4) {
			byte[] gray = new byte[16];
			for (int i = 0; i < gray.length; i++) {
				gray[i] = (byte) ((i << 4) | i);
			}
			vt.palette = new IndexColorModel(4, 16, gray, gray, gray);
		} else if (vt.videoFormat.getDepth() == 8) {
			byte[] gray = new byte[256];
			for (int i = 0; i < gray.length; i++) {
				gray[i] = (byte) i;
			}
			vt.palette = new IndexColorModel(8, 256, gray, gray, gray);
		}
		createCodec(vt);

		tracks.add(vt);
		return tracks.size() - 1;
	}

	public void setPalette(int track, IndexColorModel palette) {
		((VideoTrack) tracks.get(track)).palette = palette;
	}

	public void setCompressionQuality(int track, float newValue) {
		VideoTrack vt = (VideoTrack) tracks.get(track);
		vt.videoQuality = newValue;
		if (vt.codec != null) {
			vt.codec.setQuality(newValue);
		}
	}

	public float getVideoCompressionQuality(int track) {
		return ((VideoTrack) tracks.get(track)).videoQuality;
	}

	public void setVideoDimension(int track, int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("width and height must be greater zero.");
		}
		VideoTrack vt = (VideoTrack) tracks.get(track);
		vt.videoFormat = new VideoFormat(vt.videoFormat.getEncoding(), byte[].class, width, height,
				vt.videoFormat.getDepth());
	}

	public Dimension getVideoDimension(int track) {
		VideoTrack vt = (VideoTrack) tracks.get(track);
		VideoFormat fmt = vt.videoFormat;
		return new Dimension(fmt.getWidth(), fmt.getHeight());
	}

	private void ensureStarted() throws IOException {
		if (state != States.STARTED) {
			writeProlog();
			state = States.STARTED;
		}
	}

	@Override
	public void writeFrame(int track, BufferedImage image, long duration) throws IOException {
		ensureStarted();
		VideoTrack vt = (VideoTrack) tracks.get(track);
		if (vt.codec == null) {
			throw new UnsupportedOperationException("No codec for this video format.");
		}
		VideoFormat fmt = vt.videoFormat;
		if (fmt.getWidth() != image.getWidth() || fmt.getHeight() != image.getHeight()) {
			throw new IllegalArgumentException("Dimensions of image[" + vt.samples.size() + "] (width="
					+ image.getWidth() + ", height=" + image.getHeight() + ") differs from image[0] (width="
					+ fmt.getWidth() + ", height=" + fmt.getHeight());
		}
		{
			long offset = getRelativeStreamPosition();
			switch (fmt.getDepth()) {
			case 4: {
				IndexColorModel imgPalette = (IndexColorModel) image.getColorModel();
				int[] imgRGBs = new int[16];
				imgPalette.getRGBs(imgRGBs);
				int[] previousRGBs = new int[16];
				if (vt.previousPalette == null) {
					vt.previousPalette = vt.palette;
				}
				vt.previousPalette.getRGBs(previousRGBs);
				if (!Arrays.equals(imgRGBs, previousRGBs)) {
					vt.previousPalette = imgPalette;
					DataChunk paletteChangeChunk = new DataChunk(vt.twoCC + "pc");
					int first = 0;
					int last = imgPalette.getMapSize() - 1;
					DataChunkOutputStream pOut = paletteChangeChunk.getOutputStream();
					pOut.writeByte(first);
					pOut.writeByte(last - first + 1);
					pOut.writeShort(0);

					for (int i = first; i <= last; i++) {
						pOut.writeByte((imgRGBs[i] >>> 16) & 0xff); 
						pOut.writeByte((imgRGBs[i] >>> 8) & 0xff); 
						pOut.writeByte(imgRGBs[i] & 0xff);
						pOut.writeByte(0);
					}
					moviChunk.add(paletteChangeChunk);
					paletteChangeChunk.finish();
					long length = getRelativeStreamPosition() - offset;
					vt.samples.add(new Sample(paletteChangeChunk.chunkType, 0, offset, length - 8, false));
					offset = getRelativeStreamPosition();
				}
				break;
			}
			case 8: {
				IndexColorModel imgPalette = (IndexColorModel) image.getColorModel();
				int[] imgRGBs = new int[256];
				imgPalette.getRGBs(imgRGBs);
				int[] previousRGBs = new int[256];
				if (vt.previousPalette == null) {
					vt.previousPalette = vt.palette;
				}
				vt.previousPalette.getRGBs(previousRGBs);
				if (!Arrays.equals(imgRGBs, previousRGBs)) {
					vt.previousPalette = imgPalette;
					DataChunk paletteChangeChunk = new DataChunk(vt.twoCC + "pc");
					int first = 0;
					int last = imgPalette.getMapSize() - 1;
					DataChunkOutputStream pOut = paletteChangeChunk.getOutputStream();
					pOut.writeByte(first);
					pOut.writeByte(last - first + 1);
					pOut.writeShort(0);
					for (int i = first; i <= last; i++) {
						pOut.writeByte((imgRGBs[i] >>> 16) & 0xff);
						pOut.writeByte((imgRGBs[i] >>> 8) & 0xff);
						pOut.writeByte(imgRGBs[i] & 0xff);
						pOut.writeByte(0);
					}
					moviChunk.add(paletteChangeChunk);
					paletteChangeChunk.finish();
					long length = getRelativeStreamPosition() - offset;
					vt.samples.add(new Sample(paletteChangeChunk.chunkType, 0, offset, length - 8, false));
					offset = getRelativeStreamPosition();
				}
				break;
			}
			}
		}
		{
			if (vt.outputBuffer == null) {
				vt.outputBuffer = new Buffer();
			}
			boolean isSync = vt.syncInterval == 0 ? false : vt.samples.size() % vt.syncInterval == 0;
			Buffer inputBuffer = new Buffer();
			inputBuffer.flags = (isSync) ? Buffer.FLAG_KEY_FRAME : 0;
			inputBuffer.data = image;
			vt.codec.process(inputBuffer, vt.outputBuffer);
			if (vt.outputBuffer.flags == Buffer.FLAG_DISCARD) {
				return;
			}
			isSync = (vt.outputBuffer.flags & Buffer.FLAG_KEY_FRAME) != 0;
			long offset = getRelativeStreamPosition();
			DataChunk videoFrameChunk = new DataChunk(isSync ? vt.twoCC + "db" : vt.twoCC + "dc");
			moviChunk.add(videoFrameChunk);
			videoFrameChunk.getOutputStream().write((byte[]) vt.outputBuffer.data, vt.outputBuffer.offset, vt.outputBuffer.length);
			videoFrameChunk.finish();
			long length = getRelativeStreamPosition() - offset;
			vt.samples.add(new Sample(videoFrameChunk.chunkType, (int) vt.frameRate, offset, length - 8, isSync));
			if (getRelativeStreamPosition() > 1L << 32) {
				throw new IOException("AVI file is larger than 4 GB");
			}
		}
	}

	private void createCodec(VideoTrack vt) {
		VideoFormat fmt = vt.videoFormat;
		String enc = fmt.getEncoding();
		if (enc.equals(VideoFormat.AVI_MJPG)) {
			vt.codec = new JPEGCodec();
		} else if (enc.equals(VideoFormat.AVI_PNG)) {
			vt.codec = new PNGCodec();
		} else if (enc.equals(VideoFormat.AVI_DIB)) {
			vt.codec = new DIBCodec();
		} else if (enc.equals(VideoFormat.AVI_RLE)) {
			vt.codec = new RunLengthCodec();
		} else if (enc.equals(VideoFormat.AVI_TECHSMITH_SCREEN_CAPTURE)) {
			vt.codec = new TechSmithCodec();
		}
		vt.codec.setInputFormat(new VideoFormat(enc, BufferedImage.class, fmt.getWidth(), fmt.getHeight(), fmt.getDepth()));
		vt.codec.setOutputFormat(new VideoFormat(enc, byte[].class, fmt.getWidth(), fmt.getHeight(), fmt.getDepth()));
		vt.codec.setQuality(vt.videoQuality);
	}
	
	public void writeFrame(int track, File file) throws IOException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			writeFrame(track, in);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public void writeFrame(int track, InputStream in) throws IOException {
		ensureStarted();
		VideoTrack vt = (VideoTrack) tracks.get(track);
		DataChunk videoFrameChunk = new DataChunk(vt.videoFormat.getEncoding().equals(VideoFormat.AVI_DIB) ? vt.twoCC + "db" : vt.twoCC + "dc");
		moviChunk.add(videoFrameChunk);
		OutputStream mdatOut = videoFrameChunk.getOutputStream();
		long offset = getRelativeStreamPosition();
		byte[] buf = new byte[512];
		int len;
		while ((len = in.read(buf)) != -1) {
			mdatOut.write(buf, 0, len);
		}
		long length = getRelativeStreamPosition() - offset;
		videoFrameChunk.finish();
		vt.samples.add(new Sample(videoFrameChunk.chunkType, (int) vt.frameRate, offset, length - 8, true));
		if (getRelativeStreamPosition() > 1L << 32) {
			throw new IOException("AVI file is larger than 4 GB");
		}
	}

	@Override
	public void writeSample(int track, byte[] data, int off, int len, long duration, boolean isSync) throws IOException {
		ensureStarted();
		Track t = tracks.get(track);
		DataChunk dc;
		if (t instanceof VideoTrack) {
			VideoTrack vt = (VideoTrack) t;
			dc = new DataChunk(vt.videoFormat.getEncoding().equals(VideoFormat.AVI_DIB) ? vt.twoCC + "db" : vt.twoCC + "dc");
		} else {
			throw new UnsupportedOperationException("Not yet implemented");
		}
		moviChunk.add(dc);
		OutputStream mdatOut = dc.getOutputStream();
		long offset = getRelativeStreamPosition();
		mdatOut.write(data, off, len);
		long length = getRelativeStreamPosition() - offset;
		dc.finish();
		t.samples.add(new Sample(dc.chunkType, (int) t.frameRate, offset, length - 8, true));
		if (getRelativeStreamPosition() > 1L << 32) {
			throw new IOException("AVI file is larger than 4 GB");
		}
	}

	@Override
	public void writeSamples(int track, int sampleCount, byte[] data, int off, int len, long sampleDuration, boolean isSync) throws IOException {
		for (int i = 0; i < sampleCount; i++) {
			writeSample(track, data, off, len / sampleCount, sampleDuration, isSync);
			off += len / sampleCount;
		}
	}

	@Override
	public void close() throws IOException {
		if (state == States.STARTED) {
			finish();
		}
		if (state != States.CLOSED) {
			out.close();
			state = States.CLOSED;
		}
	}

	public void finish() throws IOException {
		ensureOpen();
		if (state != States.FINISHED) {
			for (Track tr : tracks) {
				if (tr instanceof VideoTrack) {
					VideoTrack vt = (VideoTrack) tr;
					VideoFormat fmt = vt.videoFormat;
					if (fmt.getWidth() == -1 || fmt.getHeight() == -1) {
						throw new IllegalStateException("image width and height must be specified");
					}
				}
			}
			moviChunk.finish();
			writeEpilog();
			state = States.FINISHED;
		}
	}

	private void ensureOpen() throws IOException {
		if (state == States.CLOSED) {
			throw new IOException("Stream closed");
		}
	}

	@Override
	public boolean isVFRSupported() {
		return false;
	}

	@Override
	public boolean isDataLimitReached() {
		try {
			return getRelativeStreamPosition() > (long) (1.8 * 1024 * 1024 * 1024);
		} catch (IOException ex) {
			return true;
		}
	}

	private void writeProlog() throws IOException {
		aviChunk = new CompositeChunk("RIFF", "AVI ");
		CompositeChunk hdrlChunk = new CompositeChunk("LIST", "hdrl");
		aviChunk.add(hdrlChunk);
		avihChunk = new FixedSizeDataChunk("avih", 56);
		avihChunk.seekToEndOfChunk();
		hdrlChunk.add(avihChunk);
		CompositeChunk strlChunk = new CompositeChunk("LIST", "strl");
		hdrlChunk.add(strlChunk);
		for (Track tr : tracks) {
			if (tr instanceof VideoTrack) {
				VideoTrack vt = (VideoTrack) tr;
				vt.strhChunk = new FixedSizeDataChunk("strh", 56);
				vt.strhChunk.seekToEndOfChunk();
				strlChunk.add(vt.strhChunk);
				vt.strfChunk = new FixedSizeDataChunk("strf", vt.palette == null ? 40 : 40 + vt.palette.getMapSize() * 4);
				vt.strfChunk.seekToEndOfChunk();
				strlChunk.add(vt.strfChunk);
			} else {
				throw new UnsupportedOperationException("Track type not implemented yet.");
			}
		}
		moviChunk = new CompositeChunk("LIST", "movi");
		aviChunk.add(moviChunk);

	}

	private void writeEpilog() throws IOException {
		long largestBufferSize = 0;
		long duration = 0;
		for (Track tr : tracks) {
			if (tr instanceof VideoTrack) {
				VideoTrack vt = (VideoTrack) tr;
				long trackDuration = 0;
				for (Sample s : vt.samples) {
					trackDuration += s.duration;
				}
				duration = max(duration, trackDuration);
				for (Sample s : vt.samples) {
					if (s.length > largestBufferSize) {
						largestBufferSize = s.length;
					}
				}
			}
		}
		DataChunkOutputStream d;
		DataChunk idx1Chunk = new DataChunk("idx1");
		aviChunk.add(idx1Chunk);
		d = idx1Chunk.getOutputStream();
		long moviListOffset = moviChunk.offset + 8;
		for (Track tr : tracks) {
			if (tr instanceof VideoTrack) {
				VideoTrack vt = (VideoTrack) tr;
				for (Sample f : vt.samples) {
					d.writeType(f.chunkType);
					d.writeUInt((f.chunkType.endsWith("pc") ? 0x100 : 0x0) | (f.isSync ? 0x10 : 0x0));
					d.writeUInt(f.offset - moviListOffset); 
					d.writeUInt(f.length); 
				}
			} else {
				throw new UnsupportedOperationException("Track type not yet implemented.");
			}
		}
		idx1Chunk.finish();
		avihChunk.seekToStartOfData();
		d = avihChunk.getOutputStream();
		Track tt = tracks.get(0);
		d.writeUInt((1000000L * (long) tt.timeScale) / (long) tt.frameRate); 
		d.writeUInt(0);
		d.writeUInt(0x10 | 0x20); 
		long dwTotalFrames = 0;
		for (Track t : tracks) {
			dwTotalFrames += t.samples.size();
		}
		d.writeUInt(dwTotalFrames); 
		d.writeUInt(0); 
		d.writeUInt(1);
		d.writeUInt(largestBufferSize); 
		{
			VideoTrack vt = null;
			for (Track t : tracks) {
				if (t instanceof VideoTrack) {
					vt = (VideoTrack) t;
					break;
				}
			}
			VideoFormat fmt = vt.videoFormat;
			d.writeUInt(vt == null ? 0 : fmt.getWidth());
			d.writeUInt(vt == null ? 0 : fmt.getHeight()); 
		}
		d.writeUInt(0);
		d.writeUInt(0);
		d.writeUInt(0);
		d.writeUInt(0);
		for (Track tr : tracks) {
			
			tr.strhChunk.seekToStartOfData();
			d = tr.strhChunk.getOutputStream();
			d.writeType(tr.mediaType.fccType); 
			d.writeType(tr.fourCC);
			
			if (tr instanceof VideoTrack && ((VideoTrack) tr).videoFormat.getDepth() <= 8) {
				d.writeUInt(0x00010000);
			} else {
				d.writeUInt(0); 
			}
			d.writeUShort(0); 
			d.writeUShort(0);
			d.writeUInt(0);
			d.writeUInt(tr.timeScale); 
			d.writeUInt(tr.frameRate);
			d.writeUInt(0);
			d.writeUInt(tr.samples.size()); 
			long dwSuggestedBufferSize = 0;
			for (Sample s : tr.samples) {
				if (s.length > dwSuggestedBufferSize) {
					dwSuggestedBufferSize = s.length;
				}
			}
			d.writeUInt(dwSuggestedBufferSize); 
			d.writeInt(-1); 
			d.writeUInt(0); 
			d.writeUShort(tr instanceof VideoTrack ? ((VideoTrack) tr).rcFrame.x : 0);
			d.writeUShort(tr instanceof VideoTrack ? ((VideoTrack) tr).rcFrame.y : 0);
			d.writeUShort(tr instanceof VideoTrack ? ((VideoTrack) tr).rcFrame.x + ((VideoTrack) tr).rcFrame.width : 0); 
			d.writeUShort(tr instanceof VideoTrack ? ((VideoTrack) tr).rcFrame.y + ((VideoTrack) tr).rcFrame.height : 0); 
			{
				VideoTrack vt = (VideoTrack) tr;
				tr.strfChunk.seekToStartOfData();
				d = tr.strfChunk.getOutputStream();
				d.writeUInt(40); 
				d.writeInt(vt.videoFormat.getWidth()); 
				d.writeInt(vt.videoFormat.getHeight());
				d.writeShort(1); 
				d.writeShort(vt.videoFormat.getDepth()); 
				String enc = vt.videoFormat.getEncoding();
				if (enc.equals(VideoFormat.AVI_DIB)) {
					d.writeInt(0); 
				} else if (enc.equals(VideoFormat.AVI_RLE)) {
					if (vt.videoFormat.getDepth() == 8) {
						d.writeInt(1);
					} else if (vt.videoFormat.getDepth() == 4) {
						d.writeInt(2);
					} else {
						throw new UnsupportedOperationException("RLE only supports 4-bit and 8-bit images");
					}
				} else {
					d.writeType(vt.videoFormat.getEncoding());
				}
				if (enc.equals(VideoFormat.AVI_DIB)) {
					d.writeInt(0); 
				} else {
					VideoFormat fmt = vt.videoFormat;
					if (fmt.getDepth() == 4) {
						d.writeInt(fmt.getWidth() * fmt.getHeight() / 2);
					} else {
						int bytesPerPixel = Math.max(1, fmt.getDepth() / 8);
						d.writeInt(fmt.getWidth() * fmt.getHeight() * bytesPerPixel);
					}
				}
				d.writeInt(0);
				d.writeInt(0); 
				d.writeInt(vt.palette == null ? 0 : vt.palette.getMapSize()); 
				d.writeInt(0);
				if (vt.palette != null) {
					for (int i = 0, n = vt.palette.getMapSize(); i < n; ++i) {
						d.write(vt.palette.getBlue(i));
						d.write(vt.palette.getGreen(i));
						d.write(vt.palette.getRed(i));
						d.write(0);
					}
				}
			}
		}
		aviChunk.finish();
	}
}
