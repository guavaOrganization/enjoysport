package com.example.spring.dao.interfaces;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.spring.javabeans.TestBitUser;

public interface ITestBitDAO {
	public int countTestBitTotal() throws DataAccessException;
	
	public void insertToUser(int id, String name) throws DataAccessException;
	
	public void updateUser(int id, String name) throws DataAccessException;
	
	public void deleteUserById(int id) throws DataAccessException;

	public String getUserNameById(int id) throws DataAccessException;
	
	public String getUserNameByIdWithNamedParameterJdbcTemplate(int id) throws DataAccessException;
	
	public String getUserNameByIdWithMap(int id) throws DataAccessException;
	
	public String getUserNameByIdWithBeanProperty(TestBitUser bitUser) throws DataAccessException;
	
	public TestBitUser getUserById(int id) throws DataAccessException;
	
	public List<TestBitUser> getAllUser() throws DataAccessException;
	
}
