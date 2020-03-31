package org.prepare.data;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class ContructorData {

    public void readXMLData(String filename) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(filename);
        /* if file not exists then exit */
        if (!file.exists()) {
            System.out.println(" File not exists !!!");
            System.exit(0);
        }
        /* read file xml */
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        doc.getDocumentElement().normalize();
        PagesRoot pagesRoot = new PagesRoot();
        pagesRoot.setPageRectHeight(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectHeight")));
        pagesRoot.setPageRectWidth(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectWidth")));
        pagesRoot.setPageRectTop(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectTop")));
        pagesRoot.setPageRectLeft(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectLeft")));
        pagesRoot.setTitle(doc.getDocumentElement().getAttribute("PageTitle"));
        pagesRoot.setWindowHeight(Double.valueOf(doc.getDocumentElement().getAttribute("WindowHeight")));
        pagesRoot.setWindowWidth(Double.valueOf(doc.getDocumentElement().getAttribute("WindowWidth")));
        pagesRoot.setUrl(doc.getDocumentElement().getAttribute("Url"));
        /* Node layout body */
        Node nodeLayOutBody = doc.getDocumentElement().getChildNodes().item(1);
        String xpath = "";
        Double fontSize = Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("FontSize").getNodeValue());
        Double linkTextLen = Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("LinkTextLen").getNodeValue());
        Double linkNum = Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("LinkNum").getNodeValue());
        Double containImg = Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("ContainImg").getNodeValue());
        Double containP = Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("ContainP").getNodeValue());
        Double objectRectLeft =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("ObjectRectLeft").getNodeValue());
        Double objectRectTop =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("ObjectRectTop").getNodeValue());
        Double objectRectHeight =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("ObjectRectHeight").getNodeValue());
        Double objectRectWidth =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("ObjectRectWidth").getNodeValue());
        Double fontWeight =  Double.valueOf(100);
        String fw = nodeLayOutBody.getAttributes().getNamedItem("FontWeight").getNodeValue();
        if ( fw != null && !Utility.isNumericRegex(fw)) {
            if (fw.equals("normal")) {
                fontWeight = 400.0;
            }
            if (fw.equals("bold")) {
                fontWeight = 700.0;
            }
        } else {
            fontWeight = Double.valueOf(fontWeight);
        }
        Double textLen = Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("TextLen").getNodeValue());
        Double isImage = Boolean.valueOf(nodeLayOutBody.getAttributes().getNamedItem("TextLen").getNodeValue()) ? 1.0 : 0.0;
        DataBlock blockBody = new DataBlock("", fontSize, linkTextLen, linkNum, containImg, containP, objectRectLeft,
                    objectRectTop, objectRectHeight, objectRectWidth, fontWeight, textLen, isImage, "", "");
        pagesRoot.setBlockBody(blockBody);
        pagesRoot.setElements(blocksData(nodeLayOutBody));


        System.out.println(pagesRoot);

    }

    public ArrayList<DataBlock> blocksData(Node node) {
        ArrayList<DataBlock> dataBlocks = new ArrayList<>();
        if (node.hasChildNodes()) {
            int sizeNodeList = node.getChildNodes().getLength();
            for (int i = 0; i < sizeNodeList; i++) {
                Node tmp = node.getChildNodes().item(i);
                if (tmp.getNodeName() != null && !tmp.getNodeName().equals("#text")) {
                    dataBlocks.addAll(blocksData(tmp));
                }
            }
        } else {
            dataBlocks.add(convertNodeToBlockData(node));
        }
        return dataBlocks;
    }


    public DataBlock convertNodeToBlockData(Node node) {

        String xpath = node.getAttributes().getNamedItem("xPath").getNodeValue();
        Double fontSize = Double.valueOf(node.getAttributes().getNamedItem("FontSize").getNodeValue());
        Double linkTextLen = Double.valueOf(node.getAttributes().getNamedItem("LinkTextLen").getNodeValue());
        Double linkNum = Double.valueOf(node.getAttributes().getNamedItem("LinkNum").getNodeValue());
        Double containImg = Double.valueOf(node.getAttributes().getNamedItem("ContainImg").getNodeValue());
        Double containP = Double.valueOf(node.getAttributes().getNamedItem("ContainP").getNodeValue());
        Double objectRectLeft =  Double.valueOf(node.getAttributes().getNamedItem("ObjectRectLeft").getNodeValue());
        Double objectRectTop =  Double.valueOf(node.getAttributes().getNamedItem("ObjectRectTop").getNodeValue());
        Double objectRectHeight =  Double.valueOf(node.getAttributes().getNamedItem("ObjectRectHeight").getNodeValue());
        Double objectRectWidth =  Double.valueOf(node.getAttributes().getNamedItem("ObjectRectWidth").getNodeValue());
        Double fontWeight =  Double.valueOf(100);
        String fw = node.getAttributes().getNamedItem("FontWeight").getNodeValue();
        if ( fw != null && !Utility.isNumericRegex(fw)) {
            if (fw.equals("normal")) {
                fontWeight = 400.0;
            }
            if (fw.equals("bold")) {
                fontWeight = 700.0;
            }
        } else {
            fontWeight = Double.valueOf(fontWeight);
        }
        Double textLen = Double.valueOf(node.getAttributes().getNamedItem("TextLen").getNodeValue());
        Double isImage = Boolean.valueOf(node.getAttributes().getNamedItem("TextLen").getNodeValue()) ? 1.0 : 0.0;
        String content = node.getAttributes().getNamedItem("Content").getNodeValue();
        DataBlock block = new DataBlock(xpath, fontSize, linkTextLen, linkNum, containImg, containP, objectRectLeft,
                objectRectTop, objectRectHeight, objectRectWidth, fontWeight, textLen, isImage, content, "");

        return block;
    }



    public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException {
        ContructorData app = new ContructorData();
        app.readXMLData("data1/test.xml");
    }
}
