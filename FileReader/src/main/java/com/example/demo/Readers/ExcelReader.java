package com.example.demo.Readers;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 读取Excel数据工具类----可以读取多个sheet
 *  注意：1、若单元格为空时，读出为""空字符串；列数为按最后一列计算；
 * 2、如果在最后一列前单元格合并数据个数还是按合并前列数算，但是合并后单元格只有左上单元格有数据；
 * 3、如果是最后一列合并单元格，则算最前面一列 ，后面列不计算 在其中，也没数据（不是""空串）
 */
public class ExcelReader{
    private static HSSFWorkbook wb;
    private static HSSFSheet sheet;
    private static HSSFRow row;
    private static XSSFWorkbook wbx;
    private static XSSFSheet sheetx;
    private static XSSFRow rowx;

    /**
     * 读取xls文件
     * @param filePath 文件路径
     */
    public ExcelData readExcel(String filePath) {
        InputStream is = null;
        File file = new File(filePath);
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ExcelData excelData = new ExcelData();
        try {
            if(filePath.substring(filePath.length()-5,filePath.length()).equals(".xlsx")){
                wbx = new XSSFWorkbook(is);
                return readExcelx(wbx,file.getName());
            }
            wb = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer sheetNum = wb.getNumberOfSheets();
        excelData.setSheetSum(sheetNum);
        excelData.setFileName(file.getName());

        // 循环获取所有sheet数据
        List<ExcelSheetData> sheetDatas = new ArrayList<>();
        for (int i = 0; i < sheetNum; i++) {
            ExcelSheetData sheetData = new ExcelSheetData();
            sheet = wb.getSheetAt(i);
            sheetData.setLineSum(sheet.getPhysicalNumberOfRows());
            sheetData.setSheetName(sheet.getSheetName());

            List<ExcelLineData> lineDatas = readExcelContentBySheet(sheet);
            sheetData.setLineData(lineDatas);
            sheetDatas.add(sheetData);
        }
        excelData.setSheetData(sheetDatas);
        return excelData;
    }

    /**
     * 读取xlsx文件
     * @param fileName 文件路径
     */
    private ExcelData readExcelx(XSSFWorkbook wbx,String fileName) {

        ExcelData excelData = new ExcelData();
        Integer sheetNum = wbx.getNumberOfSheets();
        excelData.setSheetSum(sheetNum);
        excelData.setFileName(fileName);

        //循环获取所有sheet数据
        List<ExcelSheetData> sheetDatas = new ArrayList<>();
        for (int i = 0; i < sheetNum; i++) {
            ExcelSheetData sheetData = new ExcelSheetData();
            sheetx = wbx.getSheetAt(i);
            sheetData.setSheetName(sheetx.getSheetName());
            sheetData.setLineSum(sheetx.getPhysicalNumberOfRows());
            List<ExcelLineData> lineDatas = readExcelContentBySheetx(sheetx);
            sheetData.setLineData(lineDatas);
            sheetDatas.add(sheetData);
        }
        excelData.setSheetData(sheetDatas);
        return excelData;
    }


    /**
     * 按行读取一个Sheet中的内容 (xls)
     * @param sheet
     * @return
     */
    private List<ExcelLineData> readExcelContentBySheet(HSSFSheet sheet) {
        List<ExcelLineData> lineDatas = new ArrayList<>();
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        for (int i = 0; i <= rowNum; i++) {
            int j = 0;
            row = sheet.getRow(i);
            if (Objects.isNull(row)) {
                continue;
            }

            int colNum = row.getLastCellNum();
            ExcelLineData lineData = new ExcelLineData();
            List<String> colData = new ArrayList<>();
            lineData.setLineNumber(i);
            lineData.setColSum(colNum);
            while (j < colNum) {
                String value = getCellValue(row.getCell(j)).trim();
                colData.add(value);
                j++;
            }
            lineData.setColData(colData);
            lineDatas.add(lineData);
        }

        return lineDatas;
    }

    /**
     * 按行读取一个Sheet中的内容 (xlsx)
     * @param sheetx
     * @return
     */
    private List<ExcelLineData> readExcelContentBySheetx(XSSFSheet sheetx) {
        List<ExcelLineData> lineDatas = new ArrayList<>();
        // 得到总行数
        int rowNum = sheetx.getLastRowNum();
        for (int i = 0; i <= rowNum; i++) {
            int j = 0;
            rowx = sheetx.getRow(i);
            if (Objects.isNull(rowx)) {
                continue;
            }

            int colNum = rowx.getLastCellNum();
            ExcelLineData lineData = new ExcelLineData();
            List<String> colData = new ArrayList<>();
            lineData.setColSum(colNum);
            lineData.setLineNumber(i);
            while (j < colNum) {
                String value = getCellValuex(rowx.getCell(j)).trim();
                colData.add(value);
                j++;
            }
            lineData.setColData(colData);
            lineDatas.add(lineData);
        }

        return lineDatas;
    }

    /**
     * 获取单元格数据 (xls)
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getCellValue(HSSFCell cell) {
        if (Objects.isNull(cell)) {
            return "";
        }

        String value = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                // 时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                    break;
                } else {
                    value = new DecimalFormat("0").format(cell.getNumericCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                value = cell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                value = cell.getCellFormula() + "";
                break;
            case HSSFCell.CELL_TYPE_BLANK: // 空值
                value = "";
                break;
            case HSSFCell.CELL_TYPE_ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }

    /**
     * 获取单元格数据 (xlsx)
     * @param cellx Excel单元格
     * @return String 单元格数据内容
     */
    private String getCellValuex(XSSFCell cellx) {
        if (Objects.isNull(cellx)) {
            return "";
        }

        String value = "";
        switch (cellx.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC: // 数字
                // 时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(cellx)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = sdf.format(HSSFDateUtil.getJavaDate(cellx.getNumericCellValue())).toString();
                    break;
                } else {
                    value = new DecimalFormat("0").format(cellx.getNumericCellValue());
                }
                break;
            case XSSFCell.CELL_TYPE_STRING: // 字符串
                value = cellx.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                value = cellx.getBooleanCellValue() + "";
                break;
            case XSSFCell.CELL_TYPE_FORMULA: // 公式
                value = cellx.getCellFormula() + "";
                break;
            case XSSFCell.CELL_TYPE_BLANK: // 空值
                value = "";
                break;
            case XSSFCell.CELL_TYPE_ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }


    public static void main(String[] args) {
        ExcelReader excelReader = new ExcelReader();
        ExcelData excelData = excelReader.readExcel("D:\\OneDrive - 东南大学\\实习\\文件\\FileReader\\src\\main\\resources\\static\\2.xlsx");
        System.out.println(excelData.toString());
        for(ExcelSheetData excelSheetData:excelData.getSheetData()){
            System.out.println();
            System.out.println("*****************************************************************************");
            System.out.println("Sheet: "+excelSheetData.getSheetName());
            for(ExcelLineData excelLineData :excelSheetData.getLineData()){
                //System.out.println("----------------"+excelLineData.getColSum());
                if (excelLineData.lineNumber == 0){
                    System.out.println("列名：" + excelLineData.getColData());
                    continue;
                }
                System.out.println("第"+ excelLineData.lineNumber + "行数据："+excelLineData.getColData());
            }
        }

    }


    /**
     * 整个Excel表格类
     */
    public class ExcelData {
        // sheet数量
        private int sheetSum;
        // 文件名
        private String fileName;
        // sheet数据集合
        private List<ExcelSheetData> sheetData;

        public int getSheetSum() {
            return sheetSum;
        }

        public void setSheetSum(int sheetSum) {
            this.sheetSum = sheetSum;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public List<ExcelSheetData> getSheetData() {
            return sheetData;
        }

        public void setSheetData(List<ExcelSheetData> sheetData) {
            this.sheetData = sheetData;
        }
    }

    /**
     * 一张Sheet类
     */
    public class ExcelSheetData {
        // 工作簿名称
        private String sheetName;
        // 表格总行数
        private int lineSum;
        // 行数据集合
        private List<ExcelLineData> lineData;

        public String getSheetName() {
            return sheetName;
        }

        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        public int getLineSum() {
            return lineSum;
        }

        public void setLineSum(int lineSum) {
            this.lineSum = lineSum;
        }

        public List<ExcelLineData> getLineData() {
            return lineData;
        }

        public void setLineData(List<ExcelLineData> lineData) {
            this.lineData = lineData;
        }
    }

    /**
     * 一行数据类
     */
    public class ExcelLineData {
        // 行编号
        private int lineNumber;
        // 总列数
        private int colSum;
        // 列数据集合
        private List<String> colData;

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        public List<String> getColData() {
            return colData;
        }

        public void setColData(List<String> colData) {
            this.colData = colData;
        }
    }

}
