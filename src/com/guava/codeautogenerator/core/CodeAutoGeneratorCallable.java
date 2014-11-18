package com.guava.codeautogenerator.core;

import java.util.concurrent.Callable;

import com.guava.codeautogenerator.core.mapping.CodeAutoGeneratorMapping;

public class CodeAutoGeneratorCallable implements Callable<ReturnValueHolder> {
	private final CodeAutoGeneratorMapping mapping;
	private final ParameterHolder parameterHolder;

	public CodeAutoGeneratorCallable(CodeAutoGeneratorMapping mapping, ParameterHolder parameterHolder) {
		this.mapping = mapping;
		this.parameterHolder = parameterHolder;
	}

	@Override
	public ReturnValueHolder call() throws Exception {
		ReturnValueHolder returnValueHolder = parameterHolder.getTemplateEngine().render(parameterHolder, mapping);
		return returnValueHolder;
	}
}
