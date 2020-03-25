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
            /*��ȡĿ��xml�ļ�*/
            Document document = saxReader.read("src/Phones.xml");
            /*��ȡxml�ļ��ĸ��ڵ�*/
            Element rootElement = document.getRootElement();
//            /*��ȡ���ڵ��µ�����һ���ڵ㼯��*/
//            List<Element> phones = rootElement.elements();
//            for(Element element:phones){
//                /*��ǩ��*/
//                System.out.println("����ǩ");
//                System.out.println("name:"+element.getName());
//                /*��ǩ������������������*/
//                System.out.println("attributeText:"+element.attribute("name").getName()+"="+element.attribute("name").getText());
//                System.out.println("attributeValue:"+element.attribute("name").getName()+"="+element.attribute("name").getValue());
//                System.out.println("attributeStringValue:"+element.attribute("name").getName()+"="+element.attribute("name").getStringValue());
//                /*��ǩ�е��ı����ݣ��������ӱ�ǩ*/
//                System.out.println("text:"+element.getText().trim());
//                /*��ǩ�е��ı����ݣ������ӱ�ǩ*/
//                System.out.println("stringvalue:"+element.getStringValue().trim());
//                /*��ȡ��һ���ڵ��е����ж����ڵ㼯��*/
//                List<Element> elements = element.elements();
//                System.out.println("�ӱ�ǩ");
//                for(Element item:elements){
//                    /*��ȡ�������ڵ���������Լ���*/
//                    List<DefaultAttribute> attributes = item.attributes();
//                    /*�������ԣ���ӡ������������ֵ*/
//                    for(DefaultAttribute s:attributes){
//                        System.out.println(s.getName()+"="+s.getValue());
//                    }
//                }
//                System.out.println("---------------------------");
//            }

            /*xml������½ڵ㣬�����ǩ��*/
            Element element = rootElement.addElement("iphone");
            /*Ϊ�½ڵ����������������ֵ*/
            element.addAttribute("name","iphone 7Plus");
            element.addAttribute("color","black");
            /*Ϊ�½ڵ�����ı�����*/
            element.addText("������");
            /*Ϊ�½ڵ���ӵ�һ���ӽڵ�*/
            Element element1 = element.addElement("type");
            element1.addAttribute("name","lyk");
            /*Ϊ�½ڵ���ӵڶ����ӽڵ�*/
            Element element2 = element.addElement("type");
            element2.addAttribute("name","hhh");
            /*��ʽ�������������ڵ�֮�󱣴���Ϣ*/
            OutputFormat outputFormat = OutputFormat.createPrettyPrint();
            /*��дxml����document����*/
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
