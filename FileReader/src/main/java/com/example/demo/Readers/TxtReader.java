package com.example.demo.Readers;

import java.io.*;

public class TxtReader {
    public static void main(String[] args){
        File file = new File("D:\\OneDrive - 东南大学\\实习\\文件\\FileReader\\src\\main\\resources\\static\\1.txt");
        BufferedReader reader = null;
        String tempString = null;
        int line =1;

        try {
            System.out.println("按行读取txt文件：");
            reader = new BufferedReader(new FileReader(file));
            while ((tempString = reader.readLine()) != null) {
                System.out.println(tempString);
                line ++ ;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
