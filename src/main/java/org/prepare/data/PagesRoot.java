package org.prepare.data;

import java.util.ArrayList;

public class PagesRoot {

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
