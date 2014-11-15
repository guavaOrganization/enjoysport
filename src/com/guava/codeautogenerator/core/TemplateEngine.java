package com.guava.codeautogenerator.core;

import com.guava.codeautogenerator.exception.CodeAutoGeneratorException;

public interface TemplateEngine {
	public ReturnValueHolder render(ParameterHolder parameterHolder,CodeAutoGeneratorMapping autoGeneratorMapping) throws CodeAutoGeneratorException;
}
