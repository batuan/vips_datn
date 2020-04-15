package org.prepare.data;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Test
{
    public static void main(String[] args) throws UnsupportedEncodingException {
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