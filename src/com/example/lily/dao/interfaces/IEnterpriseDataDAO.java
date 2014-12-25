package com.example.lily.dao.interfaces;

import com.example.lily.javabeans.QueryDBResultHolder;

public interface IEnterpriseDataDAO {
	public QueryDBResultHolder queryEnterpriseData(String tableName, String matchColumn, String matchValue) throws Exception;
}
