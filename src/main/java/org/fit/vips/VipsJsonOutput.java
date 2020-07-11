package org.fit.vips;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.ElementBox;
import org.fit.cssbox.layout.Viewport;
import org.w3c.dom.Element;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;

/**
 * Class handle result with json file
 * @author ThaiTuan
 */
public class VipsJsonOutput {

    public static final double HEADER_HEIGHT = 131.4;
    public static final double FOOTER_HEIGHT = 452.4;

    private JsonArray jsonArray = null;
    private boolean _escapeOutput = true;
    private int _pDoC = 0;
    private int _order = 1;
    private String _filename = "VIPSResult";
    private double pageWidth = 0.0;
    private double pageHeight = 0.0;
    private double windowHeight = 0.0;
    public int get_pDoC() {
        return _pDoC;
    }
    String[] listXPath;

    public void set_filename(String _filename) {
        this._filename = _filename;
    }

    public void set_pDoC(int _pDoC) {
        this._pDoC = _pDoC;
    }
    public void setListXPath(String[] list) { this.listXPath = list; }


    /**
     * Khởi tạo
     * @param _pDoC
     * @param listXPath
     */

    public VipsJsonOutput(int _pDoC, String[] listXPath) {
        this.set_pDoC(_pDoC);
        this.setListXPath(listXPath);
    }

