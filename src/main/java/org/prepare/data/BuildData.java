package org.prepare.data;

import java.io.*;

public class BuildData {
    public static void main(String[] args) throws IOException {
        String fileName = "result-3.json";
        String srcPath = "/Users/batuan/Documents/hoctap/datn/vips_java/segmentation-Layout-Content-website/";
        String batdongsan = srcPath + "data-link/batdongsan/result-3.json";
        String alonhadat = srcPath + "data-link/alonhadat/result-3.json";
        String dothi = srcPath + "data-link/dothi/result-3.json";
        String nhadatviet = srcPath + "data-link/123nhadatviet/result-3.json";
        String bds123 = srcPath + "data-link/bds123/result-3.json";
        String chothuenha = srcPath + "data-link/chothuenha/result-3.json";
        String anphu = srcPath + "data-link/test/result-3.json";
        String homedy = srcPath + "data-link/homedy/result-3.json";


        String[] list = {batdongsan, alonhadat, dothi, nhadatviet, bds123, chothuenha, homedy};

        PrintWriter pw = new PrintWriter("/Users/batuan/Documents/hoctap/datn/vips_java/segmentation-Layout-Content-website/data-link/contentdata.json");

        for (String path : list) {
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
