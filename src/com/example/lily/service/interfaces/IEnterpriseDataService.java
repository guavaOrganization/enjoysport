package com.example.lily.service.interfaces;

import java.util.List;

import com.example.lily.javabeans.MatchingResultHolder;
import com.example.lily.javabeans.QueryDBResultHolder;

public interface IEnterpriseDataService {
	public QueryDBResultHolder queryEnterpriseData(String tableName, String matchColumn, String matchValue) throws Exception;

	public MatchingResultHolder matchingEnterpriseData(List<List<String>> sourceDatas, int startIndex, int endIndex, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns) throws Exception;

	public MatchingResultHolder matchingEnterpriseDataAndCreateResultToHolder(String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads) throws Exception;

	public void matchingEnterpriseDataAndreateResultFileToExcel(String targetAbsoluteFilePath, String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads) throws Exception;


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
}
