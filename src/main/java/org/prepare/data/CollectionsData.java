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
        String test = srcPath + "data-link/test";
        String bannha = srcPath + "data-link/bannha.net";

        String folder = "";
        String filename = "";
        int _case = 0;
//        folder = test;
//        filename = "test";
//        collectData(folder, filename, 8, 3, 350, 400);
        //folder = homedy;
        //filename = "homedy";
        //collectData(folder, filename, 8, 1, 350, 400);
        for (_case = 3; _case < 4; _case++){
            if (_case == 10) {
                folder = batdongsan;
                filename = "batdongsan";
                collectData(folder, filename, 8, 3, 350, 400);
            } else if (_case == 1) {
                folder = alonhadat;
                filename = "alonhadat";
                collectData(folder, filename, 8, 3, 350, 400);
            } else if (_case == 2)  {
                folder = dothi;
                filename = "dothi";
                collectData(folder, filename, 8, 3, 350, 400);
            } else if (_case == 3) {
                folder = nhadatviet;
                filename = "123nhadatviet";
                collectData(folder, filename, 7, 2, 350, 400);
            } else if (_case == 4) {
                folder = bds123;
                filename = "bds123";
                collectData(folder, filename, 7, 3, 350, 400);
            } else if (_case == 5) {
                folder = chothuenha;
                filename = "chothuenha";
                collectData(folder, filename, 8, 3, 350, 400);
            } else if (_case ==6 ) {
                folder = bannha;
                filename = "bannha";
                collectData(folder, filename, 8, 3, 350, 400);
            }
            else if (_case == 7) {
                folder = test;
                filename = "test";
                collectData(folder, filename, 8, 3, 350, 400);
            } else {
                System.out.println("Done");
            }
        }

    }

    private static void collectData(String folder, String filename, int predefinedDoC, int numberOfIterations, int width, int height) throws IOException {
        long starTime = System.nanoTime();
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
            vips.setSizeTresholdHeight(height);
            vips.setSizeTresholdWidth(width);
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
        long endTime = System.nanoTime();
        System.out.println("------------ DONE" + filename +"-----------------");
        long diff = endTime - starTime;
        System.out.println("Total Time of Run: " + (diff / 1000000000.0) + " sec");
    }
}
