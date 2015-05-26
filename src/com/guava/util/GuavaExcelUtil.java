package com.guava.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.guava.codeautogenerator.core.support.StringUtils;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 *	<p>
 *		Java Excel工具类
 *		依赖
 *			Apache poi-3.11-20141221.jar
 *			poi-ooxml-3.11-20141221.jar
 *			poi-ooxml-schemas-3.11-20141221.jar
 *			xmlbeans-2.6.0
 *	</p>
 * 
 * @author 八两俊
 * @since
 */
public class GuavaExcelUtil {
	private transient static Log log = LogFactory.getLog(GuavaExcelUtil.class);
	
	public static void writeDataToExcel(List<List<String>> dataList, String sheetName, XSSFWorkbook wb, OutputStream os, String fileName) throws IOException {
		if (null == os)
			os = new FileOutputStream(fileName);
		if (null == wb) {
			wb = new XSSFWorkbook();
		}
		writeDataToExcel(dataList, sheetName, wb, os);  
		wb.write(os);
		os.close();// 关闭输出流  
	}
	
	public static void writeDataToExcel(List<List<String>> dataList, String sheetName, XSSFWorkbook wb, OutputStream os) throws IOException {
		if (null == wb) {
			throw new IllegalArgumentException("XSSFWorkbook不允许为空");
		}
		if (null == os) {
			throw new IllegalArgumentException("OutputStream不允许为空");
		}
		XSSFSheet sheet = wb.createSheet(sheetName); // 创建Sheet页
		for (int i = 0; i < dataList.size(); i++) {
			List<String> datas = dataList.get(i);
			XSSFRow row = sheet.createRow(i);
			for (int j = 0; j < datas.size(); j++) {
				row.createCell(j).setCellValue(datas.get(j));
			}
		}
	}
	
	public static void writeDataToExcel(int startIndex, int endIndex, List<String> head, List<List<String>> dataList, String sheetName, XSSFWorkbook wb, OutputStream os) throws IOException {
		if (null == wb) {
			throw new IllegalArgumentException("XSSFWorkbook不允许为空");
		}
		if (null == os) {
			throw new IllegalArgumentException("OutputStream不允许为空");
		}
		int writingRowIndex = 0;
		XSSFSheet sheet = wb.createSheet(sheetName); // 创建Sheet页
		if (null != head) {
			XSSFRow row = sheet.createRow(writingRowIndex++);
			for (int j = 0; j < head.size(); j++) {
				row.createCell(j).setCellValue(head.get(j));
			}
		}
		
		for (int i = startIndex; i < endIndex; i++) {
			List<String> datas = dataList.get(i);
			XSSFRow row = sheet.createRow(writingRowIndex++);
			for (int j = 0; j < datas.size(); j++) {
				row.createCell(j).setCellValue(datas.get(j));
			}
		}
	}
	
	public static List<List<String>> loadExcelDataToList(String absolutePath) {
		return loadExcelDataToList(absolutePath, 1).get(0);
	}
	
	/**
	 * 从excel中加载所有的列，然后存放到List<List<String>>中
	 * @since
	 * @throws
	 */
	public static List<List<List<String>>> loadExcelDataToList(String absolutePath, int sheetNum) {
		InputStream is = null;
		XSSFWorkbook workbook = null;
		DecimalFormat df = new DecimalFormat("0"); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List<List<List<String>>> excelAllDatas = new ArrayList<List<List<String>>>();
		try {
			long now = System.currentTimeMillis();
			is = new FileInputStream(new File(absolutePath));
			workbook = new XSSFWorkbook(is);
			for (int index = 0; index < sheetNum; index++) {
				List<List<String>> sheetAllDatas = new ArrayList<List<String>>();
				XSSFSheet sheet = workbook.getSheetAt(index);// 读取第一个sheet页数据
				for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) { // 迭代行
					XSSFRow xssfRow = sheet.getRow(i);
					if(null == xssfRow){
						System.out.println("第[" + (i + 1) + "]行为空");
						break;
					}
					List<String> rows = new ArrayList<String>();
					for (int j = xssfRow.getFirstCellNum(); j < xssfRow.getLastCellNum(); j++) {// 迭代列
						XSSFCell xssfCell = xssfRow.getCell(j);
						if (null == xssfCell) {
							rows.add(StringUtils.EMPTY);
							continue;
						}
						if (Cell.CELL_TYPE_NUMERIC == xssfCell.getCellType()) {// 数字 和 时间
							if(HSSFDateUtil.isCellDateFormatted(xssfCell)) {// 时间
								rows.add(sdf.format(xssfCell.getDateCellValue()));
							}else{
								rows.add(df.format(xssfCell.getNumericCellValue()));
							}
						} else if (Cell.CELL_TYPE_BOOLEAN == xssfCell.getCellType()) {// Blooean类型
							rows.add(xssfCell.getBooleanCellValue() + "");
						} else {
							rows.add(xssfCell.toString());
						}
					}
					sheetAllDatas.add(rows);
				}
				excelAllDatas.add(sheetAllDatas);
			}
			if(log.isInfoEnabled())
				log.info("耗时：" + (System.currentTimeMillis() - now));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != workbook) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return excelAllDatas;
	}
}
