/*
 * Tomas Popela, 2012
 * VIPS - Visual Internet Page Segmentation
 * Module - VipsBlock.java
 */

package org.fit.vips;

import java.util.ArrayList;
import java.util.List;

import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.ElementBox;
import org.fit.cssbox.layout.TextBox;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static org.joox.JOOX.$;

/**
 * Class that represents block on page.
 * @author Tomas Popela
 *
 */
public class VipsBlock {

	//rendered Box, that corresponds to DOM element
	private Box _box = null;
	//children of this node
	private List<VipsBlock> _children = null;
	//node id
	private int _id = 0;
	//node's Degree Of Coherence
	private int _DoC = 0;

	//number of images in node
	private int _containImg = 0;
	//if node is image
	private boolean _isImg = false;
	//if node is visual block
	private boolean _isVisualBlock = false;
	//if node contains table
	private boolean _containTable = false;
	//number of paragraphs in node
	private int _containP = 0;
	//if node was already divided
	private boolean _alreadyDivided = false;
	//if node can be divided
	private boolean _isDividable = true;

	private String _bgColor = null;

	private int _frameSourceIndex = 0;
	private int _sourceIndex = 0;
	private int _tmpSrcIndex = 0;
	private int _order = 0;


	//length of text in node
	private int _textLen = 0;
	//length of text in links in node
	private int _linkTextLen = 0;
	// LinkNum of
	private int LinkNum = 0;
	// FormNum
	private int _formNum = 0;
	//FormSize
	private int _formSize = 0;
	//OptionNum
	private int _optionNum = 0;
	//OptionTextLength
	private int _optionTextLength = 0;
	//InterationNUm
	private int _iterationNum = 0;
	//InterractionSize
	private int _iterationSize = 0;

	//TableNum
	private int _tableNum = 0;
	//ParaNum
	private int _paraNum = 0;

	private String _xPath = "";

	public String get_xPath() {
		return _xPath;
	}

	public void set_xPath(String _xPath) {
		this._xPath = _xPath;
	}

	public VipsBlock() {
		this._children = new ArrayList<VipsBlock>();
	}

	public VipsBlock(int id, VipsBlock node) {
		this._children = new ArrayList<VipsBlock>();
		setId(id);
		addChild(node);
	}

	/**
	 * Sets block as visual block
	 * @param isVisualBlock Value
	 */
	public void setIsVisualBlock(boolean isVisualBlock)
	{
		_isVisualBlock = isVisualBlock;
		checkProperties();
	}

	/**
	 * Checks if block is visual block
	 * @return True if block if visual block, otherwise false
	 */
	public boolean isVisualBlock()
	{
		return _isVisualBlock;
	}

	/**
	 * Checks the properties of visual block
	 */
	private void checkProperties()
	{
		checkIsImg();
		checkContainImg(this);
		/* new custom */
		//updateXPath(this);
		//testXpath(this);
		/*---------------*/
		checkContainTable(this);
		checkContainP(this);
		_linkTextLen = 0;
		LinkNum = 0;
		_textLen = 0;

		_formNum = 0;
		_formSize = 0;
		countFormNum(this);

		_optionNum = 0;
		_optionTextLength = 0;
		countOptionNum(this);

		_iterationNum = 0;
		_iterationSize = 0;
		countIterationNum(this);

		_tableNum = 0;
		countTabelNum(this);

		_paraNum = 0;
		countParaNum(this);
		countTextLength(this);
		countLinkTextLength(this);
		countLinkNum(this);
		setSourceIndex(this.getBox().getNode().getOwnerDocument());
	}

	private void countParaNum(VipsBlock vipsBlock) {
		if (vipsBlock.getBox().getNode().getNodeName().equals("p") || vipsBlock.getBox().getNode().getNodeName().equals("br")) {
			_paraNum += 1;
		}
		for (VipsBlock childVipsBlock: vipsBlock.getChildren())
			countLinkNum(childVipsBlock);
	}

