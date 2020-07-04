package org.prepare.data;

import com.google.gson.JsonObject;
import org.fit.vips.Vips;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class DataAnalyze {
    private PagesRoot pagesRoot;
    private ArrayList<DataStandard> data;
    public void readXMLData(String fileName) throws ParserConfigurationException, IOException, SAXException {
        this.pagesRoot = new PagesRoot();
        this.data = new ArrayList<>();
        File file = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
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
        String xpath = "";//nodeLayOutBody.getAttributes().getNamedItem("xPath").getNodeValue();
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
        String src = "";/*nodeLayOutBody.getAttributes().getNamedItem("SRC").getNodeValue();*/

        Double optionNum =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("OptionNum").getNodeValue());
        Double optionTextLength =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("OptionTextLength").getNodeValue());
        Double formNum =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("FormNum").getNodeValue());
        Double formSize =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("FormSize").getNodeValue());
        Double interactionNum =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("InteractionNum").getNodeValue());
        Double interactionSize =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("InteractionSize").getNodeValue());
        Double tableNum =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("TableNum").getNodeValue());
        Double paraNum =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("ParaNum").getNodeValue());

        DataBlock blockBody = new DataBlock("", fontSize, linkTextLen, linkNum, src, containImg, containP,
                objectRectLeft, objectRectTop, objectRectHeight, objectRectWidth, fontWeight,
                textLen, isImage, "", formNum, formSize, interactionNum, interactionSize, optionNum,
                optionTextLength, paraNum, tableNum);
        blockBody.setOrder(order);
        blockBody.setIdParent(id);
        pagesRoot.setBlockBody(blockBody);
        ArrayList<DataBlock> dataBlocks = blocksData(nodeLayOutBody);
        pagesRoot.setElements(dataBlocks);
        pagesRoot.propertiesNoLabel();

        for (DataStandard it: pagesRoot.elementStandard) {
            data.add(it);
        }
        updateProperty();
    }


    public void readXMLDataTest(String fileName) throws ParserConfigurationException, IOException, SAXException {
        this.pagesRoot = new PagesRoot();
        this.data = new ArrayList<>();
        File file = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        pagesRoot.setPageRectHeight(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectHeight")));
        pagesRoot.setPageRectWidth(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectWidth")));
        pagesRoot.setPageRectTop(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectTop")));
        pagesRoot.setPageRectLeft(Double.valueOf(doc.getDocumentElement().getAttribute("PageRectLeft")));
        pagesRoot.setTitle(doc.getDocumentElement().getAttribute("PageTitle"));
        pagesRoot.setWindowHeight(Double.valueOf(doc.getDocumentElement().getAttribute("WindowHeight")));
        pagesRoot.setWindowWidth(Double.valueOf(doc.getDocumentElement().getAttribute("WindowWidth")));
        pagesRoot.setUrl(doc.getDocumentElement().getAttribute("Url"));

        Node nodeLayOutBody = doc.getDocumentElement().getChildNodes().item(1);
        String xpath = "";//nodeLayOutBody.getAttributes().getNamedItem("xPath").getNodeValue();
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
        String src = "";/*nodeLayOutBody.getAttributes().getNamedItem("SRC").getNodeValue();*/

        Double optionNum =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("OptionNum").getNodeValue());
        Double optionTextLength =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("OptionTextLength").getNodeValue());
        Double formNum =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("FormNum").getNodeValue());
        Double formSize =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("FormSize").getNodeValue());
        Double interactionNum =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("InteractionNum").getNodeValue());
        Double interactionSize =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("InteractionSize").getNodeValue());
        Double tableNum =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("TableNum").getNodeValue());
        Double paraNum =  Double.valueOf(nodeLayOutBody.getAttributes().getNamedItem("ParaNum").getNodeValue());

        DataBlock blockBody = new DataBlock("", fontSize, linkTextLen, linkNum, src, containImg, containP,
                objectRectLeft, objectRectTop, objectRectHeight, objectRectWidth, fontWeight,
                textLen, isImage, "", formNum, formSize, interactionNum, interactionSize, optionNum,
                optionTextLength, paraNum, tableNum);
        blockBody.setOrder(order);
        blockBody.setIdParent(id);
        pagesRoot.setBlockBody(blockBody);

        NodeList nodeList = doc.getElementsByTagName("LayoutNode");
        ArrayList<DataBlock> dataBlocks = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            DataBlock dataBlock = convertNodeToBlockData(node);
            if (dataBlock == null) {
                continue;
            } else {
                dataBlocks.add(dataBlock);
            }
        }
        pagesRoot.setElements(dataBlocks);
        pagesRoot.propertiesNoLabel();
        for (DataStandard it: pagesRoot.elementStandard) {
            data.add(it);
        }
        updateProperty();
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
        String xpath;
        try{
            xpath = node.getAttributes().getNamedItem("xPath").getNodeValue();
        } catch (Exception e) {
            return null;
        }
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

    public PagesRoot getPagesRoot() {
        return this.pagesRoot;
    }


    public ArrayList<DataStandard> getData() {
        return data;
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        if (args.length != 2)
        {
            System.err.println("We've just two argument - web address of page arg[0] and name of folder arg[1]");
            System.exit(0);
        }
        String url = args[0];//"https://batdongsan.com.vn/ban-nha-rieng-pho-do-quang-phuong-trung-hoa-4/pl-chinh-chu-50m2-x-7-tang-gia-13-5-ty-lh-0853998888-pr24403336";
        String folder = "/Users/batuan/Documents/hoctap/datn/test" + "/" + args[1];
        System.out.println(folder);

        runVIPS(folder, url);

        DataAnalyze dataAnalyze = new DataAnalyze();
        dataAnalyze.readXMLDataTest(folder +"/VIPS.xml");
        dataAnalyze.writeToFile( folder + "/ketqua.json", true);
    }
    public static void main1(String[] args) throws ParserConfigurationException, IOException, SAXException {
        if (args.length != 2)
        {
            System.err.println("We've just two argument - web address of page arg[0] and name of folder arg[1]");
            System.exit(0);
        }
        String url = args[0];//"https://batdongsan.com.vn/ban-nha-rieng-pho-do-quang-phuong-trung-hoa-4/pl-chinh-chu-50m2-x-7-tang-gia-13-5-ty-lh-0853998888-pr24403336";
        String folder = "/Users/batuan/Documents/hoctap/datn/test" + "/" + args[1];
        System.out.println(folder);

        DataAnalyze dataAnalyze = new DataAnalyze();
        dataAnalyze.readXMLData(folder + "/VIPS.xml");
        dataAnalyze.writeToFile(folder + "/ketqua.json", true);
    }

    static void runVIPS(String folder, String url) throws IOException {
        Vips vips = new Vips();
        File file = new File(folder);
        file.mkdir();
        new File(folder+"/ketqua.json").createNewFile();
        vips.setOutputFileName("VIPS");
        vips.setOutputDirectoryName(folder);
        // disable graphics output
        vips.setSizeDimensionWidth(1420);
        vips.setSizeDimensionHeight(980);
        vips.setSizeTresholdHeight(350);
        vips.setSizeTresholdWidth(400);
        vips.enableGraphicsOutput(false);
        // disable output to separate folder (no necessary, it's default value is false)
        vips.enableOutputToFolder(false);
        vips.enableOutputEscaping(true);
        // set permitted degree of coherence
        vips.setPredefinedDoC(8);
        vips.setNumberOfIterations(3);
        vips.startSegmentation(url);
    }
}
