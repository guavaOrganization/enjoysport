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

	public String readUserNameById(int id) throws Exception;
	
	public String readUserNameByIdWithNamedParameterJdbcTemplate(int id) throws Exception;
	
	public String readUserNameByIdWithMap(int id) throws Exception;
	
	public String readUserNameByIdWithBeanProperty(int id) throws Exception;

	public TestBitUser readUserById(int id) throws Exception;
	
	public List<TestBitUser> readAllUser() throws Exception;
	
	public int[] updateUser(List<TestBitUser> bitUsers) throws Exception;
	
	public int[] updateUserWithNamedParameterJdbcTemplate(List<TestBitUser> bitUsers) throws Exception;
}
