package org.prepare.data;

import java.util.ArrayList;

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

    public PagesRoot() {
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

    public void properties() {

    }

    public ArrayList<DataStandard> calculatorDataStandara(ArrayList<DataBlock> elements) {
        ArrayList<DataStandard> dataStandards = new ArrayList<>();
        for (DataBlock it: elements) {
            DataStandard data = new DataStandard();
            Double blockCenterX = (it.getObjectRectLeft() + it.getObjectRectWidth())/(2 * blockBody.getObjectRectWidth());
            Double blockRectWidth = it.getObjectRectWidth() / blockBody.getObjectRectWidth();
            Double blockRectHeigh = it.getObjectRectHeight() / WindowHeight;
            Double blockCenterY = (it.getObjectRectTop() + it.getObjectRectHeight())/(2 * blockBody.getObjectRectHeight());;
            if (blockCenterY < HEADER_HEIGHT) {
                blockCenterY = blockCenterY / (2 * HEADER_HEIGHT);
            } else if ((HEADER_HEIGHT < blockCenterY) &&
                    (blockCenterY < (blockBody.getObjectRectHeight() - FOOTER_HEIGHT))) {
                blockCenterY = 0.5;
            } else {
                blockCenterY = 1 - (blockBody.getObjectRectHeight() - blockCenterY) / (2 * FOOTER_HEIGHT);
            }
            Double fontSize = it.getFontsize() / blockBody.getFontsize();
            Double linkNum = it.getLinkNum() / blockBody.getLinkNum();
            Double interactionSize = (it.getObjectRectHeight() * it.getObjectRectWidth()) * (blockBody.getObjectRectHeight() * blockBody.getObjectRectWidth());
            Double innerTextLength = it.getTextLen();
            Double imgSize = it.getIsImage() == 1.0 ? (it.getObjectRectHeight() * it.getObjectRectWidth())/(PageRectHeight * PageRectWidth) : 0.0;
            Double imgNum = it.getContainImg();
            Double fontWeight = it.getFontWeight();

        }
        return dataStandards;
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
                '}';
    }
}