	private void countTabelNum(VipsBlock vipsBlock) {
		if (vipsBlock.getBox().getNode().getNodeName().equals("table") ||
				vipsBlock.getBox().getNode().getNodeName().equals("tr") ||
				vipsBlock.getBox().getNode().getNodeName().equals("td"))
			this._tableNum += 1;
		else {
			if (vipsBlock.getBox().getNode().getAttributes() != null && vipsBlock.getBox().getNode().getAttributes().getNamedItem("class") != null) {
				String _class = vipsBlock.getBox().getNode().getAttributes().getNamedItem("class").getNodeValue();
				if (_class.contains("table") || _class.contains("bang") || _class.contains("tr") ||
						_class.contains("td")) {
					this._tableNum += 1;
				}
			}
		}
		for (VipsBlock childVipsBlock : vipsBlock.getChildren())
			checkContainTable(childVipsBlock);
	}

	private void countIterationNum(VipsBlock vipsBlock) {
		if (vipsBlock.getBox().getNode().getNodeName().equals("input") ||
				vipsBlock.getBox().getNode().getNodeName().equals("select")) {
			_iterationNum += 1;
			try {
				_iterationSize = Integer.parseInt(vipsBlock.getElementBox().getElement().getAttribute("size"));
			} catch (NumberFormatException e) {
				_iterationSize = 20; //gia tri mac dinh
			}
		}
		for (VipsBlock childVipsBlock: vipsBlock.getChildren())
			countOptionNum(childVipsBlock);
	}

	private void countOptionNum(VipsBlock vipsBlock) {
		if (vipsBlock.getBox().getNode().getNodeName().equals("option")) {
			_optionNum += 1;
			_optionTextLength = vipsBlock.getBox().getNode().getTextContent().length();
		}
		for (VipsBlock childVipsBlock: vipsBlock.getChildren())
			countOptionNum(childVipsBlock);
	}

	private void countFormNum(VipsBlock vipsBlock) {
		if (vipsBlock.getBox().getNode().getNodeName().equals("form")) {
			_formNum += 1;
			try {
				int size = Integer.parseInt(vipsBlock.getElementBox().getElement().getAttribute("size"));
				if (size > _formSize) {
					_formSize = size;
				}
			} catch (NumberFormatException e) {
				_formSize = 20;
			}
		}
		for (VipsBlock childVipsBlock: vipsBlock.getChildren())
			countFormNum(childVipsBlock);
	}

	/**
	 * Checks if visual block is an image.
	 */
	private void checkIsImg()
	{
		if (_box.getNode().getNodeName().equals("img"))
			_isImg = true;
		else
			_isImg = false;
	}

	/**
	 * Checks if visual block contains image.
	 * @param vipsBlock Visual block
	 */
	private void checkContainImg(VipsBlock vipsBlock)
	{
		if (vipsBlock.getBox().getNode().getNodeName().equals("img"))
		{
			vipsBlock._isImg = true;
			this._containImg++;
		}

		for (VipsBlock childVipsBlock : vipsBlock.getChildren())
			checkContainImg(childVipsBlock);
	}
	
	/**
	 *  getXpath visual structure node
	 *  @return string is xpath
	 */
	public String xPath(Node node) {
		if (node != null) {
			Node parent = node.getParentNode();
		    if (parent == null) {
		        return "/" + node.getNodeName();
		    }
		    int _index = 0;
		    int index = 0;
		    int size = parent.getChildNodes().getLength();
		    for (index = 0; index < size; index++) {
		    	Node tmp = parent.getChildNodes().item(index);
		    	if (tmp.equals(node)) {
		    		break;
				}
				if (!tmp.getNodeName().equals("#text")) {
					_index++;
				}
			}
		    if (/*parent.getNodeName().equals("body") || */parent.getNodeName().equals("html") || parent.getNodeName().equals("#document")) {
		    	return xPath(parent) + "/" + node.getNodeName();
			}
			String tmp = "[" + _index + "]/";
		    return xPath(parent) + tmp + node.getNodeName();
		}
		return "";
	}

	public void testXpath(VipsBlock vipsBlock) {
		if (vipsBlock.getBox().getNode() != null) {
			Element element = (Element) vipsBlock.getBox().getNode();
			_xPath = $(element).tag();
		}
	}

	public void updateXPath(VipsBlock vipsBlock) {
		if (vipsBlock.getBox().getNode() != null) {
			this._xPath = this.xPath(vipsBlock.getBox().getNode());
		}
		for (VipsBlock chVipsBlock: vipsBlock.getChildren())
			updateXPath(chVipsBlock);
	}
	
	public String getXPath() {
		return _xPath;
	}

