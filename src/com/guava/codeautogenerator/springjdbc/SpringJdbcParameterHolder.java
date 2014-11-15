package com.guava.codeautogenerator.springjdbc;

import com.guava.codeautogenerator.core.ParameterHolder;

public class SpringJdbcParameterHolder extends ParameterHolder {
	private String tableName;
	private String baseBeanName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBaseBeanName() {
		return baseBeanName;
	}

	public void setBaseBeanName(String baseBeanName) {
		this.baseBeanName = baseBeanName;
	}
}
