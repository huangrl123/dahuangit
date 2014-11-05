package com.dahuangit.util.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.oxm.castor.CastorMarshaller;

/**
 * xml工具类
 * 
 * @author 黄仁良
 * 
 */
public class XmlUtils {
	private static Logger logger = Logger.getLogger(XmlUtils.class);

	/**
	 * 将一个对象转换为xml字符串
	 * 
	 * @param xmlMarshaller
	 *            CastorMarshaller对象，需要从外部传入
	 * @param obj
	 *            需要转换为xml的对象，一般是一个pojo
	 * @return
	 * @throws Exception
	 */
	public static String obj2xml(CastorMarshaller xmlMarshaller, Object obj) throws Exception {
		Validate.notNull(xmlMarshaller, "CastorMarshaller对象不能为null");
		Validate.notNull(obj, "需要转换为xml的对象不能为null");

		StringWriter sw = new StringWriter();
		StringBuffer sb = new StringBuffer();
		xmlMarshaller.marshal(obj, new StreamResult(sw));
		sb.append(sw.toString());

		String str = formatXMLStr(sb.toString());
		return str;
	}

	/**
	 * 将xml字符转换为一个指定类型的对象
	 * 
	 * @param xmlMarshaller
	 *            CastorMarshaller对象，需要从外部传入
	 * @param xml
	 *            与要转换的对象想匹配的xml字符串
	 * @param clazz
	 *            需要转换为的class
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xml2obj(CastorMarshaller xmlMarshaller, String xml, Class<T> clazz) throws Exception {
		Validate.notNull(xmlMarshaller, "CastorMarshaller对象不能为null");
		Validate.notNull(xml, "与要转换的对象想匹配的xml字符串不能为null");

		xmlMarshaller.setIgnoreExtraElements(true);
		Object obj = xmlMarshaller.unmarshal(clazz, new StringReader(xml));

		return (T) obj;
	}

	/**
	 * 将xml字符转换为一个指定类型的对象
	 * 
	 * @param xmlMarshaller
	 *            CastorMarshaller对象，需要从外部传入
	 * @param in
	 *            与要转换的对象想匹配的xml字符串的流
	 * @param clazz
	 *            需要转换为的class
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlInputStream2obj(CastorMarshaller xmlMarshaller, InputStream in, Class<T> clazz)
			throws Exception {
		Validate.notNull(xmlMarshaller, "CastorMarshaller对象不能为null");
		Validate.notNull(in, "InputStream对象不能为null");

		Object obj = xmlMarshaller.unmarshal(clazz, new InputStreamReader(in, "utf-8"));

		return (T) obj;
	}

	/**
	 * 将xml类型的字符串格式化
	 * 
	 * @param str
	 *            xml字符串
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String formatXMLStr(String str) throws DocumentException, IOException {
		Validate.notNull(str, "传入的xml字符串不能为null");

		InputStream in = null;
		InputStreamReader strInStream = null;

		StringWriter sw = new StringWriter();
		try {
			SAXReader saxReader = new SAXReader();
			byte[] bytes = str.getBytes();
			in = new ByteArrayInputStream(bytes);

			strInStream = new InputStreamReader(in);
			Document document = saxReader.read(strInStream);

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");

			XMLWriter xmlwriter = new XMLWriter(sw, format);
			xmlwriter.write(document);
		} catch (Exception e) {
			throw new XmlException("将xml类型的字符串格式化出错", e);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(strInStream);
		}

		return "\r" + sw.toString();
	}

	/**
	 * 一个字符串是否为xml
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isXml(String str) {
		boolean isXml = false;

		try {
			XmlUtils.formatXMLStr(str);
			isXml = true;
		} catch (Exception e) {
		}

		return isXml;
	}
}