	/**
	 * Checks if visual block contains table.
	 * @param vipsBlock Visual block
	 */
	private void checkContainTable(VipsBlock vipsBlock)
	{
		if (vipsBlock.getBox().getNode().getNodeName().equals("table"))
			this._containTable = true;
		else {
			if (vipsBlock.getBox().getNode().getAttributes() != null && vipsBlock.getBox().getNode().getAttributes().getNamedItem("class") != null) {
				String _class = vipsBlock.getBox().getNode().getAttributes().getNamedItem("class").getNodeValue();
				if (_class.contains("table") || _class.contains("bang")) {
					/*System.out.println(_class);*/
					this._containTable = true;
//					System.out.println(this._containTable);
				}
			}
		}
		for (VipsBlock childVipsBlock : vipsBlock.getChildren())
			checkContainTable(childVipsBlock);
	}

	/**
	 * Checks if visual block contains paragraph.
	 * @param vipsBlock Visual block
	 */
	private void checkContainP(VipsBlock vipsBlock)
	{
		if (vipsBlock.getBox().getNode().getNodeName().equals("p")||vipsBlock.getBox().getNode().getNodeName().equals("br"))
			this._containP++;

		for (VipsBlock childVipsBlock : vipsBlock.getChildren())
			checkContainP(childVipsBlock);
	}

	/**
	 * Counts length of text in links in visual block
	 * @param vipsBlock Visual block
	 */
	private void countLinkTextLength(VipsBlock vipsBlock)
	{
		if (vipsBlock.getBox().getNode().getNodeName().equals("a"))
		{
			_linkTextLen += vipsBlock.getBox().getText().length();

		}

		for (VipsBlock childVipsBlock : vipsBlock.getChildren())
			countLinkTextLength(childVipsBlock);
	}

	/**
	 * counts number link of block
	 * @param vipsBlock Visualblock
	 */
	private void countLinkNum(VipsBlock vipsBlock) {
		if (vipsBlock.getBox().getNode().getNodeName().equals("a")) {
			LinkNum += 1;
		}
		for (VipsBlock childVipsBlock: vipsBlock.getChildren())
			countLinkNum(childVipsBlock);
	}

	/**
	 * Count length of text in visual block
	 * @param vipsBlock Visual block
	 */
	private void countTextLength(VipsBlock vipsBlock)
	{
		_textLen = vipsBlock.getBox().getText().replaceAll("\n", "").length();
	}

	/**
	 * Adds new child to blocks children
	 * @param child New child
	 */
	public void addChild(VipsBlock child)
	{
		_children.add(child);
	}

	/**
	 * Gets all blocks children
	 * @return List of children
	 */
	public List<VipsBlock> getChildren()
	{
		return _children;
	}

	/**
	 * Sets block corresponding Box
	 * @param box Box
	 */
	public void setBox(Box box)
	{
		this._box = box;
	}

	/**
	 * Gets Box corresponding to the block
	 * @return Box
	 */
	public Box getBox()
	{
		return _box;
	}

	/**
	 * Gets ElementBox corresponding to the block
	 * @return ElementBox
	 */
	public ElementBox getElementBox()
	{
		if (_box instanceof ElementBox)
			return (ElementBox) _box;
		else
			return null;
	}

	/**
	 * Sets block's id
	 * @param id Id
	 */
	public void setId(int id)
	{
		this._id = id;
	}

	/**
	 * Gets blocks id
	 * @return Id
	 */
	public int getId()
	{
		return _id;
	}

	/**
	 * Returns block's degree of coherence DoC
	 * @return Degree of coherence
	 */
	public int getDoC()
	{
		return _DoC;
	}

	/**
	 * Sets block;s degree of coherence
	 * @param doC Degree of coherence
	 */
	public void setDoC(int doC)
	{
		this._DoC = doC;
	}

	/**
	 * Checks if block is dividable
	 * @return True if is dividable, otherwise false
	 */
	public boolean isDividable()
	{
		return _isDividable;
	}

	/**
	 * Sets dividability of block
	 * @param isDividable True if is dividable otherwise false
	 */
	public void setIsDividable(boolean isDividable)
	{
		this._isDividable = isDividable;
	}

	public int get_formNum() {
		return _formNum;
	}

	public int get_formSize() {
		return _formSize;
	}

	public int get_optionNum() {
		return _optionNum;
	}

	public int get_optionTextLength() {
		return _optionTextLength;
	}

