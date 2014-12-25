package com.example.lily.dao.impls;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.lily.dao.interfaces.IEnterpriseDataDAO;
import com.example.lily.javabeans.QueryDBResultHolder;

@Repository("com.example.lily.dao.impls.EnterpriseDataDAOImpl")
public class EnterpriseDataDAOImpl implements IEnterpriseDataDAO {
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {// 需要在xml上下文配置中配置<context:component-scan/>
		if (this.namedParameterJdbcTemplate == null) {
			this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		}
	}
	
	public QueryDBResultHolder queryEnterpriseData(String tableName, String matchColumn, String matchValue) throws Exception {
		QueryDBResultHolder holder = new QueryDBResultHolder();
		SqlParameterSource namedParameters = new MapSqlParameterSource(matchColumn, matchValue);
		final List<List<String>> allDatas = new ArrayList<List<String>>();
		final List<String> resultMetaDatasList = new ArrayList<String>();
		this.namedParameterJdbcTemplate.query("select * from " + tableName + " where " + matchColumn + " = :" + matchColumn + "", namedParameters, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet paramResultSet) throws SQLException {
				List<String> row = new ArrayList<String>();
				ResultSetMetaData metaData = paramResultSet.getMetaData();
				int columnCount = metaData.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					if (resultMetaDatasList.size() <= columnCount) { // 查询结果列名添加到resultMetaDatasList中返回
						resultMetaDatasList.add(columnName);
					}
					row.add(paramResultSet.getString(columnName));
				}
				allDatas.add(row);
			}
		});
		holder.setResultDatas(allDatas);// 查询结果
		holder.setResultMetaDatas(resultMetaDatasList);// 查询结果列名
		return holder;
	}
}
