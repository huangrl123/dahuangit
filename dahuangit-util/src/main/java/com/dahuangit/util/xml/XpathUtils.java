package com.dahuangit.util.xml;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * xpath查询相关的工具类
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年11月11日 上午7:41:19
 */
public class XpathUtils {

	/**
	 * 通过xpath表达式获取相应的节点
	 * 
	 * @param xml
	 * @param xpathExpression
	 * @return
	 * @throws DocumentException
	 */
	public static List<Node> findNodes(String xml, String xpathExpression) throws DocumentException {
		Validate.notNull( xml, " xml不能为null");
		Validate.notNull( xpathExpression, " xpathExpression不能为null");
		
		Document doc = DocumentHelper.parseText(xml);

		Element root = doc.getRootElement();

		List<Node> nodes = root.selectNodes(xpathExpression);

		return nodes;
	}

	public static Node findUnique(String xml, String xpathExpression) throws DocumentException {
		Validate.notNull( xml, " xml不能为null");
		Validate.notNull( xpathExpression, " xpathExpression不能为null");
		
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();

		Node node = root.selectSingleNode(xpathExpression);

		return node;
	}

	public static Element findUniqueAndToElement(String xml, String xpathExpression) throws DocumentException {
		Validate.notNull( xml, " xml不能为null");
		Validate.notNull( xpathExpression, " xpathExpression不能为null");
		
		Node node = findUnique(xml, xpathExpression);

		if (null != node) {
			return (Element) node;
		}

		return null;
	}
}
