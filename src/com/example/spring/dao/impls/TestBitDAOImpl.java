package com.example.spring.dao.impls;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.spring.dao.interfaces.ITestBitDAO;

@Repository("com.example.spring.dao.impls.TestBitDAOImpl")
public class TestBitDAOImpl implements ITestBitDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {// 需要在xml上下文配置中配置<context:component-scan/>
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int countTestBitTotal() throws DataAccessException {
		return this.jdbcTemplate.queryForObject("select count(*) from testbit", Integer.class);
	}

	public void insertToUser(int id, String name) throws DataAccessException {
		this.jdbcTemplate.update("insert into t_user(id,name) values(?,?)", id, name);
	}
}
