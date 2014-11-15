package com.guava.codeautogenerator.core.mapping;

import com.guava.codeautogenerator.core.ParameterHolder;

public interface CodeAutoGeneratorMapping {
	public String getFullPathAndFileName(ParameterHolder parameterHolder);
	
	public String getClassName(ParameterHolder parameterHolder);
}
