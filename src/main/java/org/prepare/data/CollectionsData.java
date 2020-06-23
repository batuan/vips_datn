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
        String srcPath = "/Users/batuan/Documents/hoctap/datn/vips_java/segmentation-Layout-Content-website/";
        String batdongsan = srcPath + "data-link/batdongsan";
        String alonhadat = srcPath + "data-link/alonhadat";
        String dothi = srcPath + "data-link/dothi";
        String nhadatviet = srcPath + "data-link/123nhadatviet";
        String bds123 = srcPath + "data-link/bds123";
        String chothuenha = srcPath + "data-link/chothuenha";
        String anphu = srcPath + "data-link/test";
        String homedy = srcPath + "data-link/homedy";

        String folder = "";
        String filename = "";
        int _case = 0;

        folder = homedy;
        filename = "homedy";
        collectData(folder, filename, 8, 1);
//        for (_case = 0; _case < 8; _case++){
//            if (_case == 0) {
//                folder = batdongsan;
//                filename = "batdongsan";
//                collectData(folder, filename);
//            } else if (_case == 1) {
//                folder = alonhadat;
//                filename = "alonhadat";
//                collectData(folder, filename);
//            } else if (_case == 2)  {
//                folder = dothi;
//                filename = "dothi";
//                collectData(folder, filename);
//            } else if (_case == 3) {
//                folder = nhadatviet;
//                filename = "123nhadatviet";
//                collectData(folder, filename);
//            } else if (_case == 4) {
//                folder = bds123;
//                filename = "bds123";
//                collectData(folder, filename);
//            } else if (_case == 5) {
//                folder = chothuenha;
//                filename = "chothuenha";
//                collectData(folder, filename);
//            } else if (_case == 6) {
//                folder = anphu;
//                filename = "test";
//                collectData(folder, filename);
//            } else {
//                System.out.println("Done");
//            }
//        }

    }

    private static void collectData(String folder, String filename, int predefinedDoC, int numberOfIterations) throws IOException {
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
            vips.setPredefinedDoC(predefinedDoC);
            vips.setNumberOfIterations(numberOfIterations);
            String tmp = url.split("\\s+")[0];
            System.out.println(count + " : " + tmp);
            vips.setOutputFileName(filename + "-" + count);
            count++;
            vips.startSegmentation(tmp);
        }
        System.out.println("------------ DONE" + filename +"-----------------");
    }
}
