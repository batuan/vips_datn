/*
 * Tomas Popela, 2012
 * VIPS - Visual Internet Page Segmentation
 * Module - Vips.java
 */

package org.fit.vips;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.fit.cssbox.layout.BrowserCanvas;
import org.fit.cssbox.layout.Viewport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Vision-based Page Segmentation algorithm
 * @author Tomas Popela
 *
 */
public class Vips {
	private String urlString = "";
	private URL _url = null;
	private DOMAnalyzer _domAnalyzer = null;
	private Document _document = null;
	private BrowserCanvas _browserCanvas = null;
	private Viewport _viewport = null;
	private String outPutFolder;
	private boolean _graphicsOutput = false;
	private boolean _outputToFolder = false;
	private boolean _outputEscaping = true;
	private int _pDoC = 11;
	private int numberOfIterations = 10;
	private String _filename = "";
	private String _dirName = "";
	private	int sizeTresholdWidth = 350;
	private	int sizeTresholdHeight = 400;
	private int sizeDimensionWidth = 1000;
	private int sizeDimensionHeight = 600;
	private PhantomJS phantomJS;
	private PrintStream originalOut = null;
	long startTime = 0;
	long endTime = 0;

	private String[] listXpath;

	public void setListXpath(String[] listXpath) {
		this.listXpath = listXpath;
	}

	public void setOutPutFolder(String outPutFolder) {
		this.outPutFolder = outPutFolder;
	}

	/**
	 * Default constructor
	 */
	public Vips()
	{
//		this.phantomJS = new PhantomJS();
	}

	/**
	 * Enables or disables graphics output of VIPS algorithm.
	 * @param enable True for enable, otherwise false.
	 */
	public void enableGraphicsOutput(boolean enable)
	{
		_graphicsOutput = enable;
	}

	/**
	 * Enables or disables creation of new directory for every algorithm run.
	 * @param enable True for enable, otherwise false.
	 */
	public void enableOutputToFolder(boolean enable)
	{
		_outputToFolder = enable;
	}

	/**
	 * Enables or disables output XML character escaping.
	 * @param enable True for enable, otherwise false.
	 */
	public void enableOutputEscaping(boolean enable)
	{
		_outputEscaping = enable;
	}

	/**
	 * Sets permitted degree of coherence (pDoC) value.
	 * @param value pDoC value.
	 */
	public void setPredefinedDoC(int value)
	{
		if (value <= 0 || value > 11)
		{
			System.err.println("pDoC value must be between 1 and 11! Not " + value + "!");
			return;
		}
		else
		{
			_pDoC = value;
		}
	}

	/**
	 * Sets web page's URL
	 * @param url Url
	 * @throws MalformedURLException
	 */
	public void setUrl(String url)
	{
		this.urlString = url;
		try
		{
			if (url.startsWith("http://") || url.startsWith("https://"))
				_url = new URL(url);
			else
				_url = new URL("http://" + url);
		}
		catch (Exception e)
		{
			System.err.println("Invalid address: " + url);
		}
	}

	/**
	 * Parses a builds DOM tree from page source.
	 * @param urlStream Input stream with page source.
	 */
	private void getDomTree(URL urlStream)
	{
		DocumentSource docSource = null;
		try
		{
			docSource = new DefaultDocumentSource(urlStream);
			DOMSource parser = new DefaultDOMSource(docSource);

			Document domTree = parser.parse();
			this._document = domTree;
			_domAnalyzer = new DOMAnalyzer(domTree, _url);
			_domAnalyzer.attributesToStyles();
			_domAnalyzer.addStyleSheet(null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT);
			_domAnalyzer.addStyleSheet(null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT);
			_domAnalyzer.getStyleSheets();
			_browserCanvas = new BrowserCanvas(_domAnalyzer.getRoot(),
					_domAnalyzer, new java.awt.Dimension(sizeDimensionWidth, sizeDimensionHeight), _url);
		}
		catch (Exception e)
		{
			System.err.print(e.getMessage());
		}
	}

