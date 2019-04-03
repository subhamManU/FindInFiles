package patternSearchUsingJava.launcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import patternSearchUsingJava.model.FileStructure;

public class XMLParserAndModifier {

	public static List<FileStructure> modifyXML(String path, String xPath) throws Exception {
		List<FileStructure> requiredList = new ArrayList<>();
		Document doc = readXML(path);
		NodeList nodeList = generateNodeList(doc, xPath);
		Element eElement = null;
		// System.out.println(nodeList.getLength());

		System.out.println(path + "\t" + nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			FileStructure fileStructure = new FileStructure();

			Node nNode = nodeList.item(i);
			eElement = (Element) nNode;

			String content = eElement.getTextContent();

			eElement.setAttribute("title", content);

			modifyXml(doc, path);

			fileStructure.setFileName(path);
			fileStructure.setLine(content);
			fileStructure.setLineNo(i);

			System.out.println(content);
			requiredList.add(fileStructure);
		}

		return requiredList;

	}

	private static Document readXML(String path) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(path);
		
		return doc;
	}

	private static NodeList generateNodeList(Document doc, String xPath) throws XPathExpressionException {
		
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xPath);
		NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		System.out.println(nodeList.item(0));
		return nodeList;
	}

	private static void modifyXml(Document doc, String path) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(path);
		transformer.transform(source, result);
	}
	
	
	
	/*public static void main(String[] args) throws Exception {
		String path = "E:\\NRI-NAM\\NAMRUICodebase\\NAMRUI\\devel\\client\\JSPX\\views\\ref\\accountGeneralAttrConfirmationInc.jspx";
		
		String xPath = "/span[@class=\"detailTxtVal\"]";
		
		modifyXML(path,xPath);
	}*/
}
