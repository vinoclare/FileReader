package com.example.demo.Readers;

import com.linuxense.javadbf.DBFReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.StreamSupport;

public class DbfReader {
    /**
     * 获取字段名(列名)
     * @param path
     * @param charsetName
     * @return
     * @throws IOException
     */
    public static String[] getFieldName(String path, String charsetName) throws IOException {
//		InputStream fis = new FileInputStream(path);
        DBFReader dbfReader = new DBFReader(new FileInputStream(path), Charset.forName(charsetName));
        int fieldCount = dbfReader.getFieldCount();  // 获取字段数量
        String[] fieldName = new String[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            fieldName[i] = dbfReader.getField(i).getName();
        }
        dbfReader.close();
//		fis.close();
        return fieldName;
    }

    /**
     * 读dbf记录
     * @param path
     * @return
     * @throws IOException
     */
    public static List<Map<String, String>> readDbf(String path, String charsetName) throws IOException {
        List<Map<String, String>> rowList = new ArrayList<>();
//		InputStream fis = new FileInputStream(path);
        DBFReader dbfReader = new DBFReader(new FileInputStream(path), Charset.forName(charsetName));
        Object[] rowValues;
        while ((rowValues = dbfReader.nextRecord()) != null) {
            Map<String, String> rowMap = new HashMap<String, String>();
            for (int i = 0; i < rowValues.length; i++) {
                rowMap.put(dbfReader.getField(i).getName(), String.valueOf(rowValues[i]).trim());
            }
//			System.out.println(rowMap);
            rowList.add(rowMap);
        }
        dbfReader.close();
//		fis.close();
        return rowList;
    }



    public static void main(String[] args){
        String path = "D:\\OneDrive - 东南大学\\实习\\文件\\FileReader\\src\\main\\resources\\static\\2.dbf";
        String[] fieldName = null;
        List<Map<String, String>> rowList = null;

        // 获取dbf字段名
        try{
             fieldName =  getFieldName(path, "utf-8");
        } catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println(Arrays.toString(fieldName));

        // 打印dbf记录
        try{
            rowList = readDbf(path, "gbk");
        } catch (Exception e){
            e.printStackTrace();
        }
//        for (Map<String,String> map : rowList){
//            System.out.println(map);
//        }
        for (Map<String, String> map : rowList){
            System.out.print("|");
            for (int i=0; i < map.size(); i++){
                System.out.print(map.get(fieldName[i]) + "|");
            }
            System.out.println();
        }
    }
}
