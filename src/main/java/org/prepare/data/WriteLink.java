package org.prepare.data;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class WriteLink {
    public static void main(String[] args) throws IOException {
        String srcDic = "/Users/batuan/Documents/hoctap/datn/vips_java/segmentation-Layout-Content-website/datalink";
        String srcLink = "/Users/batuan/Documents/hoctap/datn/vips_java/databatdongsan/website";

        String m123nhadat = "/123nhadat";
        String m123nhadatviet = "/123nhadatviet";
        String alonhadat = "/alonhadat";
        String bannha = "/bannha";
        String batdongsan = "/batdongsan";
        String bds68 = "/bds68";
        String bds123 = "/bds123";
        String chothuenha = "/chothuenha";
        String diachinhadat = "/diachinhadat";
        String dothi = "/dothi";
        String kenhbds = "/kenhbds";
        String luachonnhadat = "/luachonnhadat";
        String muabanbatdongsan = "/muabanbatdongsan";
        String timmuanhadat = "/timmuanhadat";
        String timnhadat = "/timnhadat";

        String [] list = {  m123nhadat, m123nhadatviet, alonhadat, bannha, batdongsan, bds68,
                            bds123, chothuenha, diachinhadat, dothi, kenhbds, luachonnhadat,
                            muabanbatdongsan, timmuanhadat, timnhadat };


//        File folder = new File(srcLink + batdongsan);
//        ArrayList<String> listPath = new ArrayList<>();
//        for(File it: Objects.requireNonNull(folder.listFiles())) {
//            System.out.println(it.getPath());
//            //if (it.getName().contains("html"))
//
//                //listPath.add(it.getName());
//        }

        for (String domain : list){
            String path = srcDic + domain + "/links.txt";
            File folder = new File(srcLink + domain);
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            ArrayList<String> listPath = new ArrayList<>();
            for(File it: Objects.requireNonNull(folder.listFiles())) {
                if (it.getName().contains(".htm"))
                    listPath.add(it.getPath());
            }
            for (String url: listPath) {
                writer.write(url);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        }
    }
}

