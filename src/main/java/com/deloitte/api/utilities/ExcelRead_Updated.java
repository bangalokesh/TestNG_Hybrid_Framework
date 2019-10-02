package com.deloitte.api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ExcelRead_Updated {

	public static List<String> list;
	public static List<List<String>> excelSheetRows;
	public static Workbook workBook = null;
	public static File file;
	public static FileInputStream inputStream;
	public static FileOutputStream outputStream;
	
	public static String componentColVal;
	public static String parentTagColVal;
	public static String tagColVal;
	public static String tagValColVal;
	public static String testId;
	
	public String getComponentVal() {
		return componentColVal;
	}

	public void setComponentVal(String componentColVal) {
		this.componentColVal = componentColVal;
	}
	
	public String getParentTagVal() {
		return parentTagColVal;
	}

	public void setParentTagVal(String parentTagColVal) {
		this.parentTagColVal = parentTagColVal;
	}
	
	
	public String getTagVal() {
		return tagColVal;
	}

	public void setTagVal(String tagColVal) {
		this.tagColVal = tagColVal;
	}
	
	public String getTagValColVal() {
		return tagValColVal;
	}

	public void setTagValColVal(String tagValColVal) {
		this.tagValColVal = tagValColVal;	
	}

	public static void getCellType(List<String> innerList, Row row, int j) {
		switch (row.getCell(j).getCellTypeEnum()) {
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
	
	public static Workbook fileType(String filePath, String fileName) throws IOException {
		file = new File(filePath + "/" + fileName);
		inputStream = new FileInputStream(file);		
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workBook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			workBook = new HSSFWorkbook(inputStream);
		}		
		return workBook;		
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
	
	/*Checks if Excel Column is Empty*/

	
	public static List<List<String>> readExcel(String filePath, String fileName, String sheetName) throws IOException {
		workBook = fileType(filePath, fileName);
		Sheet sheet = workBook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() + 1;
		int columnCount = sheet.getRow(0).getLastCellNum();
		excelSheetRows = new LinkedList<>();
		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
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
		return excelSheetRows;
	}
	
	/* Read Excel values per Test */
	
	
	public void excelData() throws IOException {
		List<List<String>> myList = readExcel(SoapConstants.TESTDATA_EXCEL_PATH, SoapConstants.TESTDATA_SOAP_FILENAME, "Add Request");
		
		int size = myList.size();

		Set<String> testCases = new HashSet<>();
		for (int i = 0; i < size; i++) {
			testCases.add(myList.get(i).get(0));
		}		
		
		for (String testCase : testCases) {
			//String testCase = "Test2";
			for (int j = 0; j < size; j++) {		
				if (myList.get(j).get(0).equals("")) {					
					myList.get(j).set(0, testId);
				} else {
					testId = myList.get(j).get(0);
				}
				if (myList.get(j).get(0).equals(testCase)) {					
					setComponentVal(myList.get(j).get(1));
					setParentTagVal(myList.get(j).get(2));
					setTagVal(myList.get(j).get(3));
					setTagValColVal(myList.get(j).get(4));
					System.out.println("Test Case         >> " +testId);
					System.out.println("getComponentVal   >> " +getComponentVal());
					System.out.println("getParentTagVal   >> " +getParentTagVal());
					System.out.println("getTagVal   	  >> " +getTagVal());
					System.out.println("getTagValColVal   >> " +getTagValColVal());
				}
			}
		}
	}
}
