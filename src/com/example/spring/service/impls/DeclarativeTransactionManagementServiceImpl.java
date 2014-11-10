package com.example.spring.service.impls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.example.spring.service.interfaces.IDeclarativeTransactionManagementService;

@Service("com.example.spring.service.impls.DeclarativeTransactionManagementServiceImpl")
public class DeclarativeTransactionManagementServiceImpl implements IDeclarativeTransactionManagementService {
	private transient static Log log = LogFactory.getLog(DeclarativeTransactionManagementServiceImpl.class);
 
	public void analyzeDeclarativeTransactionManagementBaseProxy() throws Exception{
		log.debug("invoking analyzeDeclarativeTransactionManagementBaseProxy analyzeDeclarativeTransactionManagementBaseProxy() method");
	}
	
	public void createDeclarativeTransactionManagementBaseProxy() throws Exception{
		log.debug("invoking analyzeDeclarativeTransactionManagementBaseProxy createDeclarativeTransactionManagementBaseProxy method");
	}
}
