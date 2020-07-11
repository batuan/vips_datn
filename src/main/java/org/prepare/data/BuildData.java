package org.prepare.data;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class BuildData {
    public static void main(String[] args) throws IOException {
        GetDataFromJson();
    }

    public static void GetDataOld() throws IOException{
        String fileName = "/data-1";
        String srcPath = "/Users/batuan/Documents/hoctap/datn/vips_java/segmentation-Layout-Content-website/datalink";

        String m123nhadat = srcPath + "123nhadat" + fileName;
        String m123nhadatviet = srcPath +  "123nhadatviet" + fileName;
        String alonhadat = srcPath +  "alonhadat" + fileName;
        String bannha = srcPath +  "bannha" + fileName;
        String batdongsan = srcPath +  "batdongsan" + fileName;
        String bds123 = srcPath +  "bds123" + fileName;
        String chothuenha = srcPath +  "chothuenha" + fileName;
        String dothi = srcPath +  "dothi" + fileName;
        String[] list = {batdongsan, alonhadat, dothi, bds123, chothuenha};//, homedy};
        String[] nlist = {batdongsan, alonhadat, bds123, chothuenha, dothi, bannha};
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

    public static void GetDataFromJson() throws IOException {
        String fileName = "/data_3";
        String srcPath = "/Users/batuan/Documents/hoctap/datn/vips_java/segmentation-Layout-Content-website/datalink/";

        String m123nhadat = srcPath + "123nhadat" + fileName;
        String m123nhadatviet = srcPath +  "123nhadatviet" + fileName;
        String alonhadat = srcPath +  "alonhadat" + fileName;
        String bannha = srcPath +  "bannha" + fileName;
        String batdongsan = srcPath +  "batdongsan" + fileName;
        String bds68 = srcPath + "bds68" + fileName;
        String bds123 = srcPath +  "bds123" + fileName;
        String chothuenha = srcPath +  "chothuenha" + fileName;
        String diachinhadat = srcPath + "diachinhadat" + fileName;
        String dothi = srcPath +  "dothi" + fileName;
        String kenhbds = srcPath + "kenhbds" + fileName;
        String luachonnhadat = srcPath + "luachonnhadat" + fileName;
        String muabanbatdongsan = srcPath + "muabanbatdongsan" + fileName;
        String timmuanhadat = srcPath + "timmuanhadat" + fileName;
        String timnhadat = srcPath + "timnhadat" + fileName;


        String[] list_train =
                {m123nhadat, m123nhadatviet, alonhadat, bannha,
                batdongsan, bds123, chothuenha, diachinhadat, bds68};
        //bannha, dothi, luachonhadat khong phan chia duoc.
        String[] list_test = {kenhbds, timnhadat, timmuanhadat};
        String fileTrain = "bds_layout_train_1.json";
        String fileTest = "bds_layout_test_1.json";
        BuildDataFromList(list_train, fileTrain);
        BuildDataFromList(list_test, fileTest);

    }

    static void BuildDataFromList(String[] list, String fileName) throws IOException{
        System.out.println("**************************************");
        System.out.println("****** BUILD "+ fileName.toUpperCase() + " ******");
        PrintWriter pw = new PrintWriter("/Users/batuan/Documents/DATN/data/data-main-layout/" + fileName );
        for (String DirectoryPath: list){
            //List all json file in folder
            ArrayList<String> listPath = new ArrayList<>();
            System.out.println(DirectoryPath);
            File folder = new File(DirectoryPath);
            for(File it: Objects.requireNonNull(folder.listFiles())) {
                if (it.getName().contains(".json"))
                    listPath.add(it.getPath());
            }
            for (String path: listPath) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
                String line = bufferedReader.readLine();
                // Khong doc dong dau tien
                while (line != null) {
                    if(!line.contains("PageTitle"))
                    {
                        pw.println(line);
                    }
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
            }
        }
        pw.close();
    }
}
