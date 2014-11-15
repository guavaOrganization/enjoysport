package com.guava.codeautogenerator.springjdbc.handler;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.core.exception.CodeAutoGeneratorException;
import com.guava.codeautogenerator.core.handler.AbstractCodeAutoGeneratorHandler;
import com.guava.codeautogenerator.core.support.PropertiesUtils;
import com.guava.codeautogenerator.core.support.StringUtils;
import com.guava.codeautogenerator.springjdbc.SpringJdbcParameterHolder;

public class SpringJdbcAutoGeneratorHandler extends AbstractCodeAutoGeneratorHandler {
	private static final String SPRING_JDBC_PROPERTIES_PATH = "/com/guava/codeautogenerator/springjdbc/SpringJdbcCodeAutoGeneratorDefault.properties";
	
	public SpringJdbcAutoGeneratorHandler() throws IOException {
		super();
	}
	
	protected Properties loadCustomProperties() throws IOException{
		return PropertiesUtils.loadProperties(SPRING_JDBC_PROPERTIES_PATH);
	}

	@Override
	protected void preprocessingAutoGeneratorCodeInternal(ParameterHolder parameterHolder) throws CodeAutoGeneratorException{
		setNeedCheckParameter(true);// 设置是否需要校验入参
	}
	
	@Override
	protected void checkParameterHolderInternal(ParameterHolder parameterHolder) throws CodeAutoGeneratorException{// 校验
		SpringJdbcParameterHolder springJdbcParameterHolder = (SpringJdbcParameterHolder) parameterHolder;
		if (StringUtils.isBlank(springJdbcParameterHolder.getBaseBeanName()))
			throw new CodeAutoGeneratorException("The Parameter 'baseBeanName' Is Null");
	}
	
	@Override
	protected String getTheEndFileFullPathAndName() {
		return PROJECT_ROOT_PATH + File.separatorChar + "tempDir" + File.separatorChar + System.currentTimeMillis() + "_" + getTheEndFileName();
	}
}
