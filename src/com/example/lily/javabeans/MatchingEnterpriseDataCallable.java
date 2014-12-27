package com.example.lily.javabeans;

import java.util.List;
import java.util.concurrent.Callable;

import com.example.lily.service.interfaces.IEnterpriseDataService;

public class MatchingEnterpriseDataCallable implements
		Callable<MatchingResultHolder> {
	List<List<String>> sourceDatas;
	int startIndex;
	int endIndex;
	List<Integer> matchIndexs;
	String sourceTableName;
	List<String> matchColumns;
	IEnterpriseDataService enterpriseDataService;

	public MatchingEnterpriseDataCallable() {
	}

	public MatchingEnterpriseDataCallable(List<List<String>> sourceDatas,
			int startIndex, int endIndex, List<Integer>  matchIndexs,
			String sourceTableName, List<String> matchColumns,
			IEnterpriseDataService enterpriseDataService) {
		super();
		this.sourceDatas = sourceDatas;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.matchIndexs = matchIndexs;
		this.sourceTableName = sourceTableName;
		this.matchColumns = matchColumns;
		this.enterpriseDataService = enterpriseDataService;
	}



	@Override
	public MatchingResultHolder call() throws Exception {
		return enterpriseDataService.matchingEnterpriseData(sourceDatas, startIndex, endIndex, matchIndexs, sourceTableName, matchColumns);
	}
}
