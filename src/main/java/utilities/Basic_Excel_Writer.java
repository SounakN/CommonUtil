package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Basic_Excel_Writer {

    public static void Excel_Writer_Existing_Excel(String filename, String sheetname, HashMap<Integer, ArrayList<String>> values) {
        try
        {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetname);
            int rowcount = sheet.getLastRowNum();
            int columncount = sheet.getRow(0).getLastCellNum();

            for(int i = 0;i<=rowcount;i++)
            {
                Row row = sheet.getRow(i);
                for(int j = 0;j<columncount;j++)
                {
                    Cell cell = row.createCell(j);
                    cell.setCellValue((String)values.get(i).get(j));
                }

            }

            fis.close();

            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            //Close the workbook and output stream
            workbook.close();
            out.close();

        }
        catch(Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

    public static void Excel_Writer_New_Excel_Google_SEO_Crawling_Specifics(String filename, String sheetname, HashMap<Integer, ArrayList<String>> values) {
        try
        {
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet(sheetname);
            CellStyle cellStyle_Passed = workbook.createCellStyle();
            cellStyle_Passed.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            cellStyle_Passed.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            CellStyle cellStyle_Failed = workbook.createCellStyle();
            cellStyle_Failed.setFillForegroundColor(IndexedColors.RED.getIndex());
            cellStyle_Failed.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            CellStyle cellStyle_Captcha = workbook.createCellStyle();
            cellStyle_Captcha.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            cellStyle_Captcha.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for(int i = 0;i<values.size();i++)
            {
                Row row = sheet.createRow(i);
                for(int j = 0;j<values.get(i).size();j++)
                {
                    Cell cell = row.createCell(j);
                    cell.setCellValue((String)values.get(i).get(j));
                    if(values.get(i).get(j).contains("Passed")){
                        cell.setCellStyle(cellStyle_Passed);
                    }else if(values.get(i).get(j).contains("Failed")){
                        cell.setCellStyle(cellStyle_Failed);
                    }else if(values.get(i).get(j).contains("CAPTCHA")){
                        cell.setCellStyle(cellStyle_Captcha);
                    }

                }
            }

            int columncount = sheet.getRow(0).getLastCellNum();
            for(int i = 0; i < columncount; i++) {
                sheet.autoSizeColumn(i);
            }



            File file = new File(filename);
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            //Close the workbook and output stream
            workbook.close();
            out.close();

        }
        catch(Exception e)
        {
            System.out.print(e.getMessage());
        }
    }


}
