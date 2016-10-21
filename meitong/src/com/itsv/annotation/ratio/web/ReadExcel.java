package com.itsv.annotation.ratio.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public  class ReadExcel {
	public static List<List<String>> readExcel(String excelPath)
			throws InvalidFormatException, FileNotFoundException, IOException {
		Workbook workbook = WorkbookFactory.create(new FileInputStream(
				new File(excelPath)));
		Sheet sheet = workbook.getSheetAt(0);
		int startRowNum = sheet.getFirstRowNum();
		int endRowNum = sheet.getLastRowNum();
		List<List<String>> list = new ArrayList<List<String>>();
		for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {
			List<String> sList = new ArrayList<String>();
			Row row = sheet.getRow(rowNum);
			if (row == null)
				continue;
			int startCellNum = row.getFirstCellNum();
			int endCellNum = row.getLastCellNum();
			for (int cellNum = startCellNum; cellNum < endCellNum; cellNum++) {
				Cell cell = row.getCell(cellNum);
				if (cell == null)
					continue;
				int type = cell.getCellType();
				switch (type) {
				case Cell.CELL_TYPE_NUMERIC:// 数值、日期类型
					double d = cell.getNumericCellValue();
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 日期类型
					// Date date = cell.getDateCellValue();
						Date date = HSSFDateUtil.getJavaDate(d);
						sList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "");
						System.out.print(" "
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(date) + " ");
					} else {// 数值类型
						DecimalFormat df = new DecimalFormat("0.0");//转换成整型
						String ints=df.format(d);
						sList.add(ints);
						System.out.print(" " + d + " ");
					}
					break;
				case Cell.CELL_TYPE_BLANK:// 空白单元格
					sList.add("");
					System.out.print(" null ");
					break;
				case Cell.CELL_TYPE_STRING:// 字符类型
					sList.add("" + cell.getStringCellValue());
					System.out.print(" " + cell.getStringCellValue() + " ");
					break;
				case Cell.CELL_TYPE_BOOLEAN:// 布尔类型
					sList.add(""+cell.getBooleanCellValue());
					System.out.println(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					System.err.println("非法字符");// 非法字符;
					break;
				default:
					System.err.println("error");// 未知类型
					break;
				}
			}
			list.add(sList);
			System.out.println();
		}
		return list;
	}

	public static void main(String[] args) {
		ReadExcel ReadExcel2 = new ReadExcel();
		try {
			ReadExcel2
					.readExcel("C:\\Users\\zhou\\Desktop\\消费.xlsx");
			ReadExcel2
					.readExcel("C:\\Users\\zhou\\Desktop\\消费.xls");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}