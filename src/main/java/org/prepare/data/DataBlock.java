package org.prepare.data;

public class DataBlock {
    String idParent;
    String xpath;
    Double fontsize;
    Double linkTextLen;
    Double linkNum;
    String src;
//    Double PageRectHeight;
//    Double PageRectLeft;
//    Double PageRectTop;
//    Double PageRectWidth;
//    Double title;
//    Double WindowHeight;
//    Double WindowWidth;
    Double containImg;
    Double containP;
    Double objectRectLeft;
    Double objectRectTop;
    Double objectRectHeight;
    Double objectRectWidth;
    Double fontWeight; // normal === 400
    Double textLen;
    Double isImage;
    String content;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    Integer order;
    String label;


    public DataBlock() {
    }

    public DataBlock(String xpath, Double fontsize, Double linkTextLen,
                     Double linkNum, Double containImg, Double containP,
                     Double objectRectLeft, Double objectRectTop, Double objectRectHeight,
                     Double objectRectWidth, Double fontWeight, Double textLen, Double isImage,
                     String content, String src, String label) {
        this.xpath = xpath;
        this.fontsize = fontsize;
        this.linkTextLen = linkTextLen;
        this.linkNum = linkNum;
        this.containImg = containImg;
        this.containP = containP;
        this.objectRectLeft = objectRectLeft;
        this.objectRectTop = objectRectTop;
        this.objectRectHeight = objectRectHeight;
        this.objectRectWidth = objectRectWidth;
        this.fontWeight = fontWeight;
        this.textLen = textLen;
        this.isImage = isImage;
        this.content = content;
        this.src = src;
        this.label = label;
    }

    public boolean isEqual(DataBlock data) {
        if (checkSize(objectRectWidth - data.getObjectRectWidth(),objectRectHeight - data.getObjectRectHeight())
                && content.trim().equals(data.getContent().trim())) {
            return true;
        }
        return false;
    }

    public boolean checkSize(double minusWidth, double minusHeight) {
        if (Math.abs(minusWidth) > 20 || Math.abs(minusHeight) > 20) {
            return false;
        }
        return true;
    }

    public boolean checkPoision(Double minusLeft, Double minusTop) {
        if (Math.abs(minusLeft) > 150 || Math.abs(minusTop) > 150 ) {
            return false;
        }
        return true;
    }


    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public Double getFontsize() {
        return fontsize;
    }

    public void setFontsize(Double fontsize) {
        this.fontsize = fontsize;
    }

    public Double getLinkTextLen() {
        return linkTextLen;
    }

    public void setLinkTextLen(Double linkTextLen) {
        this.linkTextLen = linkTextLen;
    }

    public Double getLinkNum() {
        return linkNum;
    }

    public void setLinkNum(Double linkNum) {
        this.linkNum = linkNum;
    }

    public Double getContainImg() {
        return containImg;
    }

    public void setContainImg(Double containImg) {
        this.containImg = containImg;
    }

    public Double getContainP() {
        return containP;
    }

    public void setContainP(Double containP) {
        this.containP = containP;
    }

    public Double getObjectRectLeft() {
        return objectRectLeft;
    }

    public void setObjectRectLeft(Double objectRectLeft) {
        this.objectRectLeft = objectRectLeft;
    }

    public Double getObjectRectTop() {
        return objectRectTop;
    }

    public void setObjectRectTop(Double objectRectTop) {
        this.objectRectTop = objectRectTop;
    }

    public Double getObjectRectHeight() {
        return objectRectHeight;
    }

    public void setObjectRectHeight(Double objectRectHeight) {
        this.objectRectHeight = objectRectHeight;
    }

    public Double getObjectRectWidth() {
        return objectRectWidth;
    }

    public void setObjectRectWidth(Double objectRectWidth) {
        this.objectRectWidth = objectRectWidth;
    }

    public Double getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(Double fontWeight) {
        this.fontWeight = fontWeight;
    }

    public Double getTextLen() {
        return textLen;
    }

    public void setTextLen(Double textLen) {
        this.textLen = textLen;
    }

    public Double getIsImage() {
        return isImage;
    }

    public void setIsImage(Double isImage) {
        this.isImage = isImage;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent;
    }

    @Override
    public String toString() {
        return "DataBlock{" +
                "xpath='" + xpath + '\'' +
                ", fontsize=" + fontsize +
                ", linkTextLen=" + linkTextLen +
                ", linkNum=" + linkNum +
                ", containImg=" + containImg +
                ", containP=" + containP +
                ", objectRectLeft=" + objectRectLeft +
                ", objectRectTop=" + objectRectTop +
                ", objectRectHeight=" + objectRectHeight +
                ", objectRectWidth=" + objectRectWidth +
                ", fontWeight=" + fontWeight +
                ", textLen=" + textLen +
                ", isImage=" + isImage +
                ", content='" + content + '\'' +
                ", src='" + src + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

}
