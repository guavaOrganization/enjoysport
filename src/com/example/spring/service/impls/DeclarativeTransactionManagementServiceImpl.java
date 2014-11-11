package com.example.spring.service.impls;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.spring.dao.interfaces.ITestBitDAO;
import com.example.spring.javabeans.TestBitUser;
import com.example.spring.service.interfaces.IDeclarativeTransactionManagementService;

@Service("com.example.spring.service.impls.DeclarativeTransactionManagementServiceImpl")
public class DeclarativeTransactionManagementServiceImpl implements IDeclarativeTransactionManagementService {
	private transient static Log log = LogFactory.getLog(DeclarativeTransactionManagementServiceImpl.class);
 
	@Autowired
	@Qualifier("com.example.spring.dao.impls.TestBitDAOImpl")
	private ITestBitDAO testBitDAO;
	public void analyzeDeclarativeTransactionManagementBaseProxy() throws Exception{
		log.debug("invoking analyzeDeclarativeTransactionManagementBaseProxy analyzeDeclarativeTransactionManagementBaseProxy() method");
	}
	
	public void createDeclarativeTransactionManagementBaseProxy() throws Exception{
		log.debug("invoking analyzeDeclarativeTransactionManagementBaseProxy createDeclarativeTransactionManagementBaseProxy method");
	}
	
	public int countTestBitTotal() throws Exception{
		int count = testBitDAO.countTestBitTotal();
		log.info("count is " + count);
		return count;
	}
	
	public void createUser() throws Exception{
		testBitDAO.insertToUser(3, "曾德田");
	}
	
	public void updateUser(int id, String name) throws Exception{
		testBitDAO.updateUser(id, name);
	}
	
	public void deleteUserById(int id) throws Exception{
		testBitDAO.deleteUserById(id);
	}
	
	public String retrieveUserNameById(int id) throws Exception{
		String name = testBitDAO.getUserNameById(id);
		log.info("Retrieve User Is Success,User Id is" + id + " And Name is " + name);
		return name;
	}
	
	public String retrieveUserNameByIdWithNamedParameterJdbcTemplate(int id) throws Exception{
		String name = testBitDAO.getUserNameByIdWithNamedParameterJdbcTemplate(id);
		log.info("Retrieve User Is Success,User Id is" + id + " And Name is " + name);
		return name;
	}
	
	public String retrieveUserNameByIdWithMap(int id) throws Exception{
		String name = testBitDAO.getUserNameByIdWithMap(id);
		log.info("Retrieve User Is Success,User Id is" + id + " And Name is " + name);
		return name;
	}
	
	public String retrieveUserNameByIdWithBeanProperty(int id) throws Exception{
		TestBitUser bitUser = new TestBitUser();
		bitUser.setId(id);
		String name = testBitDAO.getUserNameByIdWithBeanProperty(bitUser);
		log.info("retrieveUserNameByIdWithBeanProperty >>>> Retrieve User Is Success,User Id is" + id + " And Name is " + name);
		return name;
		
	}
	public TestBitUser retrieveUserById(int id) throws Exception {
		TestBitUser user = testBitDAO.getUserById(id);
		log.info("Retrieve User Is Success,User info : " + user);
		return user;
	}

	public List<TestBitUser> retrieveAllUser() throws Exception {
		List<TestBitUser> bitUsers = testBitDAO.getAllUser();
		log.info("Total User Number is " + bitUsers == null ? 0 : bitUsers.size());
		for (int i = 0; null != bitUsers && i < bitUsers.size(); i++) {
			log.info("User Info : " + bitUsers.get(i));
		}
		return bitUsers;
	}
}
