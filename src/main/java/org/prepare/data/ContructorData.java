package org.prepare.data;

import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class ContructorData {

    ArrayList<PagesRoot> pagesRoot;
    ArrayList<DataStandard> data;

    public ContructorData(int numberPages) {
        pagesRoot = new ArrayList<>();
        data = new ArrayList<>();
    }

    public void readXMLData(String filename, int index) throws ParserConfigurationException, IOException, SAXException {
        if (this.pagesRoot.size() < index + 1) {
            this.pagesRoot.add(new PagesRoot());
        }
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
        this.pagesRoot.get(index).setPageRectHeight(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectHeight")));
        this.pagesRoot.get(index).setPageRectWidth(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectWidth")));
        this.pagesRoot.get(index).setPageRectTop(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectTop")));
        this.pagesRoot.get(index).setPageRectLeft(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectLeft")));
        this.pagesRoot.get(index).setTitle(doc.getDocumentElement().getAttribute("PageTitle"));
        this.pagesRoot.get(index).setWindowHeight(Double.valueOf(doc.getDocumentElement().getAttribute("WindowHeight")));
        this.pagesRoot.get(index).setWindowWidth(Double.valueOf(doc.getDocumentElement().getAttribute("WindowWidth")));
        this.pagesRoot.get(index).setUrl(doc.getDocumentElement().getAttribute("Url"));
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
        Integer order =  Integer.valueOf(nodeLayOutBody.getAttributes().getNamedItem("order").getNodeValue());
        Double fontWeight =  Double.valueOf(100);
        String id = nodeLayOutBody.getAttributes().getNamedItem("ID").getNodeValue();
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
        blockBody.setOrder(order);
        blockBody.setIdParent(id);
        this.pagesRoot.get(index).setBlockBody(blockBody);
        this.pagesRoot.get(index).setElements(blocksData(nodeLayOutBody));

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
        String idParent = node.getParentNode().getAttributes().getNamedItem("ID").getNodeValue();
        Integer order = Integer.valueOf(node.getAttributes().getNamedItem("order").getNodeValue());
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
        block.setIdParent(idParent);
        block.setOrder(order);
        return block;
    }


    public ArrayList<PagesRoot> getPagesRoot() {
        return pagesRoot;
    }


    public void setPagesRoot(ArrayList<PagesRoot> pagesRoot) {
        this.pagesRoot = pagesRoot;
    }

    /* batdongsan: /#document/html/body/form[4]/div[3]/div[1]/div[0]/div[0] */
    /* dothi: /#document/html/body/form[9]/div[0]/div[0]/div[1]/div[2] */
    /* alonhadat: /#document/html/body/div[1]/div[2]/div[0]/div[0] */
    public void writeToFile(String pathFile, boolean modeWriteAppend) throws IOException {
        File file = new File(pathFile);
        if (!file.exists()) {
            System.out.println("File do not exists !!!");
            System.exit(0);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, modeWriteAppend));
        int size = data.size();
        StringBuffer strings = new StringBuffer();
        for (int i = 0 ;i < size; i++) {
            JsonObject object = data.get(i).convertToJsonObject();
            strings.append(object.toString());
            if (i != (size - 1)) {
                strings.append("\n");
            }
        }
        bufferedWriter.write(strings.toString());
        bufferedWriter.close();
    }

    public void cleanData() {
        boolean isDone = false;
        int index = 0;
        int size = data.size();
        while (index < size && !isDone) {
            boolean check = false;
            for (int j = index + 1; j < size - 1; j++) {
                if (data.get(index).content.equals(data.get(j).content)) {
                    check = true;
                    data.remove(j);
                    size = data.size();
                }
            }
            if (check) {
                data.remove(index);
            } else {
                index++;
            }
            /*if (index == size) {
                isDone = true;
            }*/
        }
    }

    public void updateProperty() {
        double maxFontWeight = 0;
        double maxHTML = 0;
        double maxOrder = 0;
        int size = this.data.size();
        for (int i = 0; i < size; i++) {
            if (maxFontWeight < this.data.get(i).getFontWeight()) {
                maxFontWeight = this.data.get(i).getFontWeight();
            }
            if (maxHTML < this.data.get(i).getInnerHTMLLength()) {
                maxHTML = this.data.get(i).getInnerHTMLLength();
            }
            if (maxOrder < this.data.get(i).getOrder()) {
                maxOrder = this.data.get(i).getOrder();
            }
        }
        for (int i = 0; i < size; i++) {
            double order = this.data.get(i).getOrder();
            double html = this.data.get(i).getInnerHTMLLength();
            double fontWeight =this.data.get(i).getFontWeight();
            this.data.get(i).setOrder(order/maxOrder);
            this.data.get(i).setInnerHTMLLength(html/maxHTML);
            this.data.get(i).setFontWeight(fontWeight/maxFontWeight);
        }
    }

    public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException {
        int _case = 0;
        String folder_name = "";
        String[] xpath = null;
        if (_case == 0) {
            folder_name =  "batdongsan";
            xpath = new String[]{"/form[4]/div[3]/div[1]/div[0]/div[0]"};
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
                    "/div[0]/form[11]/section[0]/div[0]/div[2]",
                    /* bannha */
                    "/div[3]/div[0]/div[1]/div[0]/div[0]/main[0]/div[0]/div[0]/section[0]",
                    "/div[3]/div[0]/div[1]/div[0]/div[0]/main[0]/div[0]/div[0]/section[4]",
                    "/div[3]/div[0]/div[1]/div[0]/div[0]/main[0]/div[0]/div[0]/section[5]/div[0]",
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
        int size = listPath.size();
        ContructorData app = new ContructorData(size);
        for(int i = 0; i < size; i++) {
            app.readXMLData("data-link/" + folder_name + "/" + listPath.get(i), i);
           /* app.getPagesRoot().setXpathPositive(xpath);*/
         /*   app.getPagesRoot().properties();
            app.getPagesRoot().writeToFile("data-link/" + folder_name + "/result-1.json", true);*/
        }

        PagesRoot page1 = new PagesRoot(app.getPagesRoot().get(0));
        int sizePages = app.getPagesRoot().size();
        for (int i = 0; i < sizePages - 1; i++) {
            app.getPagesRoot().get(i).cleanBlock(app.getPagesRoot().get(i + 1).getElements());
        }
        app.getPagesRoot().get(sizePages - 1).cleanBlock(page1.getElements());

        for (int i = 0; i < sizePages; i++) {
            app.getPagesRoot().get(i).setXpathPositive(xpath);
            app.getPagesRoot().get(i).properties();
            for (DataStandard it: app.getPagesRoot().get(i).getElementStandard()) {
                app.getData().add(it);
            }
//            app.getPagesRoot().get(i).writeToFile("data-link/" + folder_name + "/result-1.json", true);
        }
        app.cleanData();
        app.updateProperty();
        app.writeToFile("data-link/" + folder_name + "/result-2.json", true);
    }

    public ArrayList<DataStandard> getData() {
        return data;
    }

    public void setData(ArrayList<DataStandard> data) {
        this.data = data;
    }
}
