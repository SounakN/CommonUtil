package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class Basic_Excel_Reader {



	public static HashMap<Integer, ArrayList<String>> Basic_Excel_Reader(String filename, String sheetname) {

		HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
		try
		{
			File file = new File(filename);
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetname);
			int rowcount = sheet.getLastRowNum(); 
			int columncount = sheet.getRow(0).getLastCellNum();
			int j;
			ArrayList<String> obj = new ArrayList<String>();
			for(int i = 0;i<=rowcount;i++)
			{

				Row row = sheet.getRow(i);
				for(j = 0;j<columncount;j++)
				{
					Cell cell = row.getCell(j);
					if(cell == null){
						obj.add("");
					}else{
						CellType type = cell.getCellType();
						if(type == CellType.STRING)
						{
							obj.add(row.getCell(j).getStringCellValue());
						}
						else if(type == CellType.NUMERIC)
						{
							Object s1 =(double) row.getCell(j).getNumericCellValue();
							obj.add(s1.toString());
							s1 = null;
						}
						else if(type == CellType.FORMULA)
						{
							DecimalFormat df=new DecimalFormat("0.0");
							String formate = df.format(row.getCell(j).getNumericCellValue());
							obj.add(formate);
							formate = null;
						}else if(type == CellType.BLANK){
							obj.add("");
						}
					}


				}

				map.put((i), obj);
				obj = new ArrayList<String>();
			}

			//System.out.println(map);

			fis.close();
			workbook.close();
			return map;
		}
		catch(Exception e)
		{	
			System.out.print(e.getMessage());
			return null;
		}


	}

	/*public ArrayList<String> Get_Column_Values(String column)
		{
			try
			{
				File file = new File(VariableController.datasheet);
				FileInputStream fis = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(fis); 
				XSSFSheet sheet = workbook.getSheet(VariableController.google);
				int rowcount = sheet.getLastRowNum(); 
				int columncount = sheet.getRow(0).getLastCellNum();

				int j;
				ArrayList<String> obj = new ArrayList<String>();


					Row row = sheet.getRow(0);
					for(j =0;j<columncount;j++)
					{
						Cell cell = row.getCell(j);
						String cellvalue = cell.getStringCellValue();
						if(cellvalue.equalsIgnoreCase(column))
						{
							for(int i =1;i<=rowcount;i++)
							{
								Row row2 = sheet.getRow(i);
								System.out.println(row2.getCell(j).getStringCellValue());
								obj.add(row2.getCell(j).getStringCellValue());
							}
						}
						else
						{
							continue; 
						}
					}
					fis.close();
					workbook.close();
					return obj;

			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
				return null;
			}
	 */
	/*public static String[][] getExcelData(String fileName, String sheetName , String TestName) 


		{

			String[][] arrayExcelData = null;
			int count = 0;
			int k = 0;
			String Active = "Y";
			try 		
			{

				FileInputStream fs = new FileInputStream(fileName);
				Workbook wb = Workbook.getWorkbook(fs);
				Sheet sh = wb.getSheet(sheetName);

				int totalNoOfCols = sh.getColumns();
				int totalNoOfRows = sh.getRows();


			// Check whether excel sheet with test data is empty or not	



				//Get the No. of Rows with Condition
				for (int x= 0 ; x < totalNoOfRows; x++) 

				{

					if(sh.getCell(3, x).getContents().equalsIgnoreCase(TestName) && sh.getCell(1, x).getContents().equalsIgnoreCase(Active))

						{
							count++;
						}
					}


				File file = new File(fileName);
				FileInputStream fis = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(fis); 
				XSSFSheet sheet = workbook.getSheet(sheetName);
				int rowcount = sheet.getLastRowNum(); 
				int columncount = sheet.getRow(0).getLastCellNum();

				for(int i = 0; i<=rowcount;i++)
				{
					Row row = sheet.getRow(i);
					if(row.getCell(3).getStringCellValue().equalsIgnoreCase(TestName) && row.getCell(1).getStringCellValue().equalsIgnoreCase(Active))
					{
						columncount = row.getLastCellNum();
						count++;
					}
				}			

				arrayExcelData = new String[count][columncount];	

				for (int i= 0 ; i <=rowcount; i++) 
				{				 
					Row row = sheet.getRow(i);
					if(row.getCell(3).getStringCellValue().equalsIgnoreCase(TestName) && row.getCell(1).getStringCellValue().equalsIgnoreCase(Active))
					{
						for (int j=0; j <columncount; j++) 				
						{
							arrayExcelData[k][j] = row.getCell(j).getStringCellValue();
						}   				
						k++;			
					}

				}


			} 

			catch (FileNotFoundException e) 

			{
				e.printStackTrace();
			} 

			catch (IOException e) 

			{
				e.printStackTrace();
				e.printStackTrace();
			}
			return arrayExcelData;
		}*/


}





