package org.prepare.data;

import java.io.*;

public class BuildData {
    public static void main(String[] args) throws IOException {
        String fileName = "ketqua_2.json";
        String srcPath = "/Users/batuan/Documents/hoctap/datn/vips_java/segmentation-Layout-Content-website/";
        String batdongsan = srcPath + "data-link/batdongsan/" + fileName;
        String alonhadat = srcPath + "data-link/alonhadat/" + fileName;
        String dothi = srcPath + "data-link/dothi/" + fileName;
        String nhadatviet = srcPath + "data-link/123nhadatviet/" + fileName;
        String bds123 = srcPath + "data-link/bds123/" + fileName;
        String chothuenha = srcPath + "data-link/chothuenha/" + fileName;;
        String anphu = srcPath + "data-link/test/" + fileName;
        String homedy = srcPath + "data-link/homedy/" + fileName;
        String bannha = srcPath + "data-link/bannha/" + fileName;

        String[] list = {batdongsan, alonhadat, dothi, nhadatviet, bds123, chothuenha};//, homedy};
        String[] nlist = {batdongsan, alonhadat, nhadatviet, bds123, chothuenha, dothi, bannha};
        PrintWriter pw = new PrintWriter("/Users/batuan/Documents/DATN/data/data-main-layout/contentdata_ketqua_2.json");

        for (String path : nlist) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line = bufferedReader.readLine();
            while (line != null) {
                pw.println(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }

        pw.flush();
        pw.close();
    }
}
