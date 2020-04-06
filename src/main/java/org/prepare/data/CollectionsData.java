package org.prepare.data;
import org.fit.vips.Vips;

import java.io.*;
import java.util.ArrayList;

public class CollectionsData {

    /**
     * Main function
     * @param args Internet address of web page.
     */

    public static void main(String args[]) throws IOException {
        // we've just one argument -  web address of page
        /* read list links */
        String batdongsan = "data-link/batdongsan";
        String alonhadat = "data-link/batdongsan";
        String dothi = "data-link/dothi";

        String folder = "";
        String filename = "";
        int _case = 0;
        if (_case == 0) {
            folder = batdongsan;
            filename = "batdongsan";
        } else if (_case == 1){
            folder = alonhadat;
            filename = "alonhadat";
        } else  {
            folder = "dothi";
            filename = "dothi";
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(folder + "/links.txt")));
        ArrayList<String> urls = new ArrayList<>();
        String line = bufferedReader.readLine();
        int num_links = 0;
        while (line != null) {
            urls.add(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        /* construction */
        Vips vips = new Vips();
        vips.setOutputDirectoryName("data-link/batdongsan");
        // disable graphics output
        vips.setSizeDimensionWidth(1420);
        vips.setSizeDimensionHeight(980);
        vips.enableGraphicsOutput(false);
        // disable output to separate folder (no necessary, it's default value is false)
        vips.enableOutputToFolder(false);
        // set permitted degree of coherence
        vips.setPredefinedDoC(7);
        vips.setNumberOfIterations(3);
        int count  = 1;
        for (String url: urls) {
            String tmp = url.split("\\s+")[0];
            System.out.println(count + " : " + tmp);
            vips.setOutputFileName(filename + "-" + count);
            count++;
            vips.startSegmentation(tmp);
        }
        System.out.println("---------- DONE -----------------");
    }
}
