package com.example.demo.Readers;

import com.csvreader.CsvReader;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

public class CsvReaderMy {
    /**
     * CsvReader 读取
     * @param filePath 文件路径
     * @return
     */
    public static ArrayList<String> readCsv(String filePath) {
        ArrayList<String> strList = null;
        try {
            ArrayList<String[]> arrList = new ArrayList<String[]>();
            strList = new ArrayList<String>();
            CsvReader reader = new CsvReader(filePath, ',', Charset.forName("UTF-8"));
            while (reader.readRecord()) {
//                System.out.println(Arrays.asList(reader.getValues()));
                arrList.add(reader.getValues()); // 按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            System.out.println("读取的行数：" + arrList.size());
            for (int i=0; i<arrList.size(); i++){
                String[] line = arrList.get(i);
                if (i == 0){
                    System.out.println("列名：" + Arrays.toString(line));
                    continue;
                }
                System.out.println(Arrays.toString(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }

    public static void main(String[] args){
        readCsv("D:\\OneDrive - 东南大学\\实习\\文件\\FileReader\\src\\main\\resources\\static\\2.csv");
    }
}