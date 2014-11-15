package com.guava.codeautogenerator.test;

import java.io.IOException;

import org.junit.Test;

import com.guava.codeautogenerator.core.handler.CodeAutoGeneratorHandler;
import com.guava.codeautogenerator.springjdbc.SpringJdbcParameterHolder;
import com.guava.codeautogenerator.springjdbc.handler.SpringJdbcAutoGeneratorHandler;

public class CodeAutoGeneratorTest {
	@Test
	public void handleAutoGeneratorCode() throws IOException {
		CodeAutoGeneratorHandler generatorHandler = new SpringJdbcAutoGeneratorHandler();
		SpringJdbcParameterHolder parameterHolder = new SpringJdbcParameterHolder();
		parameterHolder.setTableName("t_guava_user");
		parameterHolder.setBaseBeanName("GuavaUser");
		generatorHandler.handleAutoGeneratorCode(parameterHolder);
	}
}
