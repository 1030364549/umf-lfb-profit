package com.umf.socket.client;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author:LiuYuKun
 * @Date:2019/11/19
 */
public class Test3xml {
    public static void main(String[] args) {
        SAXReader saxReader = new SAXReader();
        try {
            /*获取目标xml文件*/
            Document document = saxReader.read("src/Phones.xml");
            /*获取xml文件的根节点*/
            Element rootElement = document.getRootElement();
//            /*获取根节点下的所有一级节点集合*/
//            List<Element> phones = rootElement.elements();
//            for(Element element:phones){
//                /*标签名*/
//                System.out.println("父标签");
//                System.out.println("name:"+element.getName());
//                /*标签的属性名，属性内容*/
//                System.out.println("attributeText:"+element.attribute("name").getName()+"="+element.attribute("name").getText());
//                System.out.println("attributeValue:"+element.attribute("name").getName()+"="+element.attribute("name").getValue());
//                System.out.println("attributeStringValue:"+element.attribute("name").getName()+"="+element.attribute("name").getStringValue());
//                /*标签中的文本内容，不包含子标签*/
//                System.out.println("text:"+element.getText().trim());
//                /*标签中的文本内容，包含子标签*/
//                System.out.println("stringvalue:"+element.getStringValue().trim());
//                /*获取到一级节点中的所有二级节点集合*/
//                List<Element> elements = element.elements();
//                System.out.println("子标签");
//                for(Element item:elements){
//                    /*获取到二级节点的所有属性集合*/
//                    List<DefaultAttribute> attributes = item.attributes();
//                    /*遍历属性，打印属性名和属性值*/
//                    for(DefaultAttribute s:attributes){
//                        System.out.println(s.getName()+"="+s.getValue());
//                    }
//                }
//                System.out.println("---------------------------");
//            }

            /*xml中添加新节点，输入标签名*/
            Element element = rootElement.addElement("iphone");
            /*为新节点添加属性名和属性值*/
            element.addAttribute("name","iphone 7Plus");
            element.addAttribute("color","black");
            /*为新节点添加文本内容*/
            element.addText("咯咯哒");
            /*为新节点添加第一个子节点*/
            Element element1 = element.addElement("type");
            element1.addAttribute("name","lyk");
            /*为新节点添加第二个子节点*/
            Element element2 = element.addElement("type");
            element2.addAttribute("name","hhh");
            /*格式化操作，添加完节点之后保存信息*/
            OutputFormat outputFormat = OutputFormat.createPrettyPrint();
            /*回写xml，将document放入*/
            XMLWriter xml = new XMLWriter(new FileOutputStream("src/Phones.xml"),outputFormat);
            xml.write(document);
            xml.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
