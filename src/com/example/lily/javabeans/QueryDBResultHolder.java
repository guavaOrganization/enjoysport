package com.example.lily.javabeans;

import java.util.List;

public class QueryDBResultHolder {
	List<List<String>> resultDatas;
	List<String> resultMetaDatas;

	public List<List<String>> getResultDatas() {
		return resultDatas;
	}

	public void setResultDatas(List<List<String>> resultDatas) {
		this.resultDatas = resultDatas;
	}

	public List<String> getResultMetaDatas() {
		return resultMetaDatas;
	}

	public void setResultMetaDatas(List<String> resultMetaDatas) {
		this.resultMetaDatas = resultMetaDatas;
	}
}
