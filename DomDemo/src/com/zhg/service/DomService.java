package com.zhg.service;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zhg.entity.Student;

public class DomService {
	public ArrayList<Student> getStudents() {
		ArrayList<Student> students = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse("c:\\student.xml");
			NodeList list = doc.getElementsByTagName("student");
			if (list != null) {
				students = new ArrayList<Student>();
				int len = list.getLength();
				for (int i = 0; i < len; i++) {
					Node studentNode = list.item(i);
					if (studentNode != null) {
						Element element = (Element) studentNode;
						int id = Integer.parseInt(element.getAttribute("id"));

						Student stu = new Student();
						stu.setId(id);
						String name = element.getElementsByTagName("name")
								.item(0).getFirstChild().getNodeValue();
						String sex = element.getElementsByTagName("sex")
								.item(0).getFirstChild().getNodeValue();
						int age = Integer.parseInt(element
								.getElementsByTagName("age").item(0)
								.getFirstChild().getNodeValue());
						stu.setName(name);
						stu.setSex(sex);
						stu.setAge(age);
						students.add(stu);
					}
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return students;
	}
}