	public int get_iterationNum() {
		return _iterationNum;
	}

	public int get_iterationSize() {
		return _iterationSize;
	}

	public int get_tableNum() {
		return _tableNum;
	}

	public int get_paraNum() {
		return _paraNum;
	}

	/**
	 * Checks if node was already divided
	 * @return True if was divided, otherwise false
	 */
	public boolean isAlreadyDivided()
	{
		return _alreadyDivided;
	}

	/**
	 * Sets if block was divided
	 * @param alreadyDivided True if block was divided, otherwise false
	 */
	public void setAlreadyDivided(boolean alreadyDivided)
	{
		this._alreadyDivided = alreadyDivided;
	}

	/**
	 * Checks if block is image
	 * @return True if block is image, otherwise false
	 */
	public boolean isImg()
	{
		return _isImg;
	}

	/**
	 * Checks if block contain images
	 * @return Number of images
	 */
	public int containImg()
	{
		return _containImg;
	}

	/**
	 * Checks if block contains table
	 * @return True if block contains table, otherwise false
	 */
	public boolean containTable()
	{
		return _containTable;
	}

	/**
	 * Gets length of text in block
	 * @return Length of text
	 */
	public int getTextLength()
	{
		return _textLen;
	}

	/**
	 * Gets length of text in links in block
	 * @return Length of links text
	 */
	public int getLinkTextLength()
	{
		return _linkTextLen;
	}

	public int getLinkNum() {
		return LinkNum;
	}

	/**
	 * Gets number of paragraphs in block
	 * @return Number of paragraphs
	 */
	public int containP()
	{
		return _containP;
	}

	/**
	 * Finds background color of element
	 * @param element Element
	 */
	private void findBgColor(Element element)
	{
		String backgroundColor = element.getAttribute("background-color");

		if (backgroundColor.isEmpty())
		{
			if (element.getParentNode() != null &&
			    !element.getTagName().equals("body"))
			{
				findBgColor((Element) element.getParentNode());
			}
			else
			{
				_bgColor = "#ffffff";
				return;
			}
		}
		else
		{
			_bgColor = backgroundColor;
			return;
		}
	}

	/**
	 * Gets background color of element
	 * @return Background color
	 */
	public String getBgColor()
	{
		if (_bgColor != null)
			return _bgColor;

		if (this.getBox() instanceof TextBox)
		{
			_bgColor = "#ffffff";
		}
		else
		{
			_bgColor = this.getElementBox().getStylePropertyValue("background-color");
		}


		if (_bgColor.isEmpty())
			findBgColor(this.getElementBox().getElement());

		return _bgColor;
	}

	/**
	 * Gets block's font size
	 * @return Font size
	 */
	public int getFontSize()
	{
		return this.getBox().getVisualContext().getFont().getSize();
	}

	/**
	 * Gets block's font weight
	 * @return Font weight
	 */
	public String getFontWeight()
	{
		String fontWeight = "";
		if (this.getBox() instanceof TextBox)
		{
			return fontWeight;
		}
		if (this.getElementBox().getStyle().getProperty("font-weight") != null) {
			fontWeight = this.getElementBox().getStyle().getProperty("font-weight").toString();
			return fontWeight;
		}
		if (this.getElementBox().getStylePropertyValue("font-weight") == null)
			return fontWeight;

		fontWeight = this.getElementBox().getStylePropertyValue("font-weight");
		if (fontWeight.isEmpty())
			fontWeight = "normal";

		return fontWeight;
	}

	/**
	 * Gets frame source index of block
	 * @return Frame source index
	 */
	public int getFrameSourceIndex()
	{
		return _frameSourceIndex;
	}

	/**
	 * Sets source index of block
	 * @param node Node
	 */
	private void setSourceIndex(Node node)
	{
		if (!this.getBox().getNode().equals(node))
			_tmpSrcIndex++;
		else
			_sourceIndex = _tmpSrcIndex;

		for (int i = 0; i < node.getChildNodes().getLength(); i++)
		{
			setSourceIndex(node.getChildNodes().item(i));
		}
	}

	/**
	 * Gets source index of block
	 * @return Block's source index
	 */
	public int getSourceIndex()
	{
		return _sourceIndex;
	}

	/**
	 * Gets order of block
	 * @return Block's order
	 */
	public int getOrder()
	{
		return _order;
	}

}
