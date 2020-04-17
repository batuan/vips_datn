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
		// we've just one argument - web address of page
		if (args.length != 1)
		{
			System.err.println("We've just only one argument - web address of page!");
			System.exit(0);
		}

		String url = args[0];

		try
		{
			Vips vips = new Vips();
			vips.setOutputDirectoryName("data1");
			vips.setOutputFileName("muabannhadat");
			// disable graphics output
			vips.setSizeDimensionWidth(1420);
			vips.setSizeDimensionHeight(980);
			vips.enableGraphicsOutput(true);
			// disable output to separate folder (no necessary, it's default value is false)
			vips.enableOutputToFolder(false);
			vips.enableOutputEscaping(true);
			// set permitted degree of coherence
		/*	vips.setSizeTresholdWidth(300);
			vips.setSizeTresholdHeight(350);*/
			vips.setPredefinedDoC(8);
			vips.setNumberOfIterations(5);
			// start segmentation on page
			vips.startSegmentation(url);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
