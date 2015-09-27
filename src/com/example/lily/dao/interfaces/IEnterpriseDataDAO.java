package com.example.lily.dao.interfaces;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.lily.javabeans.QueryDBResultHolder;

public interface IEnterpriseDataDAO {
	public QueryDBResultHolder queryEnterpriseData(String tableName, String matchColumn, String matchValue) throws Exception;

	public QueryDBResultHolder queryLikeEnterpriseData(String tableName, String matchColumn, String matchValue) throws Exception;

	public QueryDBResultHolder query(String sql) throws Exception;
	
	public QueryDBResultHolder query(String sql, SqlParameterSource namedParameters) throws Exception;
}
