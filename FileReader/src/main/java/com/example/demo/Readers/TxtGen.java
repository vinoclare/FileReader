package com.example.demo.Readers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TxtGen {
    public static void main(String[] args) throws IOException {
        String word = "hahahahhahahahhah";
        FileOutputStream fileOutputStream = null;
        File file = new File("D:\\OneDrive - 东南大学\\实习\\文件\\FileReader\\src\\main\\resources\\static\\In.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(word.getBytes("gbk"));
        fileOutputStream.flush();
        fileOutputStream.close();
        System.out.println("txt写入完成！");
    }
}
