package com.itsv.annotation.ratio.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class CaseExcl {

	private static int sheetSize = 100;

	private static String[] tableHeads = { "日期", "会议时间", "会议室", "责任单位", "参加领导" };

	public static void main(String[] args) throws Exception {

		// 一行表头
		List<String> headList = new ArrayList<String>();

		for (int i = 0; i < tableHeads.length; i++) {

			headList.add(tableHeads[i]);
		}

		List<String[]> listString = new ArrayList<String[]>();

		String[] body = { "6月25日", "9:00-12:00", "第三会议室", "朝阳区委办", "康子琪" };
		String[] body1 = { "6月25日", "9:00-12:00", "第三会议室", "朝阳区委办", "康子琪" };
		listString.add(body);
		listString.add(body1);
		// 一行数据
		List<String> row = null;
		List<List<String>> data = new ArrayList<List<String>>();
		for (int j = 0; j < listString.size(); j++) {
			String[] str = listString.get(j);
			row = new ArrayList<String>();
			for (int i = 0; i < tableHeads.length; i++) {
				row.add(str[i]);
			}
			data.add(row);
		}
		/**
		 * 导出
		 */
		//writeIntoXLS(headList, data, "C:/Result.xls");
		/**
		 * 导入
		 */
		List<String> outputExcl = readExcel("D:/qwpkFile/会议安排.xls");
		for (String ros : outputExcl) {
			if (ros.equals("")) {
				System.out.print("空\t");
			} else {
				System.out.print(ros + "\t");
			}
		}

	}

	public static Workbook getWorkBook(String fileName) throws Exception {
		Workbook work = null;
		//String path = System.getProperty("user.dir") + "/WebRoot/execl/";
		InputStream stream = new FileInputStream(fileName);
		work = Workbook.getWorkbook(stream);

		return work;
	}

	public static List<String> readExcel(String fileName) throws Exception {
		Workbook rwb = getWorkBook(fileName);
		Sheet sheet = rwb.getSheet(0);
		Cell cell = null;
		List<String> lists = new ArrayList<String>();
		for (int i = 2; i < sheet.getRows(); i++) {
			for (int j = 0; j < sheet.getColumns(); j++) {
				cell = sheet.getCell(j, i);
				lists.add(cell.getContents());
			}
			lists.add("\n");
		}
		return lists;
	}

	/**
	 * 导入
	 * @param headList 设置头
	 * @param bodyList 主题集合
	 * @param xlsFull 存入路径即文件名
	 */
	public static void writeIntoXLS(List<String> headList,
			List<List<String>> bodyList, OutputStream os, String metTime,
			String eTime,List<List<String>> bzList,List<List<String>> xzList,String remark,String dbsx) {

		WritableWorkbook wwb = null;

		try {

			int sheetNum = 1;// 当前sheet索引

			int recNum = 0; // 当前记录索引

			// 整理数据流，WritableWorkbook
			wwb = Workbook.createWorkbook(os);

			// 开始分SHEET，写数据
			while (true) {

				int rowNum = 0;

				String sheetName = "sheet" + sheetNum;

				// 建立SHEET
				WritableSheet ws = wwb.createSheet(sheetName, sheetNum);

				sheetNum++;

				// 设置样式
				WritableCellFormat format = new WritableCellFormat(
						NumberFormats.TEXT);
				//format.setAlignment(Alignment.CENTRE);
				format.setVerticalAlignment(VerticalAlignment.CENTRE);
				format.setAlignment(Alignment.JUSTIFY);
				format.setBorder(jxl.format.Border.ALL,
						jxl.format.BorderLineStyle.THIN);

				//大标题样式
				WritableCellFormat formattitle = new WritableCellFormat(
						new WritableFont(WritableFont.createFont("黑体"), 15,
								WritableFont.BOLD, false,
								UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK));
				formattitle.setAlignment(Alignment.CENTRE);//居中显示
				ws.addCell(new Label(0, rowNum, "区 领 导 一 周 重 要 会 议 活 动 安 排 （草稿）",
						formattitle));
				ws.mergeCells(0, rowNum, headList.size() - 1, rowNum);
				rowNum++;

				WritableCellFormat formatleft = new WritableCellFormat(
						NumberFormats.TEXT);
				formatleft.setAlignment(Alignment.LEFT);
				formatleft.setVerticalAlignment(VerticalAlignment.CENTRE);
				WritableCellFormat formatright = new WritableCellFormat(
						NumberFormats.TEXT);
				formatright.setAlignment(Alignment.RIGHT);
				formatright.setVerticalAlignment(VerticalAlignment.CENTRE);

				ws.addCell(new Label(0, rowNum, metTime, formatleft));
				ws.addCell(new Label(3, rowNum, eTime, formatright));
				ws.mergeCells(0, rowNum, 2, rowNum);
				ws.mergeCells(3, rowNum, 5, rowNum);
				rowNum++;
				// 写入表头信息
				WritableCellFormat format2 = new WritableCellFormat(
						new WritableFont(WritableFont.createFont("黑体"), 11,
								WritableFont.BOLD, false,
								UnderlineStyle.NO_UNDERLINE,
								jxl.format.Colour.BLACK));
				format2.setAlignment(Alignment.CENTRE);
				format2.setBorder(jxl.format.Border.ALL,
						jxl.format.BorderLineStyle.THIN);
				for (int i = 0; i < headList.size(); i++) {

					ws.addCell(new Label(i, rowNum, headList.get(i), format2));

				}

				
				for (int i = 0; i < bzList.size(); i++) {
					rowNum++;
					for(int j=0;j<bzList.get(i).size();j++){
						ws.addCell(new Label(j, rowNum, bzList.get(i).get(j), format));
					}
				}

				rowNum++;
				
				// 写入表数据行信息
				if (!bodyList.isEmpty()) {

					int sheetRecNum = 0;
					int oldrowNum = rowNum - 1;
					for (int i = recNum; i < bodyList.size(); i++, recNum++, rowNum++, sheetRecNum++) {

						// 单个SHEET数据行超过SHEET应达到数据限额，则停止写入
						if (sheetRecNum >= sheetSize)
							break;

						List<String> row = bodyList.get(i);
						ws.setColumnView(0, 15);
						ws.setColumnView(1, 7);
						ws.setColumnView(2, 18);
						ws.setColumnView(3, 18);
						ws.setColumnView(4, 18);
						ws.setColumnView(5, 18);
						for (int j = 0; j < row.size(); j++) {
							if(row.get(j)!=null){
							ws.addCell(new Label(j, rowNum, row.get(j)
									.toString(), format));
							}else{
								ws.addCell(new Label(j, rowNum, "", format));
							}
						}
						if (i == bodyList.size() - 1 && row.get(0).equals("")) {
							ws.mergeCells(0, oldrowNum, 0, rowNum);
							oldrowNum = rowNum;
						} else if (!row.get(0).equals("")) {
							ws.mergeCells(0, oldrowNum, 0, rowNum - 1);
							oldrowNum = rowNum;
						}
					}
				}
				for (int i = 0; i < xzList.size(); i++) {
					for(int j=0;j<xzList.get(i).size();j++){
						ws.addCell(new Label(j, rowNum, xzList.get(i).get(j), format));
					}
					rowNum++;
				}
				if(remark!=null&&!remark.equals("")){
					rowNum++;
					ws.addCell(new Label(0, rowNum, remark, format));
					ws.mergeCells(0, rowNum, 5, rowNum+1);
				}
				if(dbsx!=null&&!dbsx.equals("")){
					rowNum++;
					ws.addCell(new Label(0, rowNum, dbsx, format));
					ws.mergeCells(0, rowNum, 5, rowNum+1);
				}
				/*
				ws.mergeCells(0, 1, 0, 5);
				ws.mergeCells(0, 6, 0, 8);
				ws.mergeCells(0, 9, 0, 11);
				ws.mergeCells(0, 12, 0, 12);
				ws.mergeCells(0, 13, 0, 15);
				ws.mergeCells(0, 16, 0, 16);*/
				// wwb.write();--------------不能放到这里，移到下面（这个是从网上看到别人的经验得来）
				// 数据写完，退出
				if (recNum >= bodyList.size())
					break;
			}

			// PUSH当前内容到XLS文件中--------这一步很关键，sheet写好之后就要push到xls文件中，然后再新建
			wwb.write();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭所有数据流
			try {
				wwb.close();
				os.flush();
				os.close();
			} catch (Exception e) {
			}

		}
	}

}
