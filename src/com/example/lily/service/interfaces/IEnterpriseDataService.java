package com.example.lily.service.interfaces;

import java.util.List;

import com.example.lily.javabeans.MatchingResultHolder;
import com.example.lily.javabeans.QueryDBResultHolder;

public interface IEnterpriseDataService {
	public QueryDBResultHolder queryEnterpriseData(String tableName, String matchColumn, String matchValue) throws Exception;

	public MatchingResultHolder matchingEnterpriseData(List<List<String>> sourceDatas, int startIndex, int endIndex, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns) throws Exception;

	public MatchingResultHolder matchingEnterpriseDataAndCreateResultToHolder(String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads) throws Exception;

	public void matchingEnterpriseDataAndreateResultFileToExcel(String targetAbsoluteFilePath, String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads) throws Exception;
}
