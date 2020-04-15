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
        String alonhadat = "data-link/alonhadat";
        String dothi = "data-link/dothi";
        String nhadatviet = "data-link/123nhadatviet";
        String bds123 = "data-link/bds123";
        String chothuenha = "data-link/chothuenha";
        String anphu = "data-link/test";

        String folder = "";
        String filename = "";
        int _case = 6;

        if (_case == 0) {
            folder = batdongsan;
            filename = "batdongsan";
        } else if (_case == 1) {
            folder = alonhadat;
            filename = "alonhadat";
        } else if (_case == 2)  {
            folder = dothi;
            filename = "dothi";
        } else if (_case == 3) {
            folder = nhadatviet;
            filename = "123nhadatviet";
        } else if (_case == 4) {
            folder = bds123;
            filename = "bds123";
        } else if (_case == 5) {
            folder = chothuenha;
            filename = "chothuenha";
        } else if (_case == 6) {
            folder = anphu;
            filename = "test";
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
        int count  = 1;
        for (String url: urls) {
            Vips vips = new Vips();
            vips.setOutputDirectoryName(folder);
            // disable graphics output
            vips.setSizeDimensionWidth(1420);
            vips.setSizeDimensionHeight(980);
           /* vips.setSizeTresholdHeight(500);
            vips.setSizeTresholdWidth(450);*/
            vips.enableGraphicsOutput(false);
            // disable output to separate folder (no necessary, it's default value is false)
            vips.enableOutputToFolder(false);
            vips.enableOutputEscaping(true);
            // set permitted degree of coherence
            vips.setPredefinedDoC(7);
            vips.setNumberOfIterations(3);
            String tmp = url.split("\\s+")[0];
            System.out.println(count + " : " + tmp);
            vips.setOutputFileName(filename + "-" + count);
            count++;
            vips.startSegmentation(tmp);
        }
        System.out.println("---------- DONE -----------------");
    }
}
