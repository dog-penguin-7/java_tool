

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class GetExcelData {
    public static void main(String[] args) {
        GetExcelInfo();
    }

    static void GetExcelInfo() {
        String excelPath = "D:\\gile\\kepler.xlsx";

        try {
            File excel = new File(excelPath);
            if (excel.isFile() && excel.exists()) {
                // 转义特殊字符
                String[] split = excel.getName().split("\\.");
                Workbook wb;
                // 判断文件后缀（xls/xlsx）
                if ( "xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);
                    wb = new HSSFWorkbook(fis);
                }else if ("xlsx".equals(split[1])){
                    wb = new XSSFWorkbook(excel);
                }else {
                    System.out.println("文件类型错误!");
                    return;
                }

                // 读取sheet 0
                Sheet sheet = wb.getSheetAt(0);

                // 跳过第一个列名
                int firstRowIndex = sheet.getFirstRowNum()+1;
                int lastRowIndex = sheet.getLastRowNum();
//                System.out.println("firstRowIndex: "+firstRowIndex);
//                System.out.println("lastRowIndex: "+lastRowIndex);

                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    Row row = sheet.getRow(rIndex);

                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
//                        int lastCellIndex = row.getLastCellNum();
                        int lastCellIndex = 3;

                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);

                            if (cell != null) {
                                String cellStr = cell.toString();

                                if ("快递承运商".equalsIgnoreCase(cellStr) && cIndex == 2)
                                if (cIndex == 0) {
                                    System.out.print("{\"" + cellStr.replace(".0", "") + "\",\"");
                                }
                                else {
                                    String afterDeal = cellStr;
                                    System.out.println(afterDeal + "\"},");
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}