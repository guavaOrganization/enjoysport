package com.example.spring.dao.interfaces;

import org.springframework.dao.DataAccessException;

public interface ITestBitDAO {
	public int countTestBitTotal() throws DataAccessException;
	
	public void insertToUser(int id, String name) throws DataAccessException;
}
