package phoenix.record.camera;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import phoenix.record.constant.Constant;

/**
 * 初始化Java robot，用以对屏幕进行截图。截图前会自动获取屏幕大小。
 * <em>编写日期：2014年2月8日 9:07</em>
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7及以上
 *
 */
public class Capture {

    private static final Rectangle rectangle = new Rectangle(Constant.WIDTH,Constant.HEIGHT);
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage capture() throws Exception {
        return robot.createScreenCapture(rectangle);
    }
}
