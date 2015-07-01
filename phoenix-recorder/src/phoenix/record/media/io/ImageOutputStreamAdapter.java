package phoenix.record.media.io;

import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.stream.ImageOutputStream;

public class ImageOutputStreamAdapter extends OutputStream {

	protected ImageOutputStream out;

	public ImageOutputStreamAdapter(ImageOutputStream out) {
		this.out = out;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException {
		out.write(b, off, len);
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public void close() throws IOException {
		try {
			flush();
		} finally {
			out.close();
		}
	}
}
