package com.store.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 实体工厂类
 * @author qiang
 *
 */
public class BeanFactory {

	public static Object getBean(String id) {

		// 通过id指定一个实现类
		try {
			// 1.获取document对象
			Document doc = new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("application.xml"));

			// 2.获取bean
			Element ele = (Element) doc.selectSingleNode("//bean[@id='" + id + "']");

			// 3.获取bean的class
			String value = ele.attributeValue("class");

			// 4.反射
			return Class.forName(value).newInstance();
                                                     //
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	
}
