package phoenix.record.media.avi;

import java.io.*;

/**
 * 字节流转换
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7 及以上
 *
 */
public class DataChunkOutputStream extends FilterOutputStream {

	protected long written;
	private boolean forwardFlushAndClose;

	public DataChunkOutputStream(OutputStream out) {
		this(out, true);
	}

	public DataChunkOutputStream(OutputStream out, boolean forwardFlushAndClose) {
		super(out);
		this.forwardFlushAndClose = forwardFlushAndClose;
	}

	public void writeType(String s) throws IOException {
		if (s.length() != 4) {
			throw new IllegalArgumentException("type string must have 4 characters");
		}
		try {
			out.write(s.getBytes("ASCII"), 0, 4);
			incCount(4);
		} catch (UnsupportedEncodingException e) {
			throw new InternalError(e.toString());
		}
	}

	public final void writeByte(int v) throws IOException {
		out.write(v);
		incCount(1);
	}

	@Override
	public synchronized void write(byte b[], int off, int len) throws IOException {
		out.write(b, off, len);
		incCount(len);
	}

	@Override
	public synchronized void write(int b) throws IOException {
		out.write(b);
		incCount(1);
	}

	public void writeInt(int v) throws IOException {
		out.write((v >>> 0) & 0xff);
		out.write((v >>> 8) & 0xff);
		out.write((v >>> 16) & 0xff);
		out.write((v >>> 24) & 0xff);
		incCount(4);
	}

	public void writeUInt(long v) throws IOException {
		out.write((int) ((v >>> 0) & 0xff));
		out.write((int) ((v >>> 8) & 0xff));
		out.write((int) ((v >>> 16) & 0xff));
		out.write((int) ((v >>> 24) & 0xff));
		incCount(4);
	}

	public void writeShort(int v) throws IOException {
		out.write((int) ((v >>> 0) & 0xff));
		out.write((int) ((v >> 8) & 0xff));
		incCount(2);
	}

	public void writeShorts(short[] v, int off, int len) throws IOException {
		for (int i = off; i < off + len; i++) {
			out.write((int) ((v[i] >>> 0) & 0xff));
			out.write((int) ((v[i] >> 8) & 0xff));
		}
		incCount(len * 2);
	}

	public void writeInts24(int[] v, int off, int len) throws IOException {
		for (int i = off; i < off + len; i++) {
			out.write((int) ((v[i] >>> 0) & 0xff));
			out.write((int) ((v[i] >> 8) & 0xff));
			out.write((int) ((v[i] >> 16) & 0xff));
		}
		incCount(len * 3);
	}

	public void writeLong(long v) throws IOException {
		out.write((int) (v >>> 0) & 0xff);
		out.write((int) (v >>> 8) & 0xff);
		out.write((int) (v >>> 16) & 0xff);
		out.write((int) (v >>> 24) & 0xff);
		out.write((int) (v >>> 32) & 0xff);
		out.write((int) (v >>> 40) & 0xff);
		out.write((int) (v >>> 48) & 0xff);
		out.write((int) (v >>> 56) & 0xff);
		incCount(8);
	}

	public void writeUShort(int v) throws IOException {
		out.write((int) ((v >>> 0) & 0xff));
		out.write((int) ((v >> 8) & 0xff));
		incCount(2);
	}

	protected void incCount(int value) {
		long temp = written + value;
		if (temp < 0) {
			temp = Long.MAX_VALUE;
		}
		written = temp;
	}

	public final long size() {
		return written;
	}

	public void clearCount() {
		written = 0;
	}

	@Override
	public void close() throws IOException {
		if (forwardFlushAndClose) {
			super.close();
		}
	}

	@Override
	public void flush() throws IOException {
		if (forwardFlushAndClose) {
			super.flush();
		}
	}
}
