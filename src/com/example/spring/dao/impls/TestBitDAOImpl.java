package com.example.spring.dao.impls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.spring.dao.interfaces.ITestBitDAO;
import com.example.spring.javabeans.TestBitUser;

@Repository("com.example.spring.dao.impls.TestBitDAOImpl")
public class TestBitDAOImpl implements ITestBitDAO {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {// 需要在xml上下文配置中配置<context:component-scan/>
		if (this.jdbcTemplate == null || dataSource != this.jdbcTemplate.getDataSource()) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
		if (this.namedParameterJdbcTemplate == null) {
			this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		}
	}

	public int countTestBitTotal() throws DataAccessException {
		return this.jdbcTemplate.queryForObject("select count(*) from testbit", Integer.class);
	}

	// 可以使用update方法完成"insert"、"update"和"delete"操作
	public void insertToUser(int id, String name) throws DataAccessException {
		this.jdbcTemplate.update("insert into t_user(id,name) values(?,?)", id, name);
	}
	
	public void updateUser(int id, String name) throws DataAccessException {
		this.jdbcTemplate.update("update t_user set name = ? where id = ?", name, id);
	}
	
	public void deleteUserById(int id) throws DataAccessException{
		this.jdbcTemplate.update("delete from t_user where id = ?", id);
	}

	public String getUserNameById(int id) throws DataAccessException {
		String name = this.jdbcTemplate.queryForObject("select name from t_user where id = ?", new Object[] { id }, String.class);
		return name;
	}
	
	public String getUserNameByIdWithNamedParameterJdbcTemplate(int id) throws DataAccessException {
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
		String name = this.namedParameterJdbcTemplate.queryForObject("select name from t_user where id = :id", namedParameters, String.class);
		return name;
	}
	
	public String getUserNameByIdWithMap(int id) throws DataAccessException {
		Map param = new HashMap();
		param.put("id", id);
		// Map(param)将会被包装成MapSqlParameterSource对象
		String name = this.namedParameterJdbcTemplate.queryForObject("select name from t_user where id = :id", param, String.class);
		return name;
	}
	
	public String getUserNameByIdWithBeanProperty(TestBitUser bitUser) throws DataAccessException{
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(bitUser);
		String name = this.namedParameterJdbcTemplate.queryForObject("select name from t_user where id = :id", namedParameters, String.class);
		return name;
	}

	public TestBitUser getUserById(int id) throws DataAccessException {
		TestBitUser bitUser = this.jdbcTemplate.queryForObject("select * from t_user where id = ?", new Object[] { id },
				new RowMapper<TestBitUser>() {
					@Override
					public TestBitUser mapRow(ResultSet rs, int paramInt) throws SQLException {
						TestBitUser user = new TestBitUser();
						user.setId(rs.getInt("id"));
						user.setName(rs.getString("name"));
						return user;
					}
				});
		return bitUser;
	}
	
	public List<TestBitUser> getAllUser() throws DataAccessException{
		List<TestBitUser> bitUsers = this.jdbcTemplate.query("select * from t_user", new TestBitUserMapper());
		return bitUsers;
	}
	
	private static final class TestBitUserMapper implements RowMapper<TestBitUser> {// 可以将此部分提取出来当作一个公共类
		@Override
		public TestBitUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			TestBitUser user = new TestBitUser();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			return user;
		}
	}
}
