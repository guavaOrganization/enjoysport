package com.guava.codeautogenerator.springjdbc.handler;

import com.guava.codeautogenerator.core.AbstractCodeAutoGeneratorHandler;
import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.exception.CodeAutoGeneratorException;

public class SpringJdbcAutoGeneratorHandler extends AbstractCodeAutoGeneratorHandler {
	@Override
	protected void preprocessingAutoGeneratorCodeInternal(ParameterHolder parameterHolder) throws CodeAutoGeneratorException{
		setNeedCheckParameter(false);// 设置是否需要校验入参
	}
	
	@Override
	protected void postprocessingAutoGeneratorCode(ParameterHolder parameterHolder) throws CodeAutoGeneratorException {
	}
	
	@Override
	protected void checkParameterHolderInternal(ParameterHolder parameterHolder) throws CodeAutoGeneratorException{// 校验
		if(null == parameterHolder.getCustomParameter())
			throw new CodeAutoGeneratorException("The Parameter 'customParameter' Is Null");
	}
	
	@Override
	protected void handleAutoGeneratorCodeInternal(ParameterHolder parameterHolder) throws CodeAutoGeneratorException{
	}
}
