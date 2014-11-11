package com.guava.spring.jdbc.support;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import com.guava.spring.jdbc.exception.GuavaJdbcDataAccessException;

public class GuavaJdbcSQLErrorCodesTranslator extends SQLErrorCodeSQLExceptionTranslator {
	@Override
	protected DataAccessException customTranslate(String task, String sql, SQLException sqlEx) {
		if (sqlEx.getErrorCode() == 12321321) {
			return new GuavaJdbcDataAccessException(task, sqlEx);
		}
		return null;
	}
}
