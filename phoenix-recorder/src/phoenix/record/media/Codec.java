package phoenix.record.media;

public interface Codec {

	public Format setInputFormat(Format f);

	public Format setOutputFormat(Format f);

	public void process(Buffer in, Buffer out);

	public void setQuality(float newValue);

	public float getQuality();
}
