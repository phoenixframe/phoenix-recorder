package phoenix.record.media.jpeg;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import phoenix.record.media.AbstractVideoCodec;
import phoenix.record.media.Buffer;
import phoenix.record.media.Format;
import phoenix.record.media.VideoFormat;
import phoenix.record.media.io.ByteArrayImageOutputStream;


/**
 * 截屏时支持两种类型的图片格式：JPEG和PNG，本类是用来处理JPEG格式的图片。用于将JPEG格式图片的字节流压制成AVI_MJPG。
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7 及以上
 *
 */
public class JPEGCodec extends AbstractVideoCodec {

	@Override
	public Format setInputFormat(Format f) {
		if (f instanceof VideoFormat) {
			VideoFormat vf = (VideoFormat) f;
			if (BufferedImage.class.isAssignableFrom(vf.getDataClass())) {
				return super.setInputFormat(new VideoFormat(VideoFormat.IMAGE, vf.getDataClass(), vf.getWidth(), vf.getHeight(), vf.getDepth()));
			}
		}
		return super.setInputFormat(null);
	}

	@Override
	public Format setOutputFormat(Format f) {
		if (f instanceof VideoFormat) {
			VideoFormat vf = (VideoFormat) f;
			return super.setOutputFormat(new VideoFormat(VideoFormat.AVI_MJPG, byte[].class, vf.getWidth(), vf.getHeight(), 24));
		}
		return super.setOutputFormat(null);
	}

	@Override
	public void process(Buffer in, Buffer out) {
		if ((in.flags & Buffer.FLAG_DISCARD) != 0) {
			out.flags = Buffer.FLAG_DISCARD;
			return;
		}
		BufferedImage image = getBufferedImage(in);
		if (image == null) {
			out.flags = Buffer.FLAG_DISCARD;
			return;
		}
		out.format = outputFormat;
		ByteArrayImageOutputStream tmp;
		if (out.data instanceof byte[]) {
			tmp = new ByteArrayImageOutputStream((byte[]) out.data);
		} else {
			tmp = new ByteArrayImageOutputStream();
		}

		try {
			ImageWriter iw = (ImageWriter) ImageIO.getImageWritersByMIMEType("image/jpeg").next();
			ImageWriteParam iwParam = iw.getDefaultWriteParam();
			iwParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwParam.setCompressionQuality(quality);
			iw.setOutput(tmp);
			IIOImage img = new IIOImage(image, null, null);
			iw.write(null, img, iwParam);
			iw.dispose();
			out.flags = Buffer.FLAG_KEY_FRAME;
			out.data = tmp.getBuffer();
			out.offset = 0;
			out.length = (int) tmp.getStreamPosition();
		} catch (IOException ex) {
			ex.printStackTrace();
			out.flags = Buffer.FLAG_DISCARD;
			return;
		}
	}
}
