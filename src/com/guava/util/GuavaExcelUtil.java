package com.guava.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

	/**
	 * 从excel中加载所有的列，然后存放到List<List<String>>中
	 * @since
	 * @throws
	 */
	public static List<List<String>> loadExcelDataToList(String absolutePath) {
		InputStream is = null;
		XSSFWorkbook workbook = null;
		List<List<String>> allDatas = new ArrayList<List<String>>();
		try {
			long now = System.currentTimeMillis();
			is = new FileInputStream(new File(absolutePath));
			workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);// 读取第一个sheet页数据
			for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) { // 迭代行
				XSSFRow xssfRow = sheet.getRow(i);
				List<String> rows = new ArrayList<String>();
				for (int j = xssfRow.getFirstCellNum(); j < xssfRow.getPhysicalNumberOfCells(); j++) {// 迭代列
					XSSFCell xssfCell = xssfRow.getCell(j);
					rows.add(null == xssfCell ? "" : xssfCell.toString());
				}
				allDatas.add(rows);
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
		return allDatas;
	}
}
