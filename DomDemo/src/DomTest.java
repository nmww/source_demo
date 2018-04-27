import java.io.IOException;
import java.util.ArrayList;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.zhg.entity.Student;
import com.zhg.service.DomService;


public class DomTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Student> list = new DomService().getStudents();
		for(Student stu : list){
			System.out.println(stu);
		}
	}

}
