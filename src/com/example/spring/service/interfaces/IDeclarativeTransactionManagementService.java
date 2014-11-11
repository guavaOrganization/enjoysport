package com.example.spring.service.interfaces;

import java.util.List;

import com.example.spring.javabeans.TestBitUser;

public interface IDeclarativeTransactionManagementService {
	public void analyzeDeclarativeTransactionManagementBaseProxy() throws Exception;
	
	public void createDeclarativeTransactionManagementBaseProxy() throws Exception;

	public int countTestBitTotal() throws Exception;
	
	public void createUser() throws Exception;
	
	public void updateUser(int id, String name) throws Exception;
	
	public void deleteUserById(int id) throws Exception;

	public String retrieveUserNameById(int id) throws Exception;
	
	public String retrieveUserNameByIdWithNamedParameterJdbcTemplate(int id) throws Exception;
	
	public String retrieveUserNameByIdWithMap(int id) throws Exception;
	
	public String retrieveUserNameByIdWithBeanProperty(int id) throws Exception;

	public TestBitUser retrieveUserById(int id) throws Exception;
	
	public List<TestBitUser> retrieveAllUser() throws Exception;
}
