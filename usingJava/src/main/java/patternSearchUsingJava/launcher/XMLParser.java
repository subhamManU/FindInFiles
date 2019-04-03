package patternSearchUsingJava.launcher;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLParser {

	public static void main(String[] args) {

		try {
			File inputFile = new File(
					"E:\\NRI-NAM\\NAMRUICodebase\\NAMRUI\\devel\\client\\JSPX\\views\\ref\\employeeDetail.jspx");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document doc = dbBuilder.parse(inputFile);
			

			NodeList nodeList = doc.getElementsByTagName("table");

			Element eElement = null;
			for (int i = 0; i < nodeList.getLength(); i++) {
				 eElement = (Element)nodeList.item(i);
				// System.out.println(eElement.getAttribute("class"));
				 if(eElement.getAttribute("class").equals("tableStyle sixCol table-layout-override")) {
					 //System.out.println(eElement.getElementsByTagName("td").item(0));
					 Element childEle =(Element) eElement.getElementsByTagName("tr").item(0).getChildNodes();
					 
					 System.out.println(childEle);
				 }
			}
			/*doc.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(inputFile);
			transformer.transform(source, result);*/

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
