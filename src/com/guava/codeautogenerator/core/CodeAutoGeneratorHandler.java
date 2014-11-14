package com.guava.codeautogenerator.core;

import com.guava.codeautogenerator.exception.CodeAutoGeneratorException;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * @Description 代码生成器的核心处理接口
 * 
 * <P>代码生成工具依赖于apache的commons-logging包和闲.大赋的开源模版引擎框架Beetl</p>
 * @author 八两俊 
 * @since 1.0
 */
public interface CodeAutoGeneratorHandler {
	/**
	 * 
	 * @since 1.0
	 * @throws
	 */
	public void handleAutoGeneratorCode(ParameterHolder parameterHolder) throws CodeAutoGeneratorException;
}
