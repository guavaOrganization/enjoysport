package com.guava.codeautogenerator.springjdbc.handler;

import java.io.IOException;
import java.util.Properties;

import com.guava.codeautogenerator.core.AbstractCodeAutoGeneratorHandler;
import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.core.TemplateEngine;
import com.guava.codeautogenerator.exception.CodeAutoGeneratorException;
import com.guava.codeautogenerator.support.PropertiesUtils;

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
		setNeedCheckParameter(false);// 设置是否需要校验入参
	}
	
	@Override
	protected void checkParameterHolderInternal(ParameterHolder parameterHolder) throws CodeAutoGeneratorException{// 校验
		if(null == parameterHolder.getCustomParameter())
			throw new CodeAutoGeneratorException("The Parameter 'customParameter' Is Null");
		if(null == parameterHolder || !(parameterHolder.getTemplateEngine() instanceof TemplateEngine))
			throw new CodeAutoGeneratorException("ParameterHolder's field[templateEngine] is null or not implements TemplateEngine");
	}
}
