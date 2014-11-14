package com.example.spring.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import com.example.spring.javabeans.TestBitUser;

public class TestBitUserMappingSqlQuery extends MappingSqlQuery<TestBitUser> {
	public TestBitUserMappingSqlQuery(DataSource ds) {
		super(ds, "select id, name from t_user where id = ?");
		super.declareParameter(new SqlParameter("id", Types.INTEGER));
		compile();// 执行完compile()方法后，此类已经被标注为线程安全(thread-safe)
	}

	@Override
	protected TestBitUser mapRow(ResultSet rs, int rowNumber) throws SQLException {
		TestBitUser bitUser = new TestBitUser();
		bitUser.setId(rs.getInt("id"));
		bitUser.setName(rs.getString("name"));
		return bitUser;
	}
}
