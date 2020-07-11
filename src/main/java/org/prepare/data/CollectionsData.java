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

        /* read list links */

        String srcPath = "/Users/batuan/Documents/hoctap/datn/vips_java/segmentation-Layout-Content-website/datalink/";
        String m123nhadat = "123nhadat";
        String m123nhadatviet = "123nhadatviet";
        String alonhadat = "alonhadat";
        String bannha = "bannha";
        String batdongsan = "batdongsan";
        String bds123 = "bds123";
        String bds68 = "bds68";
        String chothuenha = "chothuenha";
        String diaChiNhaDat = "diachinhadat";
        String dothi = "dothi";

        //test
        String kenhbds = "kenhbds";
        String luachonnhadat = "luachonnhadat";
        String muabanbatdongsan = "muabanbatdongsan";
        String timmuanhadat = "timmuanhadat";
        String timnhadat = "timnhadat";

        //BuildTest

        int caseTest = 0;
        String folderTest = "";
        String fileNameTest = "";


        String folder = "";
        String filename = "";
        int _case = 0;

        CONSTANTXPATH constant = new CONSTANTXPATH();

        for (_case = 0; _case < 16; _case++){
            if (_case == 0) {
                folder = srcPath + m123nhadat;
                filename = m123nhadat;
                collectData(folder, filename, 7, 3, 350, 400, constant.xpath123NhaDat);
            } else if (_case == 1) {
                folder = srcPath + m123nhadatviet;
                filename = m123nhadatviet;
                collectData(folder, filename, 7, 3, 350, 400, constant.xpath123NhaDatViet);
            } else if (_case == 2)  {
                folder = srcPath + alonhadat;
                filename = alonhadat;
                collectData(folder, filename, 7, 3, 350, 400, constant.xpathAloNhaDat);
            } else if (_case == 3) {
                folder = srcPath + bannha;
                filename = bannha;
                collectData(folder, filename, 7, 2, 350, 400, constant.xpathBanNha);
            } else if (_case == 4) {
                folder = srcPath + batdongsan;
                filename = batdongsan;
                collectData(folder, filename, 7, 3, 350, 400, constant.xpathBatDongSan);
            } else if (_case == 5) {
                folder = srcPath + bds123;
                filename = bds123;
                collectData(folder, filename, 7, 3, 350, 400, constant.xpathBds123);
            } else if (_case ==6 ) {
                folder = srcPath + chothuenha;
                filename = chothuenha;
                collectData(folder, filename, 7, 3, 350, 400, constant.xpathChoThueNha);
            }
            else if (_case == 7) {
                folder = srcPath + dothi;
                filename = dothi;
                collectData(folder, filename, 7, 3, 350, 400, constant.xpathDoThi);
            } else if(_case == 8) {
                folder = srcPath + kenhbds;
                filename = kenhbds;
                collectData(folder, filename, 7, 3, 350, 400, constant.xpathKenhBatDongSan);
            } else if (_case == 9) {
                folder = srcPath + luachonnhadat;
                filename = luachonnhadat;
                collectData(folder, filename, 8, 3, 350, 400, constant.xpathLuaChonNhaDat);
            } else if (_case == 10) {
                folder = srcPath + muabanbatdongsan;
                filename = muabanbatdongsan;
                collectData(folder, filename, 8, 3, 350, 400, constant.xpathMuaBanBatDongSan);
            } else if (_case == 11) {
                folder = srcPath + timmuanhadat;
                filename = timmuanhadat;
                collectData(folder, filename, 8, 3, 350, 400, constant.xpathTimMuaNhaDat);
            } else if (_case == 12) {
                folder = srcPath + timnhadat;
                filename = timnhadat;
                collectData(folder, filename, 8, 3, 350, 400, constant.xpathTimNhaDat);
            } else if (_case == 13) {
                folder = srcPath + bds68;
                filename = bds68;
                collectData(folder, filename, 8, 3, 350, 400, constant.xpathBds68);
            } else if (_case == 14) {
                folder = srcPath + diaChiNhaDat;
                filename = diaChiNhaDat;
                collectData(folder, filename, 8, 3, 350, 400, constant.xpathDiaChiNhaDat);
            }
            else {
                System.out.println("Done");
            }
        }

    }

    private static void collectData(String folder, String filename, int predefinedDoC, int numberOfIterations, int width,
                                    int height, String[] listXpathLabel) throws IOException {
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

        folder = folder + "/data_3";
        new File(folder).mkdir();
        for (String url: urls) {
            Vips vips = new Vips();
            //vips.setOutputDirectoryName(folder);
            // disable graphics output
            vips.setSizeDimensionWidth(1420);
            vips.setSizeDimensionHeight(980);
            vips.setSizeTresholdHeight(height);
            vips.setSizeTresholdWidth(width);
            vips.enableGraphicsOutput(false);
            // disable output to separate folder (no necessary, it's default value is false)
            // set ouput folder
            vips.setOutPutFolder(folder);
            vips.enableOutputToFolder(false);
            vips.enableOutputEscaping(true);
            // set permitted degree of coherence
            vips.setPredefinedDoC(predefinedDoC);
            vips.setNumberOfIterations(numberOfIterations);
            String tmp = url.split("\\s+")[0];
            tmp = "file://" +tmp;
            System.out.println(count + " : " + tmp);
            vips.setOutputFileName(filename + "-" + count);
            count++;
            vips.setListXpath(listXpathLabel);
            vips.startSegmentation(tmp);
        }
        long endTime = System.nanoTime();
        System.out.println("------------ DONE" + filename +"-----------------");
        long diff = endTime - starTime;
        System.out.println("Total Time of Run: " + (diff / 1000000000.0) + " sec");
    }
}
