package phoenix.record.media;


public class Buffer {

	public final static int FLAG_DISCARD = 1 << 1;
	public final static int FLAG_KEY_FRAME = 1 << 4;
	public int flags;
	public Object data;
	public int offset;
	public int length;
	public long duration;
	public long timeScale;
	public long timeStamp;
	public Format format;
	public int sampleCount = 1;
}
