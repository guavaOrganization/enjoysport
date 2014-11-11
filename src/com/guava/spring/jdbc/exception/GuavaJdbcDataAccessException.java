package com.guava.spring.jdbc.exception;

import org.springframework.dao.DataAccessException;

public class GuavaJdbcDataAccessException extends DataAccessException {

	public GuavaJdbcDataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
