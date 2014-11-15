package com.guava.codeautogenerator;

import java.io.IOException;

import org.junit.Test;

import com.guava.codeautogenerator.core.CodeAutoGeneratorHandler;
import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.springjdbc.handler.SpringJdbcAutoGeneratorHandler;

public class CodeAutoGeneratorTest {
	@Test
	public void handleAutoGeneratorCode() throws IOException {
		CodeAutoGeneratorHandler generatorHandler = new SpringJdbcAutoGeneratorHandler();
		ParameterHolder parameterHolder = new ParameterHolder();
		parameterHolder.setCustomParameter(new Object());
		generatorHandler.handleAutoGeneratorCode(parameterHolder);
	}
}