    /**
     * @param node
     * @return sourcode of node
     */
    private String getSource(Element node) {
        String content = "";
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StringWriter buffer = new StringWriter();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(node), new StreamResult(buffer));
            content = buffer.toString().replaceAll("\n", "");
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return content;
    }

    public void writeJson(VisualStructure visualStructure, Viewport pageViewport) {
        try {
            this.pageWidth = pageViewport.getWidth();
            this.pageHeight = pageViewport.getHeight();
            this.windowHeight = pageViewport.getContentHeight();

            jsonArray = new JsonArray();
            JsonObject root = new JsonObject();
            String pageTitle = pageViewport.getRootElement().getOwnerDocument().getElementsByTagName("title").item(0).getTextContent();
            root.addProperty("PageTitle", pageTitle);
            root.addProperty("Url", pageViewport.getRootBox().getBase().toString());
            root.addProperty("WindowWidth", String.valueOf(pageViewport.getContentWidth()));
            root.addProperty("WindowHeight", String.valueOf(pageViewport.getContentHeight()));
            root.addProperty("PageRectTop", String.valueOf(pageViewport.getAbsoluteContentY()));
            root.addProperty("PageRectLeft", String.valueOf(pageViewport.getAbsoluteContentX()));
            root.addProperty("PageRectWidth", String.valueOf(pageViewport.getContentWidth()));
            root.addProperty("PageRectHeight", String.valueOf(pageViewport.getContentHeight()));
            root.addProperty("neworder", "0");
            root.addProperty("order", String.valueOf(pageViewport.getOrder()));
            root.addProperty("order", String.valueOf(pageViewport.getOrder()));
            jsonArray.add(root);
            getNormalizedFeatures(visualStructure);
            writeJsonVisualBlocks(visualStructure);
            //normalizedPage(jsonArray);
            FileWriter fileWriter;
            String directory = System.getProperty("user.dir") + "/";
            BufferedWriter writer = new BufferedWriter(new FileWriter(directory + _filename + ".json"));
            for (JsonElement json: jsonArray) {
                String result = json.toString();
                result = result.replaceAll("&gt;", ">");
                result = result.replaceAll("&lt;", "<");
                result = result.replaceAll("&quot;", "\"");
                writer.write(result);
                writer.newLine();
            }
            writer.flush();
            writer.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    double normalizedFontSize = 13.0;
    double normalizedFontWeight = 700.0;
    double normalizedInnerTextLength = 100.0;
    double normalizedInnerHtmlLength =  100.0;
    double normalizedImgNum = 20.0;
    //imgsize normal bang kich thuoc cua trang web
    double normalizedLinkNum = 100.0;
    double normalizedLinkTextLength = 100.0;
    double normalizedInteractionNum = 20.0;
    double normalizedInteractionSize = 20.0;
    double normalizedFormNum = 20.0;
    double normalizedFormSize = 20.0;
    double normalizedOptionNum = 20.0;
    double normalizedOptionTextLength = 20.0;
    double normalizedTableNum = 20.0;
    double normalizedParaNum = 20.0;

    private void getNormalizedFeatures(VisualStructure rootVisualStruct){
        double fontSize = rootVisualStruct.getFontSize();
        normalizedFontSize = (fontSize !=0 )? fontSize : 13.0;

        double fontWeight = caculatorFontWeight(rootVisualStruct.getFontWeight());
        normalizedFontWeight = (fontWeight !=0 )? fontWeight : 700.0;

        double innerTextLength = rootVisualStruct.getTextLength();
        normalizedInnerTextLength = (innerTextLength != 0)? innerTextLength : 100.0;

        StringBuilder src = new StringBuilder();
        for (VipsBlock block : rootVisualStruct.getNestedBlocks()) {
            ElementBox elementBox = block.getElementBox();
            if (elementBox == null)
                continue;
            if (!elementBox.getNode().getNodeName().equals("Xdiv") &&
                    !elementBox.getNode().getNodeName().equals("Xspan"))
                src.append(getSource(elementBox.getElement()));
            else
                src.append(elementBox.getText());
        }

        normalizedInnerHtmlLength =  src.length();

        double imgNum = rootVisualStruct.containImg();
        normalizedImgNum = (imgNum != 0)? imgNum : 20.0;
        //imgsize normal bang kich thuoc cua trang web

        double linkNum = rootVisualStruct.getLinkNum();
        normalizedLinkNum = (linkNum != 0)? linkNum : 100.0;

        double linkTextLength = rootVisualStruct.getLinkTextLength();
        normalizedLinkTextLength = (linkTextLength != 0)? linkTextLength : 100.0;
        double interationNum = rootVisualStruct.get_iterationNum();
        normalizedInteractionNum = (interationNum != 0)? interationNum : 20.0;
        double interactionSize = rootVisualStruct.get_iterationSize();
        normalizedInteractionSize = (interactionSize != 0)? interactionSize : 20.0;

        double formNum = rootVisualStruct.get_formNum();
        normalizedFormNum = (formNum != 0) ? formNum : 20.0;

        double formSize = rootVisualStruct.get_formSize();
        normalizedFormSize = (formSize != 0) ? formSize : 20.0;
        double optionNum = rootVisualStruct.get_optionNum();
        normalizedOptionNum = (optionNum != 0)? optionNum : 20.0;
        double optionTextLength = rootVisualStruct.get_optionTextLength();
        normalizedOptionTextLength = (optionTextLength != 0)? optionTextLength : 20.0;

        double tableNum = rootVisualStruct.get_tableNum();
        normalizedTableNum = (tableNum != 0)? tableNum : 20.0;

        double paramNum = rootVisualStruct.get_paraNum();
        normalizedParaNum = (paramNum != 0)? paramNum : 20.0;
    }

    private JsonArray normalizedPage(JsonArray jsonArray) {
        JsonArray jsonNormalLized = new JsonArray();
        for(JsonElement element : jsonArray){
            JsonObject object = (JsonObject) element;
            if(object.has("PageTitle")) continue;
            else {
                double FontWeight =  Double.parseDouble(object.getAsJsonObject("FontWeight").toString());
            }
        }
        return new JsonArray();
    }


    private void writeJsonVisualBlocks(VisualStructure visualStructure) {

        /** Nếu trong các visualstructure mà DoC < pDoC không có children thì sao?
         * thì chúng ta sẽ in ra ngay cái visual này, còn không thì tiếp tục segmentation
         **/
        int maxDoC = visualStructure.getMaxDocVisualChildren();
        if (_pDoC > visualStructure.getDoC()) {
            if (visualStructure.getChildrenVisualStructures().size() == 0){
                // in ra children có child visual struct = 0
                jsonArray.add(nodeLayoutObject(visualStructure));
            }
            // tiếp tục segmentation.
            for (VisualStructure child : visualStructure.getChildrenVisualStructures()) {
                writeJsonVisualBlocks(child);
            }
        } else {
            // write output with DoC > PDoC and stop segmentation
            jsonArray.add(nodeLayoutObject(visualStructure));
        }
    }

    /**
     * Trong bài báo có một yêu cầu là phải normalize các biến. và gọi chúng là các relative content features.
     * công thức: relative = feature/totalfeature
     * dưới đây là định nghĩa tổng các feature để chúng ta normallize, mình nghĩ có thể làm python nhanh hơn
     * nhưng thôi cứ làm java để tiện khi thu thập dữ liệu
     */

    double maxFontSize = 0.0;
    double maxFontWeight = 0.0;
    double maxInnerTextLength = 0.0;
    double maxInnerHtmlLength = 0.0;
    double totalImgNum = 0.0;
    double totalLinkNum = 0.0;
    // imgSize sẽ normalize bằng cách chia cho kích thước trang.
    double maxLinkTextLength = 0.0;
    double totalInteractionNum = 0.0;
    double maxInteractionSize = 0.0;
    double totalFormNum = 0.0;
    double maxFormSize = 0.0;
    double totalOptionNum = 0.0;
    double maxOptionTextLength = 0.0;
    double totalTableNum = 0.0;
    double totalParaNum = 0.0;

    /**
     * Hàm này tạo 1 json object với đầu vào là 1 visual structure
     * @param visualStructure
     * @return
     */
    JsonObject nodeLayoutObject(VisualStructure visualStructure) {
        JsonObject nodeLayout = new JsonObject();
        nodeLayout.addProperty("DoC", String.valueOf(visualStructure.getDoC()));

        double fontSize = (double) visualStructure.getFontSize();
        nodeLayout.addProperty("FontSize_O", fontSize);
        fontSize = fontSize / normalizedFontSize;
        nodeLayout.addProperty("FontSize_R", fontSize);
        if (fontSize > maxFontSize) maxFontSize = fontSize;

        nodeLayout.addProperty("FontWeight", String.valueOf(visualStructure.getFontWeight()));

        double fontWeight = caculatorFontWeight(visualStructure.getFontWeight());
        nodeLayout.addProperty("FontWeight_O", fontWeight);
        fontWeight = fontWeight/normalizedFontWeight;
        nodeLayout.addProperty("FontWeight_R", fontWeight);

        double innerTextLength = visualStructure.getTextLength();
        nodeLayout.addProperty("InnerTextLength_O", innerTextLength);
        innerTextLength = innerTextLength / normalizedInnerTextLength;
        nodeLayout.addProperty("InnerTextLength_R", innerTextLength);

        double imgNum = (double) visualStructure.containImg();
        nodeLayout.addProperty("ImgNum_O", imgNum);
        imgNum = imgNum / normalizedImgNum;
        totalImgNum += imgNum;
        nodeLayout.addProperty("ImgNum_R", imgNum);

        double imgSize = (double) visualStructure.maxImageSize();
        imgSize = imgSize/(this.pageWidth * this.pageHeight);
        nodeLayout.addProperty("ImgSize", imgSize);
        nodeLayout.addProperty("IsImg", String.valueOf(visualStructure.isImg()));

        double linkNum = (double) visualStructure.getLinkNum();
        nodeLayout.addProperty("LinkNum_O", linkNum);
        linkNum = linkNum / normalizedLinkNum;
        totalLinkNum += linkNum;
        nodeLayout.addProperty("LinkNum_R", linkNum);

        double linkTextLength = (double) visualStructure.getLinkTextLength();
        linkTextLength = linkTextLength / normalizedLinkTextLength;
        nodeLayout.addProperty("LinkTextLength_O", linkTextLength);
        if (linkTextLength > maxLinkTextLength) maxLinkTextLength = linkTextLength;
        nodeLayout.addProperty("LinkTextLength_R", linkTextLength);

        double interactionNum = (double) visualStructure.get_iterationNum();
        nodeLayout.addProperty("InteractionNum_O", interactionNum);
        interactionNum = interactionNum / normalizedInteractionNum;
        totalInteractionNum += interactionNum;
        nodeLayout.addProperty("InteractionNum_R", interactionNum);

        double interactionSize = (double) visualStructure.get_iterationSize();
        nodeLayout.addProperty("InteractionSize_O", interactionSize);
        interactionSize = interactionSize/normalizedInteractionSize;
        if (interactionSize > maxInteractionSize) maxInteractionSize = interactionSize;
        nodeLayout.addProperty("InteractionSize_R", interactionSize);

        double formNum = (double) visualStructure.get_formNum();
        nodeLayout.addProperty("FormNum_O", formNum);
        formNum = formNum / normalizedFormNum;
        totalFormNum += formNum;
        nodeLayout.addProperty("FormNum_R", formNum);

        double formSize = (double) visualStructure.get_formSize();
        nodeLayout.addProperty("FormSize_O", fontSize);
        formSize = formSize / normalizedFormSize;
        if (formSize > maxFormSize) maxFormSize = formSize;
        nodeLayout.addProperty("FormSize_R", fontSize);

        double optionNum = (double) visualStructure.get_optionNum();
        nodeLayout.addProperty("OptionNum_O", optionNum);
        optionNum = optionNum / normalizedOptionNum;
        totalOptionNum += optionNum;
        nodeLayout.addProperty("OptionNum_R", optionNum);

        double optionTextLength = (double) visualStructure.get_optionTextLength();
        nodeLayout.addProperty("OptionTextLength_O", optionTextLength);
        optionTextLength = optionTextLength / normalizedOptionTextLength;
        if (optionTextLength > maxOptionTextLength) maxOptionTextLength = optionTextLength;
        nodeLayout.addProperty("OptionTextLength_R", optionTextLength);

        double tableNum = (double) visualStructure.get_tableNum();
        nodeLayout.addProperty("TableNum_O", tableNum);
        tableNum = tableNum/normalizedTableNum;
        totalTableNum += tableNum;
        nodeLayout.addProperty("TableNum_R", tableNum);

        double paraNum = visualStructure.containP();
        nodeLayout.addProperty("ParaNum_O", paraNum);
        paraNum = paraNum / normalizedParaNum;
        totalParaNum += paraNum;
        nodeLayout.addProperty("ParaNum_R", paraNum);

        _order++;
        String src = "";
        String content = "";
        String xPath = "";
        String XContent = "";
        xPath = visualStructure.get_xPath();

        for (VipsBlock block : visualStructure.getNestedBlocks()) {
            ElementBox elementBox = block.getElementBox();

            if (elementBox == null)
                continue;

            if (!elementBox.getNode().getNodeName().equals("Xdiv") &&
                    !elementBox.getNode().getNodeName().equals("Xspan"))
                src += getSource(elementBox.getElement());
            else
                src += elementBox.getText();

            content += elementBox.getText() + " ";
            XContent += block.get_xPath() + "(+)";
        }
        double innerHtmlLength = src.length();
        nodeLayout.addProperty("InnerHtmlLength_O", innerHtmlLength);
        innerHtmlLength = innerHtmlLength / normalizedInnerHtmlLength;
        nodeLayout.addProperty("InnerHtmlLength_R", innerHtmlLength);

        double[] spatialFeatures = caculatorBlockRect(visualStructure.getWidth(), visualStructure.getHeight(), visualStructure.getX(), visualStructure.getY());
        nodeLayout.addProperty("BlockCenterX", spatialFeatures[0]);
        nodeLayout.addProperty("BlockCenterY", spatialFeatures[1]);
        nodeLayout.addProperty("BlockRectWidth", spatialFeatures[2]);
        nodeLayout.addProperty("BlockRectHeight", spatialFeatures[3]);

        nodeLayout.addProperty("ContainTable", String.valueOf(visualStructure.containTable()));
        //nodeLayout.addProperty("ContainP", String.valueOf(visualStructure.containP()));
        nodeLayout.addProperty("TextLen", String.valueOf(visualStructure.getTextLength()));
        Box parentBox = visualStructure.getNestedBlocks().get(0).getBox().getParent();
        nodeLayout.addProperty("DOMCldNum", String.valueOf(parentBox.getNode().getChildNodes().getLength()));
        nodeLayout.addProperty("BgColor", visualStructure.getBgColor());
        nodeLayout.addProperty("ObjectRectLeft", String.valueOf(visualStructure.getX()));
        nodeLayout.addProperty("ObjectRectTop", String.valueOf(visualStructure.getY()));
        nodeLayout.addProperty("ObjectRectWidth", String.valueOf(visualStructure.getWidth()));
        nodeLayout.addProperty("ObjectRectHeight", String.valueOf(visualStructure.getHeight()));
        nodeLayout.addProperty("ID", visualStructure.getId());
        nodeLayout.addProperty("order", String.valueOf(_order));
        nodeLayout.addProperty("FrameSourceIndex", String.valueOf(visualStructure.getFrameSourceIndex()));
        nodeLayout.addProperty("SourceIndex", visualStructure.getSourceIndex());

        nodeLayout.addProperty("Content", content);
        nodeLayout.addProperty("xPath", xPath);
        nodeLayout.addProperty("XContent", XContent);
        nodeLayout.addProperty("AllClassName", visualStructure.getClassName());
        int label = this.setLabel(xPath);
        nodeLayout.addProperty("label", String.valueOf(label));
        return nodeLayout;
    }

    int setLabel(String xPath) {
        for (String path : this.listXPath){
            if (xPath.contains(path)) {
                return 1;
            }
        }
        return 0;
    }

    public double[] caculatorBlockRect(double width, double height, double offsetLeft, double offsetTop) {

        //Hoành độ của Tâm của khối tính bằng offsetLeft + width /2
        double BlockCenterX = offsetLeft + width/2;
        BlockCenterX = BlockCenterX/this.pageWidth;

        //Tung độ của tâm của khối đuọc tính bằng offsetTop + height /2
        double BlockCenterY = offsetTop + height/2;

        // chuẩn hoá lại blcok rect width = width / pagewidth
        double BlockRectWidth = width/this.pageWidth;

        // BlockRectHeight modified as
        double BlockRectHeight = height/this.windowHeight;

        // BlockCenterY is modified as
        if (BlockCenterY < HEADER_HEIGHT) {
            BlockCenterY = BlockCenterY/(2*HEADER_HEIGHT);
        } else if ((HEADER_HEIGHT < BlockCenterY) && (BlockCenterY < this.pageHeight - FOOTER_HEIGHT)) {
            BlockCenterY = 0.5;
        } else {
            BlockCenterY = 1 - (this.pageHeight - BlockCenterY)/(2*FOOTER_HEIGHT);
        }

        double[] spatialFeatures = {BlockCenterX, BlockCenterY, BlockRectWidth, BlockRectHeight};
        return spatialFeatures;
    }
    //double maxFontWeight = 0.0;
    public double caculatorFontWeight(String fw) {
        double weight = 0.0;
        if ( fw != null ) { //&& !Utility.isNumericRegex(fw)
            if (fw.equals("normal") || fw.equals("lighter")) {
                weight = 400.0;
                if (weight > maxFontWeight) maxFontWeight = weight;
                return weight;
            } else if (fw.equals("bold") || fw.equals("bolder")) {
                weight = 700.0;
                if (weight > maxFontWeight) maxFontWeight = weight;
                return  weight;
            } else {
                weight = Double.parseDouble(fw);
                if (weight > maxFontWeight) maxFontWeight = weight;
                return weight;
            }
        } else {
            if (400.0 > weight) maxFontWeight = weight;
            return 400.0;
        }
    }
}
