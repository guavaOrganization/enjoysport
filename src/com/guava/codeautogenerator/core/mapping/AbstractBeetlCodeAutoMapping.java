package com.guava.codeautogenerator.core.mapping;

import java.util.Map;

import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.core.exception.CodeAutoGeneratorException;

public abstract class AbstractBeetlCodeAutoMapping implements CodeAutoGeneratorMapping{
	public abstract String getTemplateFilePath() throws CodeAutoGeneratorException;

	public abstract Map<String, Object> getTemplateParam(ParameterHolder parameterHolder) throws CodeAutoGeneratorException;
}
