package com.guava.codeautogenerator.springjdbc.mapping;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.core.exception.CodeAutoGeneratorException;
import com.guava.codeautogenerator.core.mapping.AbstractBeetlCodeAutoMapping;
import com.guava.codeautogenerator.core.support.StringUtils;
import com.guava.codeautogenerator.springjdbc.SpringJdbcParameterHolder;

public class SpringJdbcBeetlControllerCodeAutoGeneratorMapping extends AbstractBeetlCodeAutoMapping {
	@Override
	public String getTemplateFilePath() throws CodeAutoGeneratorException {
		return "/com/guava/codeautogenerator/springjdbc/SpringJdbcBeetlController.btl";
	}

	@Override
	public Map<String, Object> getTemplateParam(ParameterHolder parameterHolder) throws CodeAutoGeneratorException {
		SpringJdbcParameterHolder springJdbcParameterHolder = (SpringJdbcParameterHolder) parameterHolder;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put(StringUtils.KEY_MODULE_NAME, springJdbcParameterHolder.getCodeModuleName());
		map.put(StringUtils.KEY_SUB_MODULE_NAME, StringUtils.PACKAGE_CONTROLLER);
		map.put(StringUtils.KEY_CLASS_NAME, getClassName(springJdbcParameterHolder));
		return map;
	}

	@Override
	public String getFullPathAndFileName(ParameterHolder parameterHolder) {
		return parameterHolder.getFileModuleName() + StringUtils.PACKAGE_CONTROLLER + File.separatorChar + getClassName(parameterHolder);
	}

	@Override
	public String getClassName(ParameterHolder parameterHolder) {
		SpringJdbcParameterHolder springJdbcParameterHolder = (SpringJdbcParameterHolder) parameterHolder;
		return springJdbcParameterHolder.getBaseBeanName() + StringUtils.CONTROLLER_CLASS_SUFFIX_NAME;
	}
}
