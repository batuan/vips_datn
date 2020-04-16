package org.prepare.data;

import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PagesRoot {

    public static final double HEADER_HEIGHT = 131.4;
    public static final double FOOTER_HEIGHT = 452.4;

    Double PageRectHeight;
    Double PageRectLeft;
    Double PageRectTop;
    Double PageRectWidth;
    String title;
    String url;
    Double WindowHeight;
    Double WindowWidth;
    DataBlock blockBody;
    ArrayList<DataBlock> elements;
    ArrayList<DataStandard> elementStandard;
    Set<String> idParentMainSimilarJacard;
    String[] xpahtPositive;

    public PagesRoot() {
    }

    public PagesRoot(PagesRoot content) {
        this(content.getPageRectHeight(), content.getPageRectLeft(),  content.getPageRectTop(),
                content.getPageRectWidth(), content.getTitle(), content.getWindowHeight(), content.getUrl(),
                content.getWindowWidth(),  content.getBlockBody(), (ArrayList<DataBlock>) content.getElements().clone());
    }

    public Object clone() {
        PagesRoot pagesRoot = null;
        try {
            pagesRoot = (PagesRoot) super.clone();
        } catch (CloneNotSupportedException e) {
            pagesRoot = new PagesRoot(
                    this.getPageRectHeight(), this.getPageRectLeft(), this.getPageRectTop(),
                    this.getPageRectWidth(), this.getTitle(), this.getWindowHeight(), this.getUrl(),
                    this.getWindowWidth(), this.getBlockBody(), this.getElements());
        }
        return pagesRoot;
    }

    public PagesRoot(Double pageRectHeight, Double pageRectLeft, Double pageRectTop,
                        Double pageRectWidth, String title, Double windowHeight, String url,
                        Double windowWidth, DataBlock blockBody, ArrayList<DataBlock> elements) {

        PageRectHeight = pageRectHeight;
        PageRectLeft = pageRectLeft;
        PageRectTop = pageRectTop;
        PageRectWidth = pageRectWidth;
        this.title = title;
        this.url = url;
        WindowHeight = windowHeight;
        WindowWidth = windowWidth;
        this.blockBody = blockBody;
        this.elements = elements;

    }

    public void cleanBlock(ArrayList<DataBlock> block) {
        int size2 = block.size();
        for (int i = 0; i < size2; i++) {
            int size1 = this.elements.size();
            for (int j = 0; j < size1; j++) {
                if (block.get(i).isEqual(elements.get(j))) {
                    elements.remove(j);
                    break;
                }
            }
        }
    }

    public void writeToFile(String pathFile, boolean modeWriteAppend) throws IOException {
        File file = new File(pathFile);
        if (!file.exists()) {
            System.out.println("File do not exists !!!");
            System.exit(0);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, modeWriteAppend));
        int size = elementStandard.size();
        StringBuffer strings = new StringBuffer();
        for (int i = 0 ;i < size; i++) {
            JsonObject object = elementStandard.get(i).convertToJsonObject();
            strings.append(object.toString());
            if (i != (size - 1)) {
                strings.append("\n");
            }
        }
        bufferedWriter.write(strings.toString());
        bufferedWriter.close();
    }

    public void setLabelPositive(String ...xpathMatch) {
        for (DataBlock it: elements) {
            String xpath = it.getXpath();
            for (String match: xpathMatch) {
                if (xpath.contains(match)) {
                    it.setLabel("1");
                    break;
                }
            }
        }
    }

    public void properties() {
        this.setLabelPositive(this.xpahtPositive);
        this.elementStandard = this.calculatorDataStandara(this.elements);
        /*System.out.println(this.elements);*/
    }

    public ArrayList<DataStandard> calculatorDataStandara(ArrayList<DataBlock> elements) {
        ArrayList<DataStandard> dataStandards = new ArrayList<>();
        for (DataBlock it: elements) {
            if (it.getContent().trim().equals("")) {
                continue;
            }
            String [] xpaths = it.getXpath().split("/");
            if (xpaths[xpaths.length - 1].equals("img")) {
                continue;
            }
            if (it.getXpath().contains("/a[0]/#text")) {
                continue;
            }

            Double blockCenterX = (it.getObjectRectLeft() + it.getObjectRectWidth()) / (2 * blockBody.getObjectRectWidth());
            Double blockRectWidth = it.getObjectRectWidth() / blockBody.getObjectRectWidth();
            Double blockRectHeigh = it.getObjectRectHeight() / WindowHeight;
            Double blockCenterY = (it.getObjectRectTop() + it.getObjectRectHeight()) / (2 * blockBody.getObjectRectHeight());
            ;
            if (blockCenterY < HEADER_HEIGHT) {
                blockCenterY = blockCenterY / (2 * HEADER_HEIGHT);
            } else if ((HEADER_HEIGHT < blockCenterY) &&
                    (blockCenterY < (blockBody.getObjectRectHeight() - FOOTER_HEIGHT))) {
                blockCenterY = 0.5;
            } else {
                blockCenterY = 1 - (blockBody.getObjectRectHeight() - blockCenterY) / (2 * FOOTER_HEIGHT);
            }
            Double fontSizeAbsolute = it.getFontsize() / blockBody.getFontsize();
            Double fontSize = it.getFontsize() / blockBody.getFontsize();
            Double linkNum = it.getLinkNum() / blockBody.getLinkNum();
            Double interactionSize = (it.getObjectRectHeight() * it.getObjectRectWidth()) / (blockBody.getObjectRectHeight() * blockBody.getObjectRectWidth());
            Double innerTextLength = it.getTextLen() / blockBody.getTextLen();
            Double imgSize = it.getIsImage() == 1.0 ? (it.getObjectRectHeight() * it.getObjectRectWidth()) / (PageRectHeight * PageRectWidth) : 0.0;
            Double imgNum = it.getContainImg() / blockBody.getContainImg();
            Double fontWeight = it.getFontWeight();
            Double innerHTMLLength = Double.valueOf(it.getSrc().length())/* / Double.valueOf(blockBody.getSrc().length())*/;
            String xpath = it.getXpath();
            String label = it.getLabel();
            String jaccard = "None";
            if (idParentMainSimilarJacard == null) {
                this.idParentMainSimilarJacard = new HashSet<>();
            }
            if (this.idParentMainSimilarJacard.contains(it.getIdParent())) {
                jaccard = "main";
            } else {
                double score = this.simpleJaccard(title, it.content);
                if (score > 0.82) {
                    jaccard = "main";
                    this.idParentMainSimilarJacard.add(it.getIdParent());
                }
            }

            String content = StandardStringUtility.getStringFeatures(it.getContent());
            DataStandard dataStandard = new DataStandard(xpath, label, content, fontSizeAbsolute, linkNum,
                    interactionSize, innerTextLength, imgSize, blockRectWidth, blockRectHeigh,
                    fontSize, imgNum, blockCenterX, blockCenterY, fontWeight, innerHTMLLength, jaccard);

            dataStandards.add(dataStandard);
        }
        return dataStandards;
    }

    public double simpleJaccard(String in1, String in2) {
        String listIn1[] = in1.trim().toLowerCase().split("\\s+");
        String listIn2[] = in2.trim().toLowerCase().split("\\s+");
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(listIn1));
        set.addAll(Arrays.asList(listIn2));
        int size = set.size();
        int count = 0;
        for (String it1: listIn1) {
            for (String it2: listIn2) {
                if (it2.equals(it1)) {
                    count++;
                }
            }
        }
        return (double)count/(double) size;
    }

    public Double getPageRectHeight() {
        return PageRectHeight;
    }

    public void setPageRectHeight(Double pageRectHeight) {
        PageRectHeight = pageRectHeight;
    }

    public Double getPageRectLeft() {
        return PageRectLeft;
    }

    public void setPageRectLeft(Double pageRectLeft) {
        PageRectLeft = pageRectLeft;
    }

    public Double getPageRectTop() {
        return PageRectTop;
    }

    public void setPageRectTop(Double pageRectTop) {
        PageRectTop = pageRectTop;
    }

    public Double getPageRectWidth() {
        return PageRectWidth;
    }

    public void setPageRectWidth(Double pageRectWidth) {
        PageRectWidth = pageRectWidth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getWindowHeight() {
        return WindowHeight;
    }

    public void setWindowHeight(Double windowHeight) {
        WindowHeight = windowHeight;
    }

    public Double getWindowWidth() {
        return WindowWidth;
    }

    public void setWindowWidth(Double windowWidth) {
        WindowWidth = windowWidth;
    }

    public DataBlock getBlockBody() {
        return blockBody;
    }

    public void setBlockBody(DataBlock blockBody) {
        this.blockBody = blockBody;
    }

    public ArrayList<DataBlock> getElements() {
        return elements;
    }

    public void setElements(ArrayList<DataBlock> elements) {
        this.elements = elements;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<DataStandard> getElementStandard() {
        return elementStandard;
    }

    public void setElementStandard(ArrayList<DataStandard> elementStandard) {
        this.elementStandard = elementStandard;
    }

    public String[] getXpahtPositive() {
        return xpahtPositive;
    }

    public void setXpathPositive(String ...xpahtPositive) {
        this.xpahtPositive = xpahtPositive;
    }

    @Override
    public String toString() {
        return "PagesRoot{" +
                "PageRectHeight=" + PageRectHeight +
                ", PageRectLeft=" + PageRectLeft +
                ", PageRectTop=" + PageRectTop +
                ", PageRectWidth=" + PageRectWidth +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", WindowHeight=" + WindowHeight +
                ", WindowWidth=" + WindowWidth +
                ", blockBody=" + blockBody +
                ", elements=" + elements +
                ", standrad=" + elementStandard +
                '}';
    }
}
