package org.prepare.data;


import com.google.gson.JsonObject;

public class DataStandard {

    String xpath;
    String label;
    String content;
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
    String contact = "False";
    String architecture  = "False";
    String price  = "False";
    String address  = "False";
    String suface  = "False";
    String time  = "False";
    String iterator = "False";
    Double order;
    String jaccard;

    public DataStandard() {
    }

    public DataStandard(String xpath, String label, String content,  Double fontSizeAbsolute, Double linkNumAbsolute,
                        Double interactionSize, Double innerTextLength, Double imgSize,
                        Double blockRectWidth, Double blockRectHeight, Double fontSize, Double imgNum,
                        Double blockCenterX, Double blockCenterY,
                        Double fontWeight, Double innerHTMLLength,  String jaccard) {
        this.xpath = xpath;
        this.label = label;
        this.content = content;
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

    public boolean isIterator(DataStandard data) {
        if (fontSizeAbsolute == data.getFontSizeAbsolute()
                && checkrErorSize(blockCenterX, data.blockCenterX) && checkrErorSize(blockRectWidth, blockRectWidth) && (Math.abs(order - data.order) < 5)) {
                return true;
        }
        return false;
    }

    public boolean checkrErorSize(double a, double b) {
        if (Math.abs(a - b) <=  0.0012) {
            return true;
        }
        return false;
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

    public String getJaccard() {
        return jaccard;
    }

    public void setJaccard(String jaccard) {
        this.jaccard = jaccard;
    }

    public Double getInnerHTMLLength() {
        return innerHTMLLength;
    }

    public void setInnerHTMLLength(Double innerHTMLLength) {
        this.innerHTMLLength = innerHTMLLength;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuface() {
        return suface;
    }

    public void setSuface(String suface) {
        this.suface = suface;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIterator() {
        return iterator;
    }

    public void setIterator(String iterator) {
        this.iterator = iterator;
    }

    public Double getOrder() {
        return order;
    }

    public void setOrder(Double order) {
        this.order = order;
    }

    public JsonObject convertToJsonObject() {
        JsonObject object = new JsonObject();
        object.addProperty("xpath", xpath);
        object.addProperty("label", label);
        object.addProperty("jaccard", jaccard);
        object.addProperty("fontSizeAbsolute", fontSizeAbsolute);
        object.addProperty("linkNumAbsolute", linkNumAbsolute);
        object.addProperty("interactionSize", interactionSize);
        object.addProperty("innerTextLength", innerTextLength);
        object.addProperty("innerHTMLLength", innerHTMLLength);
        object.addProperty("imgSize", imgSize);
        object.addProperty("fontSize", fontSize);
        object.addProperty("imgNum", imgNum);
        object.addProperty("blockCenterX", blockCenterX);
        object.addProperty("blockCenterY", blockCenterY);
        object.addProperty("blockRectHeight", blockRectHeight);
        object.addProperty("blockRectWidth", blockRectWidth);
        object.addProperty("fontWeight", fontWeight);
        object.addProperty("contact", contact);
        object.addProperty("architecture", architecture);
        object.addProperty("address", address);
        object.addProperty("suface", suface);
        object.addProperty("time", time);
        object.addProperty("price", price);
        object.addProperty("iterator", iterator);
        object.addProperty("order", order);
        object.addProperty("content", content);
        
        return object;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DataStandard{" +
                "xpath='" + xpath + '\'' +
                ", label='" + label + '\'' +
                ", content='" + content + '\'' +
                ", fontSizeAbsolute=" + fontSizeAbsolute +
                ", linkNumAbsolute=" + linkNumAbsolute +
                ", interactionSize=" + interactionSize +
                ", innerTextLength=" + innerTextLength +
                ", innerHTMLLength=" + innerHTMLLength +
                ", imgSize=" + imgSize +
                ", fontSize=" + fontSize +
                ", imgNum=" + imgNum +
                ", blockCenterX=" + blockCenterX +
                ", blockCenterY=" + blockCenterY +
                ", blockRectHeight=" + blockRectHeight +
                ", blockRectWidth=" + blockRectWidth +
                ", fontWeight=" + fontWeight +
                ", jaccard=" + jaccard +
                '}';
    }
}
