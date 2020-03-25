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
	 * [方法] analyticalXML <br>
	 * [描述] 解析socketXML<br>
	 * [参数] 配置文件路径、映射实体类<br>
	 * [返回] boolean <br>
	 * [作者] UMF 
	 * [时间] 2019年8月13日 下午4:35:25 <br>
	 ********************************************************* .<br>
	 */
	public boolean analyticalXML(String source) throws Exception {
		try {
			SAXBuilder saxBuilder = new SAXBuilder(); // 创建一个SAXBuilder对象
			Document doc = saxBuilder.build(source); // 读取资源
			Element root = doc.getRootElement(); // 获取根元素(socketConfig)
			Element childrenRoot = (Element) root.getChildren("Init").get(0); // 子根元素(Init)
			List<Element> parameterList = childrenRoot.getChildren("parameter"); // 获取子根元素init下的所有parameter子元素
			ParamsConfig socketConfig = new ParamsConfig(); // 加载类
			for (int j = 0; j < parameterList.size(); j++) { // 遍历子根元素的子元素集合(即遍历parameter元素)
				Element element = parameterList.get(j); // 获取parameter元素
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
	 * [方法] reflexAss <br>
	 * [描述] 利用反射机制给socket参数赋值<br>
	 * [参数] xml元素、实体类对象<br>
	 * [返回] void <br>
	 * [作者] UMF 
	 * [时间] 2019年8月13日 下午4:35:35 <br>
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
	 * [方法] parseXML <br>
	 * [描述] 解析xml字符串返回map<br>
	 * [参数] xml字符串<br>
	 * [返回] Map<String,String> <br>
	 * [作者] UMF 
	 * [时间] 2019年8月13日 下午4:35:40 <br>
	 ********************************************************* .<br>
	 */
	public static Map<String, String> parseXML(String regXml) {
		Map<String, String> map = new HashMap<String, String>();
		StringReader read = new StringReader(regXml); // 创建一个新的字符串
		InputSource source = new InputSource(read); // 创建新的输入源SAX 解析器将使用
		SAXBuilder sb = new SAXBuilder(); // 创建一个新的SAXBuilder
		try {
			Document doc = sb.build(source); // 通过输入源构造一个Document
			Element root = doc.getRootElement().getChild("mt"); // 取的根元素
			List<Element> jiedian = root.getChildren(); // 得到根元素所有子元素的集合
			Element et = null; // 获得XML中的命名空间（XML中未定义可不写）
			for (int i = 0; i < jiedian.size(); i++) {
				et = (Element) jiedian.get(i);  // 循环依次得到子元素
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
	 * [方法] parseXmlHuaXin <br>
	 * [描述] 解析xml字符串返回map<br>
	 * [参数] xml字符串<br>
	 * [返回] Map<String,String> <br>
	 * [作者] UMF 
	 * [时间] 2019年8月13日 下午4:35:44 <br>
	 ********************************************************* .<br>
	 */
	public static Map<String, String> parseXmlHuaXin(String regXml) {
		Map<String, String> map = new HashMap<String, String>();
		StringReader read = new StringReader(regXml); // 创建一个新的字符串
		InputSource source = new InputSource(read); // 创建新的输入源SAX 解析器将使用
		SAXBuilder sb = new SAXBuilder(); // 创建一个新的SAXBuilder
		try {
			Document doc = sb.build(source); // 通过输入源构造一个Document
			Element rootElt = doc.getRootElement();
			List<Element> jiedian = rootElt.getChildren();
			for (int i = 0; i < jiedian.size(); i++) {
				rootElt = (Element) jiedian.get(i);  // 循环依次得到子元素
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
