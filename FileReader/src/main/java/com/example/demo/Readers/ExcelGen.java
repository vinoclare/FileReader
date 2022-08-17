package com.example.demo.Readers;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelGen {
    // xls相关变量
    private static HSSFWorkbook wb;
    private static HSSFSheet sheet;
    private static HSSFRow row;
    private static HSSFCell cell;
    private static File file;

    // xlsx相关变量
    private static XSSFWorkbook wbx;
    private static XSSFSheet sheetx;
    private static XSSFRow rowx;
    private static XSSFCell cellx;
    private static File filex;

    //创建sheet页(xls)
    public static void setSheet(String sheetName) {
        wb = new HSSFWorkbook();
        sheet = wb.createSheet(sheetName);
    }

    //创建sheet页(xlsx)
    public static void setSheetx(String sheetName) {
        wbx = new XSSFWorkbook();
        sheetx = wbx.createSheet(sheetName);
    }

    //创建表头(xls)
    public static void createHead(List<String> headList) {
        //创建表头，也就是第一行
        row = sheet.createRow(0);
        for (int i = 0; i < headList.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(headList.get(i));
        }
    }

    //创建表头(xlsx)
    public static void createHeadx(List<String> headList) {
        //创建表头，也就是第一行
        rowx = sheetx.createRow(0);
        for (int i = 0; i < headList.size(); i++) {
            cellx = rowx.createCell(i);
            cellx.setCellValue(headList.get(i));
        }
    }

    //创建表内容
    public static void createContent(List<List<String>> contentList) {
        //创建表内容，从第二行开始
        for (int i = 0; i < contentList.size(); i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < contentList.get(i).size(); j++) {
                row.createCell(j).setCellValue(contentList.get(i).get(j));
            }
        }
    }

    //创建表内容
    public static void createContentx(List<List<String>> contentList) {
        //创建表内容，从第二行开始
        for (int i = 0; i < contentList.size(); i++) {
            rowx = sheetx.createRow(i + 1);
            for (int j = 0; j < contentList.get(i).size(); j++) {
                rowx.createCell(j).setCellValue(contentList.get(i).get(j));
            }
        }
    }

    //写入文件(xls)
    public static void writeToFile(String filePath){
        file = new File(filePath);
        //将文件保存到指定的位置
        try {
            wb.write(new FileOutputStream(file));
            System.out.println("写入成功");
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //写入文件(xlsx)
    public static void writeToFilex(String filePath){
        filex = new File(filePath);
        //将文件保存到指定的位置
        try {
            wbx.write(new FileOutputStream(filex));
            System.out.println("写入成功");
            wbx.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 内容测试数据
    protected static List<List<String>> getContent() {
        List<List<String>> contentList = new ArrayList<>();
        List<String> content1 = new ArrayList<>();
        content1.add("张三");
        content1.add("18");
        List<String> content2 = new ArrayList<>();
        content2.add("李四");
        content2.add("20");
        contentList.add(content1);
        contentList.add(content2);
        return contentList;
    }

    public static void main(String[] args) {
        //表头测试数据
        List<String> headList = new ArrayList<>();
        headList.add("昵称");
        headList.add("年龄");
        List<List<String>> contentList = getContent();//内容测试数据

        setSheet("WorkSheet");                        //创建sheet页
        createHead(headList);                         //设置表头
        createContent(contentList);                   //设置内容

        setSheetx("WorkSheet");
        createHeadx(headList);
        createContentx(contentList);

        writeToFile("D:\\OneDrive - 东南大学\\实习\\文件\\FileReader\\src\\main\\resources\\static\\In.xls");          //写入xls文件
        writeToFilex("D:\\OneDrive - 东南大学\\实习\\文件\\FileReader\\src\\main\\resources\\static\\In.xlsx");          //写入xlsx文件
    }
}
