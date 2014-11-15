package com.guava.codeautogenerator.core.templateengine;

import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.core.ReturnValueHolder;
import com.guava.codeautogenerator.core.mapping.CodeAutoGeneratorMapping;
import com.guava.codeautogenerator.exception.CodeAutoGeneratorException;

public interface TemplateEngine {
	public ReturnValueHolder render(ParameterHolder parameterHolder,CodeAutoGeneratorMapping autoGeneratorMapping) throws CodeAutoGeneratorException;
}
