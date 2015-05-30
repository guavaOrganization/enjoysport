package com.example.lily.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.lily.service.impls.EnterpriseDataServiceImpl;
import com.guava.util.GuavaExcelUtil;

public class GovernmentData {
	public static final String EAST_AREA = "北京市,天津市,河北省,辽宁省,上海市,江苏省,浙江省,福建省,山东省,广东省,海南省";
	public static final String CENTRAL_AREA = "黑龙江省,吉林省,山西省,安徽省,江西省,河南省,湖北省,湖南省";
	public static final String WEST_AREA = "四川省,重庆市,贵州省,云南省,西藏自治区,陕西省,甘肃省,青海省,宁夏回族自治区,新疆维吾尔自治区,广西壮族自治区,内蒙古自治区";
	public static final String CONTROL_AREA = "商务部,中央企业";

	public static void main(String[] args) throws Exception {
		String fileNamePrefix = "E:\\lily_mcfly\\丽丽--企业财务数据\\网站数据\\新增投资统计";
		List<List<String>> list = GuavaExcelUtil.loadExcelDataToList(fileNamePrefix + ".xlsx");
		List<List<List<String>>> results = new ArrayList<List<List<String>>>();
		step5_1(list, results);
		
		OutputStream os;
		String targetAbsoluteFilePath = fileNamePrefix + "_单国多国投资"+  ".xlsx";
		os = new FileOutputStream(targetAbsoluteFilePath);
		XSSFWorkbook wb = new XSSFWorkbook();
		for (int i = 0; i < results.size(); i++) {
			String sheetName = "";
			if (i == 0) {
				sheetName = "单国";
			} else if (i == 1) {
				sheetName = "多国";
			}
			GuavaExcelUtil.writeDataToExcel(results.get(i), sheetName, wb, os);
		}
		wb.write(os);
		os.close();
	}

	// 单国投资/多国投资
	public static void step5() throws Exception {
		String fileNamePrefix = "E:\\lily_mcfly\\丽丽--企业财务数据\\网站数据\\境外直接投资企业名录";
		List<List<List<String>>> list = GuavaExcelUtil.loadExcelDataToList(fileNamePrefix + "_再细化.xlsx", 5);
		List<List<List<String>>> results = new ArrayList<List<List<String>>>();
		for (int i = 0; i < list.size(); i++) {
			List<List<String>> sheet = list.get(i);
			step5_1(sheet, results);
		}
		
		OutputStream os;
		String targetAbsoluteFilePath = fileNamePrefix + "_单国多国投资"+  ".xlsx";
		os = new FileOutputStream(targetAbsoluteFilePath);
		XSSFWorkbook wb = new XSSFWorkbook();
		for (int i = 0; i < results.size(); i++) {
			String sheetName = "";
			if (i == 0) {
				sheetName = "东部地区（单国）";
			} else if (i == 1) {
				sheetName = "东部地区（多国）";
			} else if (i == 2) {
				sheetName = "中部地区（单国）";
			} else if (i == 3) {
				sheetName = "中部地区（多国）";
			} else if (i == 4) {
				sheetName = "西部地区（单国）";
			} else if (i == 5) {
				sheetName = "西部地区（多国）";
			} else if (i == 6) {
				sheetName = "中央企业，商务部（单国）";
			} else if (i == 7) {
				sheetName = "中央企业，商务部（多国）";
			} else if (i == 8) {
				sheetName = "其他地区（单国）";
			} else if (i == 9) {
				sheetName = "其他地区（多国）";
			}
			GuavaExcelUtil.writeDataToExcel(results.get(i), sheetName, wb, os);
		}
		wb.write(os);
		os.close();
	}
	
	public static void step5_1(List<List<String>> sheet, List<List<List<String>>> results) {
		List<List<String>> single = new ArrayList<List<String>>();
		List<List<String>> multi = new ArrayList<List<String>>();
		single.add(sheet.get(0));
		multi.add(sheet.get(0));
		Set<String> set = new HashSet<String>();
		
		for (int i = 0; i < sheet.size(); i++) {
			if (i == 0)
				continue;
			List<String> row = sheet.get(i);
			String key = row.get(5);
			key = null == key || "".equals(key.trim()) ? "" : key.trim();
			if ("".equals(key)) {
				single.add(row);
				continue;
			}
			if (set.contains(key)) { // set用于记录已经比较过的数据
				multi.add(row);
				continue;
			}
			boolean isFind = false;
			for (int j = 0; j < sheet.size(); j++) {
				if (j == 0 || i == j) // 第一行和本身不进行比较
					continue;
				List<String> tempRow = sheet.get(j);
				String tempKey = tempRow.get(5);
				tempKey = null == tempKey || "".equals(tempKey.trim()) ? "" : tempKey.trim();
				if ("".equals(tempKey))
					continue;
				if(key.equals(tempKey)){
					isFind = true;
					break;
				}
			}
			if (isFind)
				multi.add(row);
			else
				single.add(row);
			set.add(key);
		}
		results.add(single);
		results.add(multi);
	}
	

