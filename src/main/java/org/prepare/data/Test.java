package org.prepare.data;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Test
{
    public static void main1(String[] args) throws UnsupportedEncodingException {
        final String xmlStr = "<employees>" +
                "   <employee id=\"101\" content=\"&#55356;\">" +
                "        <name>Lokesh Gupta</name>" +
                "       <title>Author</title>" +
                "   </employee>" +
                "   <employee id=\"102\">" +
                "        <name>Brian Lara</name>" +
                "       <title>Cricketer</title>" +
                "   </employee>" +
                "</employees>";

        //Use method to convert XML string content to XML Document object
        String a = xmlStr.replaceAll("&#5[0-9]*;", " ");
        System.out.println(a);
        Document doc = convertStringToXMLDocument( xmlStr );

        //Verify XML document is build correctly
        System.out.println(doc.getFirstChild().getNodeName());
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File file = new File("/Users/batuan/Documents/hoctap/datn/vips_java/segmentation-Layout-Content-website/data-link/123nhadatviet/123nhadatviet-1.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        //Node node = doc.getElementsByTagName("LayoutNode").getLength()
        doc.getDocumentElement().getChildNodes().item(2).getNodeName();
        System.out.println(doc.getElementsByTagName("LayoutNode").getLength());
    }

    private static Document convertStringToXMLDocument(String xmlString) throws UnsupportedEncodingException {
        System.out.println(StringEscapeUtils.unescapeJava(xmlString));
        xmlString = StringEscapeUtils.unescapeXml(xmlString);
        xmlString = new String(xmlString.getBytes(), "UTF-8");
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}