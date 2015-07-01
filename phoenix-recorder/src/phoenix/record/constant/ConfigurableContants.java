package phoenix.record.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置值
 * <em>编写日期：2014年2月8日 9:37</em>
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7及以上
 *
 */
public class ConfigurableContants {

	protected static Properties props = new Properties();

	protected static void init(String propertyFileName) {
		InputStream in = null;
		try {
			in = ConfigurableContants.class.getResourceAsStream(propertyFileName);
			if (in != null)
				props.load(in);
		} catch (IOException e) {
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}

	protected static String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
}
