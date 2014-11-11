package com.example.spring.service.impls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.spring.dao.interfaces.ITestBitDAO;
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
}
