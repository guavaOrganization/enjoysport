package com.guava.codeautogenerator.springjdbc.mapping;

import java.util.Map;

import com.guava.codeautogenerator.core.CodeAutoGeneratorMapping;
import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.exception.CodeAutoGeneratorException;

public abstract class AbstractSpringJdbcBeetlCodeAutoMapping implements CodeAutoGeneratorMapping{
	public abstract String getTemplateFilePath() throws CodeAutoGeneratorException;

	public abstract Map<String, Object> getTemplateParam(ParameterHolder parameterHolder) throws CodeAutoGeneratorException;
}
