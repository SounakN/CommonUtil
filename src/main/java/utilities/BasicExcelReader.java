package utilities;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


@Slf4j
public class BasicExcelReader {


    @SneakyThrows
    public static HashMap<Integer, ArrayList<String>> readExcelIntoHashMap(@NonNull String fileName, @NonNull String sheetName) {
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        try (InputStream inputStream = BasicExcelReader.class.getClassLoader().getResourceAsStream(fileName)) {
            try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheet(sheetName);
                int rowCount = sheet.getLastRowNum();
                int columnCount = sheet.getRow(0).getLastCellNum();
                ArrayList<String> listOfData = new ArrayList<>();
                for (int i = 0; i <= rowCount; i++) {
                    Row row = sheet.getRow(i);
                    for (int j = 0; j < columnCount; j++) {
                        Cell cell = row.getCell(j);
                        if (cell == null) {
                            listOfData.add("");
                        } else {
                            CellType type = cell.getCellType();
                            if (type == CellType.STRING) {
                                listOfData.add(row.getCell(j).getStringCellValue());
                            } else if (type == CellType.NUMERIC) {
                                listOfData.add(String.valueOf((row.getCell(j).getNumericCellValue())));
                            } else if (type == CellType.FORMULA) {
                                DecimalFormat df = new DecimalFormat("0.0");
                                listOfData.add(df.format(row.getCell(j).getNumericCellValue()));
                            } else if (type == CellType.BLANK) {
                                listOfData.add(Strings.EMPTY);
                            }
                        }
                    }
                    map.put((i), listOfData);
                    listOfData = new ArrayList<>();
                }
            } catch (Exception e) {
                log.info("Exception thrown in WorkBook Resource:: " + e.getMessage());
                return null;
            }
            return map;
        } catch (Exception e) {
            log.info("Exception thrown in File Input Stream Resource:: " + e.getMessage());
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





