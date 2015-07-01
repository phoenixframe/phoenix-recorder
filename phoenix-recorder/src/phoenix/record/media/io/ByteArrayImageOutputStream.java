package phoenix.record.media.io;

import static java.lang.Math.max;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.util.Arrays;

import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.ImageOutputStreamImpl;

/**
 * 输出图片的二进制编码
 * @author mengfeiyang
 * @version 1.0
 *
 */
public class ByteArrayImageOutputStream extends ImageOutputStreamImpl {

	protected byte buf[];
	protected int count;
	private final int arrayOffset;

	public ByteArrayImageOutputStream() {
		this(16);
	}

	public ByteArrayImageOutputStream(int initialCapacity) {
		this(new byte[initialCapacity]);
	}

	public ByteArrayImageOutputStream(byte[] buf) {
		this(buf, ByteOrder.BIG_ENDIAN);
	}

	public ByteArrayImageOutputStream(byte[] buf, ByteOrder byteOrder) {
		this(buf, 0, buf.length, byteOrder);
	}

	public ByteArrayImageOutputStream(byte[] buf, int offset, int length, ByteOrder byteOrder) {
		this.buf = buf;
		this.streamPos = offset;
		this.count = Math.min(offset + length, buf.length);
		this.arrayOffset = offset;
		this.byteOrder = byteOrder;
	}

	public ByteArrayImageOutputStream(ByteOrder byteOrder) {
		this(new byte[16], byteOrder);
	}

	@Override
	public synchronized int read() throws IOException {
		flushBits();
		return (streamPos < count) ? (buf[(int) (streamPos++)] & 0xff) : -1;
	}

	@Override
	public synchronized int read(byte b[], int off, int len) throws IOException {
		flushBits();
		if (b == null) {
			throw new NullPointerException();
		} else if (off < 0 || len < 0 || len > b.length - off) {
			throw new IndexOutOfBoundsException();
		}
		if (streamPos >= count) {
			return -1;
		}
		if (streamPos + len > count) {
			len = (int) (count - streamPos);
		}
		if (len <= 0) {
			return 0;
		}
		System.arraycopy(buf, (int) streamPos, b, off, len);
		streamPos += len;
		return len;
	}

	public synchronized long skip(long n) {
		if (streamPos + n > count) {
			n = count - streamPos;
		}
		if (n < 0) {
			return 0;
		}
		streamPos += n;
		return n;
	}

	public synchronized int available() {
		return (int) (count - streamPos);
	}

	@Override
	public void close() {
	}

	@Override
	public long getStreamPosition() throws IOException {
		checkClosed();
		return streamPos - arrayOffset;
	}

	@Override
	public void seek(long pos) throws IOException {
		checkClosed();
		flushBits();
		if (pos < flushedPos) {
			throw new IndexOutOfBoundsException("pos < flushedPos!");
		}
		this.streamPos = pos + arrayOffset;
	}

	@Override
	public synchronized void write(int b) throws IOException {
		flushBits();
		long newcount = max(streamPos + 1, count);
		if (newcount > Integer.MAX_VALUE) {
			throw new IndexOutOfBoundsException(newcount + " > max array size");
		}
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, max(buf.length << 1, (int) newcount));
		}
		buf[(int) streamPos++] = (byte) b;
		count = (int) newcount;
	}

	@Override
	public synchronized void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	@Override
	public synchronized void write(byte b[], int off, int len) throws IOException {
		flushBits();
		if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return;
		}
		int newcount = max((int) streamPos + len, count);
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		System.arraycopy(b, off, buf, (int) streamPos, len);
		streamPos += len;
		count = newcount;
	}

	public void toOutputStream(OutputStream out) throws IOException {
		out.write(buf, arrayOffset, count);
	}

	public void toImageOutputStream(ImageOutputStream out) throws IOException {
		out.write(buf, arrayOffset, count);
	}

	public synchronized byte[] toByteArray() {
		byte[] copy = new byte[count - arrayOffset];
		System.arraycopy(buf, arrayOffset, copy, 0, count);
		return copy;
	}

	public byte[] getBuffer() {
		return buf;
	}

	@Override
	public long length() {
		return count - arrayOffset;
	}

	public synchronized void clear() {
		count = arrayOffset;
		streamPos = arrayOffset;
	}
}
