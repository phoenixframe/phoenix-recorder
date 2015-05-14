package com.youku.yks.tools;

import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析XML配置文件
 * @author mengfeiyang
 *
 */
public class XmlPaser {
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getValues(String xmlFile) {
		SAXReader saxReader = new SAXReader();
		HashMap<String, String> values = new HashMap<String, String>();
		try {
			Document doc = saxReader.read(xmlFile);
			Element root = doc.getRootElement();
			List<Element> elements = root.elements();
			for (Element element : elements) {
				values.put(element.attributeValue("name"), element.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return values;
	}
}
