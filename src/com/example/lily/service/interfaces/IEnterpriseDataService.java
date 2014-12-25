package com.example.lily.service.interfaces;

import java.util.List;

import com.example.lily.javabeans.MatchingResultHolder;
import com.example.lily.javabeans.QueryDBResultHolder;

public interface IEnterpriseDataService {
	public QueryDBResultHolder queryEnterpriseData(String tableName, String matchColumn, String matchValue) throws Exception;

	public MatchingResultHolder matchingEnterpriseData(List<List<String>> sourceDatas, int startIndex, int endIndex, int matchIndex, String sourceTableName, String matchColumn) throws Exception;

	public MatchingResultHolder matchingEnterpriseDataAndCreateResultWithExcel(String sourceAbsoluteFilePath, int excelMatchIndex, String sourceTableName, String matchColumn, boolean isConcurrent, int nThreads) throws Exception;
}
