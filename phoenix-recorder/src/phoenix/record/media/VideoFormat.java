package phoenix.record.media;

/**
 * 视频格式定制，定制本类支持的视频编码类型
 * @author mengfeiyang
 *
 */
public class VideoFormat extends Format {

	private final int width;
	private final int height;
	private final int depth;
	private final Class<?> dataClass;
	private final String encoding;
	private final String compressorName;
	public static final String IMAGE = "image";
	public static final String AVI_DIB = "DIB ";
	public static final String AVI_RLE = "RLE ";
	public static final String AVI_TECHSMITH_SCREEN_CAPTURE = "tscc";
	public static final String AVI_MJPG = "MJPG";
	public static final String AVI_PNG = "png ";

	public VideoFormat(String encoding, Class<?> dataClass, int width, int height, int depth) {
		this(encoding, encoding, dataClass, width, height, depth);
	}

	public VideoFormat(String encoding, String compressorName, Class<?> dataClass, int width, int height, int depth) {
		this.encoding = encoding;
		this.compressorName = compressorName;
		this.dataClass = dataClass;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	public VideoFormat(String encoding, String compressorName) {
		this(encoding, compressorName, null, -1, -1, -1);
	}

	public VideoFormat(String encoding) {
		this(encoding, encoding, null, -1, -1, -1);
	}

	public int getDepth() {
		return depth;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Class<?> getDataClass() {
		return dataClass;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getCompressorName() {
		return compressorName;
	}

}