	// TODO STEP 4
	public static void step4() throws Exception {
		String fileNamePrefix = "E:\\lily_mcfly\\丽丽--企业财务数据\\网站数据\\境外直接投资企业名录";
		List<List<String>> list = GuavaExcelUtil.loadExcelDataToList(fileNamePrefix + "_去重_地区细化.xlsx");
		List<List<String>> list1 = new ArrayList<List<String>>();
		List<List<String>> list2 = new ArrayList<List<String>>();
		List<List<String>> list3 = new ArrayList<List<String>>();
		List<List<String>> list4 = new ArrayList<List<String>>();
		List<List<String>> list5 = new ArrayList<List<String>>();
		List<String> head = list.get(0);
		list1.add(head);
		list2.add(head);
		list3.add(head);
		list4.add(head);
		list5.add(head);
		for (int i = 0; i < list.size(); i++) {
			if (i == 0)
				continue;
			List<String> row = list.get(i);
			String tempStr = row.get(7);
			if (EAST_AREA.indexOf(tempStr) >= 0) {
				list1.add(row);
			} else if (CENTRAL_AREA.indexOf(tempStr) >= 0) {
				list2.add(row);
			} else if (WEST_AREA.indexOf(tempStr) >= 0) {
				list3.add(row);
			} else if (CONTROL_AREA.indexOf(tempStr) >= 0) {
				list4.add(row);
			} else {
				list5.add(row);
			}
		}
		
		OutputStream os;
		String targetAbsoluteFilePath = fileNamePrefix + "_再细化"+  ".xlsx";
		os = new FileOutputStream(targetAbsoluteFilePath);
		XSSFWorkbook wb = new XSSFWorkbook();
		GuavaExcelUtil.writeDataToExcel(list1, "东部地区", wb, os);
		GuavaExcelUtil.writeDataToExcel(list2, "中部地区", wb, os);
		GuavaExcelUtil.writeDataToExcel(list3, "西部地区", wb, os);
		GuavaExcelUtil.writeDataToExcel(list4, "中央企业,商务部", wb, os);
		GuavaExcelUtil.writeDataToExcel(list5, "其他地区", wb, os);
		wb.write(os);
		os.close();
	}
	
