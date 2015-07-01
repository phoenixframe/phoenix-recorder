package phoenix.record.media.avi;

import java.awt.Rectangle;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.stream.ImageOutputStream;

import phoenix.record.media.Buffer;
import phoenix.record.media.Codec;
import phoenix.record.media.VideoFormat;
import phoenix.record.media.io.ImageOutputStreamAdapter;

public abstract class AbstractAVIStream {

	protected ImageOutputStream out;
	protected long streamOffset;

	protected static enum MediaType {
		AUDIO("auds"), MIDI("mids"), TEXT("txts"), VIDEO("vids");
		protected String fccType;

		MediaType(String fourCC) {
			this.fccType = fourCC;
		}
	}

	protected ArrayList<Track> tracks = new ArrayList<Track>();

	protected long getRelativeStreamPosition() throws IOException {
		return out.getStreamPosition() - streamOffset;
	}

	protected void seekRelative(long newPosition) throws IOException {
		out.seek(newPosition + streamOffset);
	}

	protected static class Sample {

		String chunkType;
		long offset;
		long length;
		int duration;
		boolean isSync;
		
		public Sample(String chunkId, int duration, long offset, long length, boolean isSync) {
			this.chunkType = chunkId;
			this.duration = duration;
			this.offset = offset;
			this.length = length;
			this.isSync = isSync;
		}
	}
	
	protected abstract class Track {

		final MediaType mediaType;
		protected long timeScale = 1;
		protected long frameRate = 30;
		protected LinkedList<Sample> samples;
		protected int syncInterval = 30;
		protected String twoCC;
		protected String fourCC;

		public Track(int trackIndex, MediaType mediaType, String fourCC) {
			this.mediaType = mediaType;
			twoCC = "00" + Integer.toString(trackIndex);
			twoCC = twoCC.substring(twoCC.length() - 2);
			this.fourCC = fourCC;
		}

		FixedSizeDataChunk strhChunk;
		FixedSizeDataChunk strfChunk;
	}

	protected class VideoTrack extends Track {

		protected VideoFormat videoFormat;
		protected float videoQuality = 0.97f;
		protected IndexColorModel palette;
		protected IndexColorModel previousPalette;
		protected Object previousData;
		protected Codec codec;
		protected Buffer outputBuffer;
		protected Rectangle rcFrame;

		public VideoTrack(int trackIndex, String fourCC) {
			super(trackIndex, MediaType.VIDEO, fourCC);
		}
	}

	protected abstract class Chunk {

		protected String chunkType;
		protected long offset;
		
		public Chunk(String chunkType) throws IOException {
			this.chunkType = chunkType;
			offset = getRelativeStreamPosition();
		}
		
		public abstract void finish() throws IOException;
		
		public abstract long size();
	}

	protected class CompositeChunk extends Chunk {
		
		protected String compositeType;
		protected LinkedList<Chunk> children;
		protected boolean finished;

		public CompositeChunk(String compositeType, String chunkType) throws IOException {
			super(chunkType);
			this.compositeType = compositeType;
			out.writeLong(0);
			out.writeInt(0);
			children = new LinkedList<Chunk>();
		}

		public void add(Chunk child) throws IOException {
			if (children.size() > 0) {
				children.getLast().finish();
			}
			children.add(child);
		}

		@Override
		public void finish() throws IOException {
			if (!finished) {
				if (size() > 0xffffffffL) {
					throw new IOException("CompositeChunk \"" + chunkType + "\" is too large: " + size());
				}
				long pointer = getRelativeStreamPosition();
				seekRelative(offset);
				DataChunkOutputStream headerData = new DataChunkOutputStream(new ImageOutputStreamAdapter(out), false);
				headerData.writeType(compositeType);
				headerData.writeUInt(size() - 8);
				headerData.writeType(chunkType);
				for (Chunk child : children) {
					child.finish();
				}
				seekRelative(pointer);
				if (size() % 2 == 1) {
					out.writeByte(0); 
				}
				finished = true;
			}
		}

		@Override
		public long size() {
			long length = 12;
			for (Chunk child : children) {
				length += child.size() + child.size() % 2;
			}
			return length;
		}
	}

	protected class DataChunk extends Chunk {

		protected DataChunkOutputStream data;
		protected boolean finished;
		
		public DataChunk(String name) throws IOException {
			super(name);
			out.writeLong(0); 
			data = new DataChunkOutputStream(new ImageOutputStreamAdapter(out), false);
		}

		public DataChunkOutputStream getOutputStream() {
			if (finished) {
				throw new IllegalStateException("DataChunk is finished");
			}
			return data;
		}

		public long getOffset() {
			return offset;
		}

		@Override
		public void finish() throws IOException {
			if (!finished) {
				long sizeBefore = size();
				if (size() > 0xffffffffL) {
					throw new IOException("DataChunk \"" + chunkType + "\" is too large: " + size());
				}
				long pointer = getRelativeStreamPosition();
				seekRelative(offset);
				DataChunkOutputStream headerData = new DataChunkOutputStream(new ImageOutputStreamAdapter(out), false);
				headerData.writeType(chunkType);
				headerData.writeUInt(size() - 8);
				seekRelative(pointer);
				if (size() % 2 == 1) {
					out.writeByte(0);
				}
				finished = true;
				long sizeAfter = size();
				if (sizeBefore != sizeAfter) {
					System.err.println("size mismatch " + sizeBefore + ".." + sizeAfter);
				}
			}
		}

		@Override
		public long size() {
			return 8 + data.size();
		}
	}

	protected class FixedSizeDataChunk extends Chunk {

		protected DataChunkOutputStream data;
		protected boolean finished;
		protected long fixedSize;

		public FixedSizeDataChunk(String chunkType, long fixedSize) throws IOException {
			super(chunkType);
			this.fixedSize = fixedSize;
			data = new DataChunkOutputStream(new ImageOutputStreamAdapter(out), false);
			data.writeType(chunkType);
			data.writeUInt(fixedSize);
			data.clearCount();
			byte[] buf = new byte[(int) Math.min(512, fixedSize)];
			long written = 0;
			while (written < fixedSize) {
				data.write(buf, 0, (int) Math.min(buf.length, fixedSize - written));
				written += Math.min(buf.length, fixedSize - written);
			}
			if (fixedSize % 2 == 1) {
				out.writeByte(0);
			}
			seekToStartOfData();
		}

		public DataChunkOutputStream getOutputStream() {
			return data;
		}

		public long getOffset() {
			return offset;
		}

		public void seekToStartOfData() throws IOException {
			seekRelative(offset + 8);
			data.clearCount();
		}

		public void seekToEndOfChunk() throws IOException {
			seekRelative(offset + 8 + fixedSize + fixedSize % 2);
		}

		@Override
		public void finish() throws IOException {
			if (!finished) {
				finished = true;
			}
		}

		@Override
		public long size() {
			return 8 + fixedSize;
		}
	}
}
