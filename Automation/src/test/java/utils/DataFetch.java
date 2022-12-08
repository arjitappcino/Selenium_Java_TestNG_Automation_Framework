package utils;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataFetch {

	private static XSSFSheet worksheet;
	private static XSSFWorkbook workbook;
	public static DataFormatter formatter = new DataFormatter();

	public static Object[][] readExcel(String Path, String SheetName) throws Exception {

		FileInputStream fileInputStream = new FileInputStream(Path); // Excel sheet file location get mentioned
		// here
		workbook = new XSSFWorkbook(fileInputStream); // get my workbook
		worksheet = workbook.getSheet(SheetName);// get my sheet from workbook
		XSSFRow Row = worksheet.getRow(0); // get my Row which start from 0

		int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows
		int ColNum = Row.getLastCellNum(); // get last ColNum
		System.out.println("Col last = "+ColNum);
		System.out.println("rows = "+RowNum);

		Object Data[][] = new Object[RowNum - 1][ColNum]; // pass my count data in array

		for (int i = 0; i < RowNum - 1; i++) // Loop work for Rows
		{
			XSSFRow row = worksheet.getRow(i + 1);

			for (int j = 0; j < ColNum; j++) // Loop work for colNum
			{
				if (row == null)
					Data[i][j] = "";
				else {
					XSSFCell cell = row.getCell(j);
					if (cell == null)
						Data[i][j] = ""; // if it get Null value it pass no data
					else {
						// This formatter get my all values as string i.e integer, float all type
						String value = formatter.formatCellValue(cell);
						// data value
						Data[i][j] = value;

					}
				}
			}
		}
		return Data;

	}

}