	// TODO step3
	public static void distinguishArea() throws IOException {
		String fileNamePrefix = "E:\\lily_mcfly\\丽丽--企业财务数据\\网站数据\\境外直接投资企业名录_去重";
		List<List<String>> list = GuavaExcelUtil.loadExcelDataToList(fileNamePrefix + ".xlsx");
		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			if (i == 0) {
				row.add(3, "是否为亚洲国家（地区）(1:亚洲国家,0:非亚洲国家)");
				row.add(4, "是否为高收入国家(1:高收入国家;0:非高收入其他国家)");
				continue;
			}
			
			String isAsiaCountry = "0";
			if (EnterpriseDataServiceImpl.ASIA_COUNTRY.indexOf(row.get(2)) >= 0) { // 是否为亚洲国家
				isAsiaCountry = "1";
			} else {
				isAsiaCountry = "0";
			}
			String isDeveloped = "0";
			if (EnterpriseDataServiceImpl.DEVELOPED_COUNTRY.indexOf(row.get(2)) >= 0) { // 是否为高收入国家
				isDeveloped = "1";
			} else {
				isDeveloped = "0";
			}
			row.add(3, isAsiaCountry);
			row.add(4, isDeveloped);
		}
		OutputStream os;
		String targetAbsoluteFilePath = fileNamePrefix + "_地区细化"+  ".xlsx";
		os = new FileOutputStream(targetAbsoluteFilePath);
		XSSFWorkbook wb = new XSSFWorkbook();
		GuavaExcelUtil.writeDataToExcel(list, "境外直接投资企业名录", wb, os);
		wb.write(os);
		os.close();
	}
	
	// TODO step2
	public static void mergeData() throws IOException {
		String fileNamePrefix = "E:\\lily_mcfly\\丽丽--企业财务数据\\网站数据\\境外直接投资企业名录";
		List<List<String>> allRow = new ArrayList<List<String>>();
		int count = 0;
		List<List<String>> list = GuavaExcelUtil.loadExcelDataToList(fileNamePrefix + ".xlsx");
		for (int j = 0; j < list.size(); j++) {
			List<String> row = list.get(j);
			String tempStr = row.get(7);
			if (null != tempStr && tempStr.startsWith("2014")){
				count++;
				continue;
			}
			allRow.add(row);
		}
		System.out.println("count 数量为  " + count);
		OutputStream os;
		String targetAbsoluteFilePath = fileNamePrefix + "_去重.xlsx";
		os = new FileOutputStream(targetAbsoluteFilePath);
		XSSFWorkbook wb = new XSSFWorkbook();
		GuavaExcelUtil.writeDataToExcel(allRow, "匹配到结果的数据", wb, os);
		wb.write(os);
		os.close();
	}
	
	// TODO step1
	public static void loadPageData() throws IOException {
		List<List<String>> allResult = new ArrayList<List<String>>();
		int nThreads = 1;
		ExecutorService exec = Executors.newFixedThreadPool(nThreads);
		int pageTotal = 156;
		List<Future<List<List<String>>>> futures = new ArrayList<Future<List<List<String>>>>(nThreads);
		for (int index = 0; index < nThreads; index++) {
			int start = index * (pageTotal / nThreads);// 开始下标
			int end = 0;
			if (index == nThreads - 1) {
				end = (index + 1) * (pageTotal / nThreads) + pageTotal % nThreads;
			} else {
				end = (index + 1) * (pageTotal / nThreads);
			}
			futures.add(exec.submit(new GovernmentCallable(start, end)));
		}
		
		for (Future<List<List<String>>> future : futures) {
			try {
				allResult.addAll(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println("allResult 的长度为---->" + allResult.size());
		try {
			int resultSize = allResult.size();
			int fileCount = resultSize / 4000;
			if (resultSize % 4000 != 0)
				fileCount++;
			String targetAbsoluteFileDir = "E:\\lily_mcfly\\丽丽--企业财务数据\\网站数据\\境外直接投资企业名录_";
			List<String> head = new ArrayList<>();
			head.add("证书号");
			head.add("国家/地区");
			head.add("境内投资主体");
			head.add("境外投资企业（机构）");
			head.add("省市");
			head.add("经营范围");
			head.add("核准日期");
			head.add("境外注册日期");
			
			for (int i = 0; i < fileCount; i++) {
				OutputStream os;
				int start = i * (resultSize / fileCount);// 开始下标
				int end = 0;
				if (i == fileCount - 1) {
					end = (i + 1) * (resultSize / fileCount) + resultSize % fileCount;
				} else {
					end = (i + 1) * (resultSize / fileCount);
				}
				System.out.println("生成文件，文件开始下标为：【" + start + "】,结束下标为【" + end + "】");
				String targetAbsoluteFilePath = targetAbsoluteFileDir + i + ".xlsx";
				os = new FileOutputStream(targetAbsoluteFilePath);
				XSSFWorkbook wb = new XSSFWorkbook();
				GuavaExcelUtil.writeDataToExcel(start, end, head, allResult, "匹配到结果的数据", wb, os);
				wb.write(os);
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class GovernmentCallable implements Callable<List<List<String>>>{
		private int start;
		private int end;
		
		public GovernmentCallable(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public List<List<String>> call() throws Exception {
			// 初始值：Grid1CurrentPage:0,Grid1toPageNo:1
			// 结束值：Grid1CurrentPage:1553,Grid1toPageNo:1554
			HttpClient client = new HttpClient();
			// 设置代理服务器地址和端口
			// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
			// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
			PostMethod method = new PostMethod("http://wszw.hzs.mofcom.gov.cn/fecp/fem/corp/fem_cert_stat_view_list.jsp?manage=%C9%CC%CE%F1%B2%BF&check_dte_nian=1980&check_dte_nian2=2016&check_dte_yue=01&check_dte_yue2=12&CERT_NO=&COUNTRY_CN_NA=&CORP_NA_CN=&CHECK_DTE=");
			List<List<String>> allResult = new ArrayList<List<String>>();
			System.out.println("当前任务查询的数据为-->第【" + (start + 1) + "】 至 【" + end + "】页的数据");
			for (int index = start; index < end; index++) {
				System.out.println("开始整理============第【" + (index + 1) + "】页的数据");
				if (index != 0) {
					NameValuePair grid1toPageNo = new NameValuePair("Grid1toPageNo", String.valueOf(index + 1));
					NameValuePair grid1CurrentPage = new NameValuePair("Grid1CurrentPage", String.valueOf(index));
					NameValuePair pageFlag = new NameValuePair("PageFlag", "1");
					method.setRequestBody(new NameValuePair[] { grid1toPageNo, grid1CurrentPage, pageFlag });
				}
				client.executeMethod(method);
				String responseStr = method.getResponseBodyAsString();
				if (StringUtils.isNotBlank(responseStr)) {
					responseStr = responseStr.substring(responseStr.indexOf("<tr class='listTableBody' height=\"20\">"));
				}
				responseStr = responseStr.substring(0, responseStr.indexOf("</table></form>"));
				
				String[] strings = responseStr.split("<tr class='listTableBody' height=\"20\">");
				for (int i = 0; null != strings && i < strings.length; i++) {
					String str = strings[i];
					if(StringUtils.isBlank(str))
						continue;
					str = str.substring(0, str.indexOf("<div><input type=HIDDEN name=\"StatusColumn\" size=\"1\"></div></td>"));
					String[] detailInfos = str.split("</td>");
					List<String> row = new ArrayList<String>();
					for (int j = 0; null != detailInfos && j < detailInfos.length; j++) {
						String tempStr = detailInfos[j];
						tempStr = tempStr.substring(tempStr.indexOf("<div>") + "<div>".length(), tempStr.indexOf("</div>"));
						row.add(tempStr);
						if(j == detailInfos.length-1)
							row.add("暂无");
					}
					allResult.add(row);
				}
			}
			method.releaseConnection();
			return allResult;
		}
	}
}
