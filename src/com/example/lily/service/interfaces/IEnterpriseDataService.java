package com.example.lily.service.interfaces;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.example.lily.javabeans.MatchingResultHolder;
import com.example.lily.javabeans.QueryDBResultHolder;

public interface IEnterpriseDataService {
	public QueryDBResultHolder queryEnterpriseData(String tableName, String matchColumn, String matchValue) throws Exception;

	public List<List<String>> matchingEnterpriseData(List<List<String>> excelDatas, int excelMatchIndex, String year, String dbMatchColName, List<String[]> ignoreDatas) throws Exception;

	public MatchingResultHolder matchingEnterpriseData(List<List<String>> sourceDatas, int startIndex, int endIndex, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns) throws Exception;

	public MatchingResultHolder matchingEnterpriseDataAndCreateResultToHolder(String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads) throws Exception;

	public void matchingEnterpriseDataAndreateResultFileToExcel(String targetAbsoluteFilePath, String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads) throws Exception;
	
	public void lily_20150529(String targetAbsoluteFilePath, String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads, int sheetNum) throws Exception;


	/**
	 * 修复指定List中的数据
	 * sourceDatas中的第一行对应列名为sourceTableName表字段名
	 * @param sourceDatas : 待修复数据列表
	 * @param startIndex : 指定从某行数据开始修复
	 * @param endIndex : 指定从某行数据结束修复
	 * @param repairStartIndex : 从List<String> 中的制定行开始修复
	 * @param matchIndexs : 匹配的列
	 * @param sourceTableName : 从sourceTableName中获取修复数据
	 * @param matchColumns : 与matchIndexs匹配的表字段名
	 * @since
	 * @throws
	 */
	public List<List<String>> repairListData(List<List<String>> sourceDatas, int startIndex, int endIndex, int repairStartIndex, int columnStartIndex, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns) throws Exception;

	/**
	 * 根据Acquiror name到2007年的工业企业数据库中匹配，如果工业企业数据库的企业名字和Acquiror
	 * name相同，则为同一家企业，则匹配成功
	 * ，然后把法人代码及其之后的列补充好如果2007年中找不到那个企业，则去2002年工业企业数据中进行匹配，规则相同。
	 * @since
	 * @throws
	 */
	public void lily20150510_1() throws Exception;
	
	public void lily20150510_2() throws Exception;
	
	/**
	 * 	工业企业数据库即2000transformed1文件；海关数据库1月份数据即2000-01文件。
		匹配步骤：
		Step 1、筛选装备制造业企业
		筛选标准:在工业企业数据库中，筛选“行业类别”前两位数为33、34、35、36、37、38、39、40的企业。
		         在海关数据库中，筛选“海关代码”为71-93的企业。
		         
		Step 2、匹配企业。
		匹配标准一：匹配企业名称。
		企业名称匹配程度达到85%以上即认为是同一家企业。
		将匹配好的企业在两大数据库的信息合并（不做整合，只是简单排列，就像上次你帮妹妹做的那样，有个分割线就行）。
		
		匹配标准二：匹配电话号码后7位+邮编
	 * @since
	 * @throws
	 */
	public void odan_20150510(boolean isConcurrent, String sourceTable, String tableName, String yearMonth, boolean isBatch , int index, int prodCode, int threshold) throws Exception;

	public QueryDBResultHolder step8(String sql, MapSqlParameterSource namedParameters) throws Exception;
}
