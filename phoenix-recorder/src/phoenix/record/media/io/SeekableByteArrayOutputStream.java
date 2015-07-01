package phoenix.record.media.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import static java.lang.Math.*;

public class SeekableByteArrayOutputStream extends ByteArrayOutputStream {

	private int pos;
	
	public SeekableByteArrayOutputStream() {
		this(32);
	}

	public SeekableByteArrayOutputStream(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Negative initial size: " + size);
		}
		buf = new byte[size];
	}

	public SeekableByteArrayOutputStream(byte[] buf) {
		this.buf = buf;
	}

	@Override
	public synchronized void write(int b) {
		int newcount = max(pos + 1, count);
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		buf[pos++] = (byte) b;
		count = newcount;
	}

	@Override
	public synchronized void write(byte b[], int off, int len) {
		if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return;
		}
		int newcount = max(pos + len, count);
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		System.arraycopy(b, off, buf, pos, len);
		pos += len;
		count = newcount;
	}

	@Override
	public synchronized void reset() {
		count = 0;
		pos = 0;
	}

	public void seek(long pos) throws IOException {
		this.pos = (int) pos;
	}

	public long getStreamPosition() throws IOException {
		return pos;
	}

	public void toOutputStream(OutputStream out) throws IOException {
		out.write(buf, 0, count);
	}

	public byte[] getBuffer() {
		return buf;
	}
}
