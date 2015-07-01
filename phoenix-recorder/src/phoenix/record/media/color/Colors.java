package phoenix.record.media.color;

import java.awt.image.IndexColorModel;

/**
 * 视频像素颜色处理
 * @author mengfeiyang
 * @version 1.0
 *
 */
public class Colors {

	private Colors() {
	}
	
	public static IndexColorModel createMacColors() {
		byte[] r = new byte[256];
		byte[] g = new byte[256];
		byte[] b = new byte[256];
		int index = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 6; k++) {
					r[index] = (byte) (255 - 51 * i);
					g[index] = (byte) (255 - 51 * j);
					b[index] = (byte) (255 - 51 * k);
					index++;
				}
			}
		}
		index--;
		byte[] ramp = { (byte) 238, (byte) 221, (byte) 187, (byte) 170, (byte) 136, (byte) 119, 85, 68, 34, 17 };
		for (int i = 0; i < 10; i++) {
			r[index] = ramp[i];
			g[index] = (byte) (0);
			b[index] = (byte) (0);
			index++;
		}
		for (int j = 0; j < 10; j++) {
			r[index] = (byte) (0);
			g[index] = ramp[j];
			b[index] = (byte) (0);
			index++;
		}
		for (int k = 0; k < 10; k++) {
			r[index] = (byte) (0);
			g[index] = (byte) (0);
			b[index] = ramp[k];
			index++;
		}
		for (int ijk = 0; ijk < 10; ijk++) {
			r[index] = ramp[ijk];
			g[index] = ramp[ijk];
			b[index] = ramp[ijk];
			index++;
		}
		IndexColorModel icm = new IndexColorModel(8, 256, r, g, b);
		return icm;
	}
}
