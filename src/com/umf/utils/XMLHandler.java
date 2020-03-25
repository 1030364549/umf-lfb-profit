package com.umf.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.umf.config.ParamsConfig;

@SuppressWarnings("all")
public class XMLHandler {
	
	private LogUtil log = new LogUtil();
	private LoFunction lo = new LoFunction();

	/**
	 * 
	 ********************************************************* .<br>
	 * [����] analyticalXML <br>
	 * [����] ����socketXML<br>
	 * [����] �����ļ�·����ӳ��ʵ����<br>
	 * [����] boolean <br>
	 * [����] UMF 
	 * [ʱ��] 2019��8��13�� ����4:35:25 <br>
	 ********************************************************* .<br>
	 */
	public boolean analyticalXML(String source) throws Exception {
		try {
			SAXBuilder saxBuilder = new SAXBuilder(); // ����һ��SAXBuilder����
			Document doc = saxBuilder.build(source); // ��ȡ��Դ
			Element root = doc.getRootElement(); // ��ȡ��Ԫ��(socketConfig)
			Element childrenRoot = (Element) root.getChildren("Init").get(0); // �Ӹ�Ԫ��(Init)
			List<Element> parameterList = childrenRoot.getChildren("parameter"); // ��ȡ�Ӹ�Ԫ��init�µ�����parameter��Ԫ��
			ParamsConfig socketConfig = new ParamsConfig(); // ������
			for (int j = 0; j < parameterList.size(); j++) { // �����Ӹ�Ԫ�ص���Ԫ�ؼ���(������parameterԪ��)
				Element element = parameterList.get(j); // ��ȡparameterԪ��
				reflexAss(element, socketConfig);
			}
			System.out.println("init socket success...");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [����] reflexAss <br>
	 * [����] ���÷�����Ƹ�socket������ֵ<br>
	 * [����] xmlԪ�ء�ʵ�������<br>
	 * [����] void <br>
	 * [����] UMF 
	 * [ʱ��] 2019��8��13�� ����4:35:35 <br>
	 ********************************************************* .<br>
	 */
	private void reflexAss(Element element, ParamsConfig socketConfig) throws Exception {
		String filedName = element.getAttribute("name").getValue();
		String filedValue = element.getAttribute("value").getValue();
		String methodName = lo.appendField("set", filedName.substring(0, 1).toUpperCase(), filedName.substring(1));
		ParamsConfig.class.getMethod(methodName, new Class[] { String.class }).invoke(socketConfig, filedValue);
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [����] parseXML <br>
	 * [����] ����xml�ַ�������map<br>
	 * [����] xml�ַ���<br>
	 * [����] Map<String,String> <br>
	 * [����] UMF 
	 * [ʱ��] 2019��8��13�� ����4:35:40 <br>
	 ********************************************************* .<br>
	 */
	public static Map<String, String> parseXML(String regXml) {
		Map<String, String> map = new HashMap<String, String>();
		StringReader read = new StringReader(regXml); // ����һ���µ��ַ���
		InputSource source = new InputSource(read); // �����µ�����ԴSAX ��������ʹ��
		SAXBuilder sb = new SAXBuilder(); // ����һ���µ�SAXBuilder
		try {
			Document doc = sb.build(source); // ͨ������Դ����һ��Document
			Element root = doc.getRootElement().getChild("mt"); // ȡ�ĸ�Ԫ��
			List<Element> jiedian = root.getChildren(); // �õ���Ԫ��������Ԫ�صļ���
			Element et = null; // ���XML�е������ռ䣨XML��δ����ɲ�д��
			for (int i = 0; i < jiedian.size(); i++) {
				et = (Element) jiedian.get(i);  // ѭ�����εõ���Ԫ��
				String name = et.getName();
				String filedValue = et.getValue();
				map.put(name, filedValue);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (read != null) {
					read.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [����] parseXmlHuaXin <br>
	 * [����] ����xml�ַ�������map<br>
	 * [����] xml�ַ���<br>
	 * [����] Map<String,String> <br>
	 * [����] UMF 
	 * [ʱ��] 2019��8��13�� ����4:35:44 <br>
	 ********************************************************* .<br>
	 */
	public static Map<String, String> parseXmlHuaXin(String regXml) {
		Map<String, String> map = new HashMap<String, String>();
		StringReader read = new StringReader(regXml); // ����һ���µ��ַ���
		InputSource source = new InputSource(read); // �����µ�����ԴSAX ��������ʹ��
		SAXBuilder sb = new SAXBuilder(); // ����һ���µ�SAXBuilder
		try {
			Document doc = sb.build(source); // ͨ������Դ����һ��Document
			Element rootElt = doc.getRootElement();
			List<Element> jiedian = rootElt.getChildren();
			for (int i = 0; i < jiedian.size(); i++) {
				rootElt = (Element) jiedian.get(i);  // ѭ�����εõ���Ԫ��
				String name = rootElt.getName();
				String filedValue = rootElt.getValue();
				map.put(name, filedValue);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (read != null) {
					read.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
