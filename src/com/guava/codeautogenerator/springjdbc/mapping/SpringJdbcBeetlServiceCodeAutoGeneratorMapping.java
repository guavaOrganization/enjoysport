package com.guava.codeautogenerator.springjdbc.mapping;

import java.util.HashMap;
import java.util.Map;

import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.core.exception.CodeAutoGeneratorException;
import com.guava.codeautogenerator.core.mapping.AbstractBeetlCodeAutoMapping;

public class SpringJdbcBeetlServiceCodeAutoGeneratorMapping extends AbstractBeetlCodeAutoMapping{
	@Override
	public String getTemplateFilePath() throws CodeAutoGeneratorException {
		return "/com/guava/codeautogenerator/springjdbc/SpringJdbcBeetlService.btl";
	}

	@Override
	public Map<String, Object> getTemplateParam(ParameterHolder parameterHolder) throws CodeAutoGeneratorException {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("className", "TestService");
		return map;
	}
}