	/**
	 * Parses a builds DOM tree from page source
	 * @param url String Input with phantomJS
	 */
	private void getDomTree(String url) {
		DocumentSource docSource = null;
		try
		{
			docSource = new DefaultDocumentSource(url);
			DOMSource parser = new DefaultDOMSource(docSource);

			Document domTree = parser.parse();
			this._document = domTree;
			_domAnalyzer = new DOMAnalyzer(domTree, docSource.getURL());
			_domAnalyzer.attributesToStyles();
			_domAnalyzer.addStyleSheet(null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT);
			_domAnalyzer.addStyleSheet(null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT);
			_domAnalyzer.getStyleSheets();
			_browserCanvas = new BrowserCanvas(_domAnalyzer.getRoot(),
					_domAnalyzer, new java.awt.Dimension(sizeDimensionWidth, sizeDimensionHeight), docSource.getURL());

		}
		catch (Exception e)
		{
			System.err.print(e.getMessage());
		}
	}

	/**
	 * Gets page's viewport
	 */
	private void getViewport()
	{
		_viewport = _browserCanvas.getViewport();
	}

	/**
	 * Exports rendered page to image.
	 */
	private void exportPageToImage()
	{
		try
		{
			BufferedImage page = _browserCanvas.getImage();
			String filename = System.getProperty("user.dir") + "/page.png";
			ImageIO.write(page, "png", new File(filename));
		} catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Generates folder filename
	 * @return Folder filename
	 */
	private String generateFolderName()
	{
		String outputFolder = "";

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		outputFolder += sdf.format(cal.getTime());
		outputFolder += "_";
		outputFolder += _url.getHost().replaceAll("\\.", "_").replaceAll("/", "_");

		return outputFolder;
	}

	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	/**
	 * Performs page segmentation.
	 */
	private void performSegmentation()
	{

		startTime = System.nanoTime();
		int numberOfIterations = this.numberOfIterations;
		int pageWidth = _viewport.getWidth();
		int pageHeight = _viewport.getHeight();

		if (_graphicsOutput)
			exportPageToImage();

		VipsSeparatorGraphicsDetector detector;
		VipsParser vipsParser = new VipsParser(_viewport);
		VisualStructureConstructor constructor = new VisualStructureConstructor(_pDoC);
		constructor.setGraphicsOutput(_graphicsOutput);

		for (int iterationNumber = 1; iterationNumber < numberOfIterations+1; iterationNumber++)
		{
			detector = new VipsSeparatorGraphicsDetector(pageWidth, pageHeight);

			//visual blocks detection
			vipsParser.setSizeTresholdHeight(sizeTresholdHeight);
			vipsParser.setSizeTresholdWidth(sizeTresholdWidth);

			vipsParser.parse();

			VipsBlock vipsBlocks = vipsParser.getVipsBlocks();

			if (iterationNumber == 1)
			{
				if (_graphicsOutput)
				{
					// in first round we'll export global separators
					detector.setVipsBlock(vipsBlocks);
					detector.fillPool();
					detector.saveToImage("blocks" + iterationNumber);
					detector.setCleanUpSeparators(0);
					detector.detectHorizontalSeparators();
					detector.detectVerticalSeparators();
					detector.exportHorizontalSeparatorsToImage();
					detector.exportVerticalSeparatorsToImage();
					detector.exportAllToImage();
				}

				// visual structure construction
				constructor.setVipsBlocks(vipsBlocks);
				constructor.setPageSize(pageWidth, pageHeight);
			}
			else
			{
				vipsBlocks = vipsParser.getVipsBlocks();
				constructor.updateVipsBlocks(vipsBlocks);

				if (_graphicsOutput)
				{
					detector.setVisualBlocks(constructor.getVisualBlocks());
					detector.fillPool();
					detector.saveToImage("blocks" + iterationNumber);
				}
			}

			// visual structure construction
			constructor.constructVisualStructure();

			// prepare tresholds for next iteration
			if (iterationNumber <= 5 )
			{
				sizeTresholdHeight -= 50;
				sizeTresholdWidth -= 50;

			}
			if (iterationNumber == 6)
			{
				sizeTresholdHeight = 100;
				sizeTresholdWidth = 100;
			}
			if (iterationNumber == 7)
			{
				sizeTresholdHeight = 80;
				sizeTresholdWidth = 80;
			}
			if (iterationNumber == 8)
			{
				sizeTresholdHeight = 40;
				sizeTresholdWidth = 10;
			}
			if (iterationNumber == 9)
			{
				sizeTresholdHeight = 1;
				sizeTresholdWidth = 1;
			}

		}

		//		constructor.normalizeSeparatorsSoftMax();
		constructor.normalizeSeparatorsMinMax();

		VipsOutput vipsOutput = new VipsOutput(_pDoC);
		vipsOutput.setEscapeOutput(_outputEscaping);
		vipsOutput.setOutputFileName(_filename);
		vipsOutput.writeXML(constructor.getVisualStructure(), _viewport);

		VipsJsonOutput vipsJsonOutput = new VipsJsonOutput(_pDoC, this.listXpath);
		vipsJsonOutput.set_filename(_filename);
		vipsJsonOutput.writeJson(constructor.getVisualStructure(), _viewport);

		endTime = System.nanoTime();

		long diff = endTime - startTime;

		System.out.println("Execution time of VIPS: " + diff + " ns; " +
				(diff / 1000000.0) + " ms; " +
				
				(diff / 1000000000.0) + " sec");		
	}

	/**
	 * Starts segmentation on given address
	 * @param url
	 */
	public void startSegmentation(String url)
	{
		setUrl(url);

		startSegmentation();
	}

	/**
	 * Restores stdout
	 */
	private void restoreOut()
	{
		if (originalOut != null)
		{
			System.setOut(originalOut);
		}
	}

	/**
	 * Redirects stdout to nowhere
	 */
	private void redirectOut()
	{
		originalOut = System.out;
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException
			{

			}
		}));
	}

	/**
	 * Starts visual segmentation of page
	 * @throws Exception
	 */
	public void startSegmentation()
	{
		try
		{
			_url.openConnection();

			redirectOut();
			getDomTree(this.urlString);
			startTime = System.nanoTime();
			getViewport();
			restoreOut();

			String outputFolder = "";
			String oldWorkingDirectory = "";
			String newWorkingDirectory = "";

			if (_outputToFolder)
			{
				outputFolder = generateFolderName();

				if (!new File(outputFolder).mkdir())
				{
					System.err.println("Something goes wrong during directory creation!");
				}
				else
				{
					oldWorkingDirectory = System.getProperty("user.dir");
					newWorkingDirectory += oldWorkingDirectory + "/" + outputFolder + "/";
					System.setProperty("user.dir", newWorkingDirectory);
					//new File(newWorkingDirectory + "/" + "images" + "/").mkdir();
					//new File(newWorkingDirectory + "/" + _dirName + "/").mkdir();
					System.setProperty("user.dir.data", newWorkingDirectory + "/" + _dirName + "/");
				}
			}
			else {
				File setOutputFolder = new File(this.outPutFolder);
				if (!setOutputFolder.exists()) {
					if(setOutputFolder.mkdir()){
						System.out.println("DONE MKDIR");
					} else {
						System.out.println("Path is note Exsit");
					}
				}
				System.setProperty("user.dir", this.outPutFolder);
			}

			performSegmentation();

			if (_outputToFolder)
				System.setProperty("user.dir", oldWorkingDirectory);
		}
		catch (Exception e)
		{
			System.err.println("Something's wrong!");
			e.printStackTrace();
		}
	}

	public void setSizeTresholdWidth(int width) {
		this.sizeTresholdWidth = width;
	}

	public void setSizeTresholdHeight(int height) {
		this.sizeTresholdHeight = height;
	}

	public void setSizeDimensionWidth(int width) {
		this.sizeDimensionWidth = width;
	}

	public void setSizeDimensionHeight(int height) {
		this.sizeDimensionHeight = height;
	}

	public void setOutputFileName(String filename)
	{
		if (!filename.equals(""))
		{
			_filename = filename;
		}
		else
		{
			System.out.println("Invalid filename!");
		}
	}

	public void setOutputDirectoryName(String dirName) {
		if (!dirName.equals(""))
		{
			_dirName = dirName;
		}
		else
		{
			System.out.println("Invalid directory name!");
		}
	}
}
