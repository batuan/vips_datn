package org.prepare.data;

public class DataStandard {

    String xpath;
    Double fontSizeAbsolute;
    Double linkNumAbsolute;
    Double interactionSize;
    Double innerTextLength;
    Double innerHTMLLength;
    Double imgSize;
    Double fontSize;
    Double imgNum;
    Double blockCenterX;
    Double blockCenterY;
    Double blockRectHeight;
    Double blockRectWidth;
    Double fontWeight;
    Double jaccard;

    public DataStandard() {
    }

    public DataStandard(String xpath, Double fontSizeAbsolute, Double linkNumAbsolute,
                        Double interactionSize, Double innerTextLength, Double imgSize,
                        Double blockRectWidth, Double fontSize, Double imgNum, Double blockCenterX,
                        Double blockRectHeight, Double blockCenterY,
                        Double fontWeight, Double innerHTMLLength,  Double jaccard) {
        this.xpath = xpath;
        this.fontSizeAbsolute = fontSizeAbsolute;
        this.linkNumAbsolute = linkNumAbsolute;
        this.interactionSize = interactionSize;
        this.innerTextLength = innerTextLength;
        this.imgSize = imgSize;
        this.blockRectWidth = blockRectWidth;
        this.fontSize = fontSize;
        this.imgNum = imgNum;
        this.blockCenterX = blockCenterX;
        this.blockRectHeight = blockRectHeight;
        this.blockCenterY = blockCenterY;
        this.fontWeight = fontWeight;
        this.innerHTMLLength = innerHTMLLength;
        this.jaccard = jaccard;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public Double getFontSizeAbsolute() {
        return fontSizeAbsolute;
    }

    public void setFontSizeAbsolute(Double fontSizeAbsolute) {
        this.fontSizeAbsolute = fontSizeAbsolute;
    }

    public Double getLinkNumAbsolute() {
        return linkNumAbsolute;
    }

    public void setLinkNumAbsolute(Double linkNumAbsolute) {
        this.linkNumAbsolute = linkNumAbsolute;
    }

    public Double getInteractionSize() {
        return interactionSize;
    }

    public void setInteractionSize(Double interactionSize) {
        this.interactionSize = interactionSize;
    }

    public Double getInnerTextLength() {
        return innerTextLength;
    }

    public void setInnerTextLength(Double innerTextLength) {
        this.innerTextLength = innerTextLength;
    }

    public Double getImgSize() {
        return imgSize;
    }

    public void setImgSize(Double imgSize) {
        this.imgSize = imgSize;
    }

    public Double getBlockRectWidth() {
        return blockRectWidth;
    }

    public void setBlockRectWidth(Double blockRectWidth) {
        this.blockRectWidth = blockRectWidth;
    }

    public Double getFontSize() {
        return fontSize;
    }

    public void setFontSize(Double fontSize) {
        this.fontSize = fontSize;
    }

    public Double getImgNum() {
        return imgNum;
    }

    public void setImgNum(Double imgNum) {
        this.imgNum = imgNum;
    }

    public Double getBlockCenterX() {
        return blockCenterX;
    }

    public void setBlockCenterX(Double blockCenterX) {
        this.blockCenterX = blockCenterX;
    }

    public Double getBlockRectHeight() {
        return blockRectHeight;
    }

    public void setBlockRectHeight(Double blockRectHeight) {
        this.blockRectHeight = blockRectHeight;
    }

    public Double getBlockCenterY() {
        return blockCenterY;
    }

    public void setBlockCenterY(Double blockCenterY) {
        this.blockCenterY = blockCenterY;
    }

    public Double getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(Double fontWeight) {
        this.fontWeight = fontWeight;
    }

    public Double getJaccard() {
        return jaccard;
    }

    public void setJaccard(Double jaccard) {
        this.jaccard = jaccard;
    }

    public Double getInnerHTMLLength() {
        return innerHTMLLength;
    }

    public void setInnerHTMLLength(Double innerHTMLLength) {
        this.innerHTMLLength = innerHTMLLength;
    }
}
