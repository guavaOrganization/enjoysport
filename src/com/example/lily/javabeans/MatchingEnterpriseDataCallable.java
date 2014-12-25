package com.example.lily.javabeans;

import java.util.List;
import java.util.concurrent.Callable;

import com.example.lily.service.interfaces.IEnterpriseDataService;

public class MatchingEnterpriseDataCallable implements
		Callable<MatchingResultHolder> {
	List<List<String>> sourceDatas;
	int startIndex;
	int endIndex;
	int matchIndex;
	String sourceTableName;
	String matchColumn;
	IEnterpriseDataService enterpriseDataService;

	public MatchingEnterpriseDataCallable() {
	}

	public MatchingEnterpriseDataCallable(List<List<String>> sourceDatas,
			int startIndex, int endIndex, int matchIndex,
			String sourceTableName, String matchColumn,
			IEnterpriseDataService enterpriseDataService) {
		super();
		this.sourceDatas = sourceDatas;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.matchIndex = matchIndex;
		this.sourceTableName = sourceTableName;
		this.matchColumn = matchColumn;
		this.enterpriseDataService = enterpriseDataService;
	}



	@Override
	public MatchingResultHolder call() throws Exception {
		return enterpriseDataService.matchingEnterpriseData(sourceDatas,
				startIndex, endIndex, matchIndex, sourceTableName, matchColumn);
	}
}
