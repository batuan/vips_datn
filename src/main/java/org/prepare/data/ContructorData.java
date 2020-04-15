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
    PagesRoot pagesRoot;

    public ContructorData() {
        pagesRoot = new PagesRoot();
    }

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
        this.pagesRoot.setPageRectHeight(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectHeight")));
        this.pagesRoot.setPageRectWidth(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectWidth")));
        this.pagesRoot.setPageRectTop(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectTop")));
        this.pagesRoot.setPageRectLeft(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectLeft")));
        this.pagesRoot.setTitle(doc.getDocumentElement().getAttribute("PageTitle"));
        this.pagesRoot.setWindowHeight(Double.valueOf(doc.getDocumentElement().getAttribute("WindowHeight")));
        this.pagesRoot.setWindowWidth(Double.valueOf(doc.getDocumentElement().getAttribute("WindowWidth")));
        this.pagesRoot.setUrl(doc.getDocumentElement().getAttribute("Url"));
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
        String src = /*nodeLayOutBody.getAttributes().getNamedItem("SRC").getNodeValue();*/ "";

        DataBlock blockBody = new DataBlock("", fontSize, linkTextLen, linkNum, containImg, containP, objectRectLeft,
                    objectRectTop, objectRectHeight, objectRectWidth, fontWeight, textLen, isImage, "", src, "");

        this.pagesRoot.setBlockBody(blockBody);
        this.pagesRoot.setElements(blocksData(nodeLayOutBody));

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
        String src = node.getAttributes().getNamedItem("SRC").getNodeValue();
        String label = node.getAttributes().getNamedItem("Label") != null ? node.getAttributes().getNamedItem("Label").getNodeValue() : "-1";

        DataBlock block = new DataBlock(xpath, fontSize, linkTextLen, linkNum, containImg, containP, objectRectLeft,
                objectRectTop, objectRectHeight, objectRectWidth, fontWeight, textLen, isImage, content, src, label);

        return block;
    }


    public PagesRoot getPagesRoot() {
        return pagesRoot;
    }

    public void setPagesRoot(PagesRoot pagesRoot) {
        this.pagesRoot = pagesRoot;
    }

    /* batdongsan: /#document/html/body/form[4]/div[3]/div[1]/div[0]/div[0] */
    /* dothi: /#document/html/body/form[9]/div[0]/div[0]/div[1]/div[2] */
    /* alonhadat: /#document/html/body/div[1]/div[2]/div[0]/div[0] */

    public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException {
        int _case = 6;
        String folder_name = "";
        String[] xpath = null;
        if (_case == 0) {
            folder_name =  "batdongsan";
            xpath = new String[]{"/#document/html/body/form[4]/div[3]/div[1]/div[0]/div[0]"};
        } else if (_case == 1) {
            folder_name = "alonhadat";
            xpath = new String[]{"/#document/html/body/div[1]/div[2]/div[0]/div[0]"};
        } else if (_case == 2) {
            folder_name = "dothi";
            xpath = new String[]{"/#document/html/body/form[9]/div[0]/div[0]/div[1]/div[2]"};
        } else if (_case == 3) {
            folder_name = "123nhadatviet";
            xpath = new String[]{"/#document/html/body/div[1]/div[2]/div[0]/div[0]"};
        } else if (_case == 4) {
            folder_name = "bds123";
            xpath = new String[]{"/#document/html/body/div[1]/div[0]/div[0]/section[1]/div[0]/article"};
        } else if (_case == 5) {
            folder_name = "chothuenha";
            xpath = new String[]{"/#document/html/body/div[3]/main[0]/section[2]/section[0]/article[0]", "/#document/html/body/div[3]/main[0]/section[2]/section[0]/article[1]",
            "/#document/html/body/div[3]/main[0]/section[2]/section[0]/article[3]", "/#document/html/body/div[3]/main[0]/section[2]/section[0]/article[5]", "/#document/html/body/div[3]/main[0]/section[2]/section[0]/article[7]",
            "/#document/html/body/div[3]/main[0]/section[2]/section[0]/article[11]"};
        } else if (_case == 6) {
            folder_name = "test";
            xpath = new String[] {
                    /* anphu */
                    "/#document/html/body/div[0]/form[11]/section[0]/div[0]/div[2]",
                    /* bannha */
                    "/#document/html/body/div[3]/div[0]/div[1]/div[0]/div[0]/main[0]/div[0]/div[0]/section[0]",
                    "/#document/html/body/div[3]/div[0]/div[1]/div[0]/div[0]/main[0]/div[0]/div[0]/section[4]",
                    "/#document/html/body/div[3]/div[0]/div[1]/div[0]/div[0]/main[0]/div[0]/div[0]/section[5]/div[0]",
                    /* nha data 24*/
                    "/#document/html/body/form[10]/div[1]/div[0]/div[2]/div[0]/div[0]", "/#document/html/body/div[0]/div[1]/div[2]/div[0]/div[4]",
                    /* 123nhadat */
                    "/#document/html/body/div[4]/div[0]/div[0]/div[1]", "/#document/html/body/div[4]/div[0]/div[0]/div[2]", "/#document/html/body/div[4]/div[0]/div[0]/div[22]",
                    "/#document/html/body/div[4]/div[0]/div[0]/div[24]",
                    /* diaoc online */
                    "/#document/html/body/div[8]/div[0]/div[1]/div[1]/div[0]/div[1]",
                    /* kenhbds */
                    "/#document/html/body/div[1]/div[3]/div[0]/",
                    /* homedy */
                    "/#document/html/body/div[7]/div[1]/div[0]/div[0]", "/#document/html/body/div[7]/div[1]/div[0]/div[1]/div[0]/div[0]/div[1]",
                    /* dinhgia */
                    "/#document/html/body/div[0]/div[1]/div[2]/div[0]/div[4]"
            };
        }

        File folder = new File("data-link/" + folder_name);
        ArrayList<String> listPath = new ArrayList<>();
        for(File it: folder.listFiles()) {
            if (it.getName().contains(folder_name))
                listPath.add(it.getName());
        }
        int count = 0;
        for(String it: listPath) {
            ContructorData app = new ContructorData();
            app.readXMLData("data-link/" + folder_name + "/" + it);
            app.getPagesRoot().setXpahtPositive(xpath);
            app.getPagesRoot().properties();
            app.getPagesRoot().writeToFile("data-link/" + folder_name + "/result.json", true);
        }
    }
}
