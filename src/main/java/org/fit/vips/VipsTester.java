/*
 * Tomas Popela, 2012
 * VIPS - Visual Internet Page Segmentation
 * Module - VipsTester.java
 */

package org.fit.vips;

/**
 * VIPS API example application.
 * @author Tomas Popela
 *
 */
public class VipsTester {

	/**
	 * Main function
	 * @param args Internet address of web page.
	 */
	public static void main(String args[])
	{
		// we've three argument - web address of page
		// Tham số đầu tiên là địa chỉ website
		if (args.length != 2)
		{
			System.err.println("We've just two argument - web address of page and path to save save");
			System.exit(0);
		}

		String url = args[0];
		String path = args[1];
		String local = "file:///Users/batuan/Documents/hoctap/datn/vips_java/databatdongsan/website/123nhadat/123nhadat30.html";
		try
		{
			Vips vips = new Vips();
			//vips.setOutputDirectoryName("data");
			vips.setOutputFileName("VIPSOUTPUT");
			// disable graphics output
			vips.setSizeDimensionWidth(1420);
			vips.setSizeDimensionHeight(980);
			vips.enableGraphicsOutput(false);
			// disable output to separate folder (no necessary, it's default value is false)
			vips.enableOutputToFolder(false);
			vips.enableOutputEscaping(true);
			vips.setOutPutFolder(path);
			// set permitted degree of coherence
		/*	vips.setSizeTresholdWidth(300);
			vips.setSizeTresholdHeight(350);*/
			vips.setPredefinedDoC(7);
			vips.setNumberOfIterations(3);
			String[] list = {"/html[1]/body[1]/form[1]/div[3]/div[4]/div[2]/div[1]/div[1]"};
			vips.setListXpath(list);
			// start segmentation on page
			vips.startSegmentation(url);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
