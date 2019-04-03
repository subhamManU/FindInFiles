package patternSearchUsingJava.launcher;

import java.util.ArrayList;
import java.util.List;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
public class XMLParserComeOn {

	public static void main(String[] args) throws Exception
    {
        //Get DOM Node for XML
        String fileName= "E:\\NRI-NAM\\NAMRUICodebase\\NAMRUI\\devel\\client\\JSPX\\views\\ref\\employeeDetail.jspx";
        Document document = getDocument(fileName);
         
        String xpathExpression = "";
             
        /*******Get attribute values using xpath******/
         
       
         
        //Get employee whose id contains 1
        xpathExpression = "//table[@class='tableStyle sixCol table-layout-override']/tr/td";
        //xpathExpression = "//span[@class='detailTxtVal']";
        System.out.println( evaluateXPath(document, xpathExpression) );
       
    }
     
    private static List<String> evaluateXPath(Document document, String xpathExpression) throws Exception
    {
        // Create XPathFactory object
        XPathFactory xpathFactory = XPathFactory.newInstance();
         
        // Create XPath object
        XPath xpath = xpathFactory.newXPath();
 
        List<String> values = new ArrayList<>();
        try
        {
            // Create XPathExpression object
            XPathExpression expr = xpath.compile(xpathExpression);
             
            // Evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
             
            for (int i = 0; i < nodes.getLength(); i++) {
                values.add(nodes.item(i).getTextContent());
            }
                 
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return values;
    }
 
    private static Document getDocument(String fileName) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fileName);
        return doc;
    }
}


