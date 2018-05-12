package com.wz.common.tools;

import java.io.ByteArrayInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nuxeo.common.xmap.XMap;

/**
 * xml bean 转换工具
 * 
 * @author Johnson.Jia
 */
public class XmlToBeanUtils {

	private static Logger logger = Logger.getLogger(XmlToBeanUtils.class);

	/**
	 * 把对象 转换成 xml String 字符串
	 * 
	 * @author Johnson.Jia
	 * @date 2015年9月21日 下午4:22:03
	 * @param obj
	 * @param T
	 * @return
	 */
	public static String objectToXml(Object obj, Class<?> T) {
		try {
			XMap xmp = new XMap();
			xmp.register(T);
			String xml = xmp.toXML(obj);
			return xml.replace("<root>\n", "").replace("</root>\n", "");
		} catch (Exception e) {
			logger.error("【====Xml格式转换异常====】", e);
			return null;
		}
	}

	/**
	 * 把 xml字符串转换成 bean
	 * 
	 * @author Johnson.Jia
	 * @param xml
	 * @param T
	 * @return
	 */
	public static Object[] xmlToObject(String xml, Class<?> T) {
		try {
			if (StringUtils.isBlank(xml)) {
				return null;
			}
			XMap xmp = new XMap();
			xmp.register(T);
			return xmp.loadAll(new ByteArrayInputStream(xml.getBytes()));
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		
	}
}
