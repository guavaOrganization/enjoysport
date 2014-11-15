package com.guava.codeautogenerator.core.templateengine;

import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.core.ReturnValueHolder;
import com.guava.codeautogenerator.core.exception.CodeAutoGeneratorException;
import com.guava.codeautogenerator.core.mapping.CodeAutoGeneratorMapping;

public interface TemplateEngine {
	public ReturnValueHolder render(ParameterHolder parameterHolder,CodeAutoGeneratorMapping autoGeneratorMapping) throws CodeAutoGeneratorException;
}
