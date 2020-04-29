package com.organization.api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ExcelRead {

	static List<String> list;
	static List<List<String>> excelSheetRows;
	
	/*Set Cell Data Type*/


	public static void getCellType(List<String> innerList, Row row, int j) {
		switch (row.getCell(j).getCellTypeEnum()) {
		
		case _NONE:
		    innerList.add("");
		    break;
		
		case BLANK:
		    innerList.add("");
			break;

		case STRING:
			innerList.add(row.getCell(j).getStringCellValue());
			break;

		case NUMERIC:
			innerList.add(row.getCell(j).getNumericCellValue() + "");
			break;

		case BOOLEAN:
			innerList.add(row.getCell(j).getBooleanCellValue() + "");
			break;

		default:
			throw new IllegalArgumentException("Cannot read the column : " + j);
		}
	}

	public static List<List<String>> readExcel(String filePath, String fileName, String sheetName) throws IOException {
		File file = new File(filePath + "/" + fileName);

		FileInputStream inputStream = new FileInputStream(file);

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		Workbook workBook = null;

		if (fileExtensionName.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			workBook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			// If it is xls file then create object of HSSFWorkbook class
			workBook = new HSSFWorkbook(inputStream);
		}

		Sheet sheet = workBook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() + 1;
		int columnCount = 0 ;
		excelSheetRows = new LinkedList<>();
		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			if(i==1) {
				columnCount=row.getLastCellNum();
			}
			
			list = new LinkedList<>();
			for (int j = 0; j < columnCount; j++) {
				if(isCellEmpty(row.getCell(j))) {
					row.getCell(j,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					
				}
				getCellType(list, row, j);
							}
			excelSheetRows.add(list);
		}
		inputStream.close();
		//System.out.println("File closed");
		//System.out.println("Final List >>>>>>>>>>>>>> " + excelSheetRows);
		return excelSheetRows;
	}

	/*Checks if Excel Cell is Empty*/

	public static boolean isCellEmpty(final Cell cell) {
	    if (cell == null || cell.getCellTypeEnum() == CellType.BLANK) {
	        return true;
	    }

	    if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue().isEmpty()) {
	        return true;
	    }

	    return false;
	}
	
	/*Read column data for each Test*/

	public void excelData() throws IOException {

		List<List<String>> myList = readExcel(SoapConstants.TESTDATA_EXCEL_PATH, SoapConstants.TESTDATA_SOAP_FILENAME, "Request Operation 1");
		String testId = "";
		int size = myList.size();

		Set<String> testCases = new HashSet<>();
		for (int i = 0; i < size; i++) {
			testCases.add(myList.get(i).get(0));
		}

		for (String testCase : testCases) {   // For all test cases associated values
			//String testCase = "Test2";   // If want to get specific test case associated value
			
			System.out.println("Value of iterator Test Case >> " + testCase);
			for (int j = 0; j < size; j++) {
				
				if (myList.get(j).get(0).equals("")) {					
					myList.get(j).set(0, testId);
				} else {
					testId = myList.get(j).get(0);
				}
				
				if (myList.get(j).get(0).equals(testCase)) {
				////	for(int k =0; k < myList.get(j).size(); k++) {
					try {
						System.out.println("Specific value from list >> " + myList.get(j).get(0));
						System.out.println("Specific value from list >> " + myList.get(j).get(1));
						System.out.println("Specific value from list >> " + myList.get(j).get(2));
						System.out.println("Specific value from list >> " + myList.get(j).get(3));
						System.out.println("Specific value from list >> " + myList.get(j).get(4));
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
					//}					
				}
			}
		}

	}

}
