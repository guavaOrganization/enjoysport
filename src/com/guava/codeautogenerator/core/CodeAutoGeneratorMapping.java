package com.guava.codeautogenerator.core;

import java.util.Map;

import com.guava.codeautogenerator.exception.CodeAutoGeneratorException;

public interface CodeAutoGeneratorMapping {
	public String getTemplateFilePath() throws CodeAutoGeneratorException;

	public Map<String, Object> getTemplateParam(ParameterHolder parameterHolder) throws CodeAutoGeneratorException;
}
