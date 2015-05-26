package com.example.lily.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.guava.util.GuavaExcelUtil;

public class GovernmentData {
	public static void main(String[] args) throws IOException {
		// 初始值：Grid1CurrentPage:0,Grid1toPageNo:1
		// 结束值：Grid1CurrentPage:1553,Grid1toPageNo:1554
		HttpClient client = new HttpClient();
		// 设置代理服务器地址和端口
		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
		PostMethod method = new PostMethod( "http://wszw.hzs.mofcom.gov.cn/fecp/fem/corp/fem_cert_stat_view_list.jsp");
		List<List<String>> allResult = new ArrayList<List<String>>();
		for (int index = 0; index < 1885; index++) {
			NameValuePair grid1toPageNo = new NameValuePair("Grid1toPageNo", String.valueOf(index + 1));
			NameValuePair grid1CurrentPage = new NameValuePair("Grid1CurrentPage", String.valueOf(index));
			NameValuePair pageFlag = new NameValuePair("PageFlag", "1");
			method.setRequestBody(new NameValuePair[] { grid1toPageNo, grid1CurrentPage, pageFlag });
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
		// 释放连接
		method.releaseConnection();
	}
}
