package com.guava.codeautogenerator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.guava.codeautogenerator.exception.CodeAutoGeneratorException;
import com.guava.codeautogenerator.springjdbc.templateengine.BeetlTemplateEngine;

public abstract class AbstractCodeAutoGeneratorHandler implements CodeAutoGeneratorHandler {
	private transient static Log log = LogFactory.getLog(AbstractCodeAutoGeneratorHandler.class);

	public static final String CODE_STYLE_DEFAULT = "SpringJdbc";
	private String codeStyle = CODE_STYLE_DEFAULT;// 代码风格，如：spring-jdbc、spring-hibernate
	private TemplateEngine templateEngine = new BeetlTemplateEngine(); // 指定模版引擎
	private String codeModuleName = "com.guava.codeautogenerator.testing";// 模块名
	private List<CodeAutoGeneratorMapping> codeAutoGeneratorMappings = new ArrayList<CodeAutoGeneratorMapping>();
	private boolean needCheckParameter = true; // 是否要校验ParameterHolder
	private static final String DEFAULT_PROPERTIES_PATH = "CodeAutoGeneratorDefault.properties";
	private Properties properties;

	public AbstractCodeAutoGeneratorHandler() {
		loadDefaultProperties();// 初始化默认配置
		parsePropertiesAndDecorateHandler();
	}
	
	private void loadDefaultProperties(){
		if (log.isInfoEnabled())
			log.debug("loadDefaultProperties>>>>start");
		// 加载DEFAULT_PROPERTIES_PATH属性文件
		if (log.isInfoEnabled())
			log.debug("loadDefaultProperties>>>>end");
		
		if (log.isInfoEnabled())
			log.debug("loadCustomProperties>>>>start");
		Properties customProperties = loadCustomProperties();// 获取自定义的属性信息
		if (log.isInfoEnabled())
			log.debug("loadCustomProperties>>>>end");
	}
	
	protected Properties loadCustomProperties() {// 加载自定义配置
		return null;
	}
	
	private void parsePropertiesAndDecorateHandler() {
		// 解析properties，装饰Handler相关属性
	}
	
	@Override
	public final void handleAutoGeneratorCode(ParameterHolder parameterHolder) throws CodeAutoGeneratorException{
		long startTime = System.currentTimeMillis();
		if(log.isInfoEnabled())
			log.info("Welcome Use Code Auto Generator Tool...");
		preprocessingAutoGeneratorCode(parameterHolder); // TODO Step1 : 前置处理
		handleAutoGeneratorCodeInternal(parameterHolder);// TODO Step2 : 自动生成代码逻辑
		postprocessingAutoGeneratorCode(parameterHolder); // TODO Step3 : 后置处理
		if(log.isInfoEnabled())
			log.info("See You Next Time...");
		if(log.isInfoEnabled())
			log.info("The time-consuming is >>>> " + (System.currentTimeMillis() - startTime));
	}
	
	private void preprocessingAutoGeneratorCode(ParameterHolder parameterHolder) throws CodeAutoGeneratorException{// 前置处理
		preprocessingAutoGeneratorCodeInternal(parameterHolder);
		initParameterHolder(parameterHolder);// 初始化ParameterHolder
		checkParameterHolder(parameterHolder);
	}
	
	private void initParameterHolder(ParameterHolder parameterHolder) {
		if (null == parameterHolder.getTemplateEngine() || !(parameterHolder.getTemplateEngine() instanceof TemplateEngine)) {
			parameterHolder.setTemplateEngine(this.templateEngine);
		}
		if(null == parameterHolder.getCodeStyle())
			parameterHolder.setCodeStyle(this.codeStyle);
		if (null == parameterHolder.getCodeModuleName())
			parameterHolder.setCodeModuleName(this.codeModuleName);
		parameterHolder.setCodeAutoGeneratorMappings(this.codeAutoGeneratorMappings);
	}
	
	private void checkParameterHolder(ParameterHolder parameterHolder) throws CodeAutoGeneratorException {
		if (null == parameterHolder)
			throw new CodeAutoGeneratorException("ParameterHolder NOT NULL >>>> Please Check In Parameter");
		if (needCheckParameter)
			checkParameterHolderInternal(parameterHolder);
	}
	
	protected void checkParameterHolderInternal(ParameterHolder parameterHolder) throws CodeAutoGeneratorException {
		// 目前空实现，如果子类有校验方法则调用校验
	}
	
	protected void postprocessingAutoGeneratorCode(ParameterHolder parameterHolder) throws CodeAutoGeneratorException {
	}

	protected abstract void handleAutoGeneratorCodeInternal(ParameterHolder parameterHolder) throws CodeAutoGeneratorException;

	protected void preprocessingAutoGeneratorCodeInternal(ParameterHolder parameterHolder) throws CodeAutoGeneratorException {
	}
	
	public String getCodeStyle() {
		return codeStyle;
	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}
	
	public boolean isNeedCheckParameter() {
		return needCheckParameter;
	}

	public void setNeedCheckParameter(boolean needCheckParameter) {
		this.needCheckParameter = needCheckParameter;
	}

	public String getCodeModuleName() {
		return codeModuleName;
	}

	public Properties getProperties() {
		return properties;
	}

	public List<CodeAutoGeneratorMapping> getCodeAutoGeneratorMappings() {
		return codeAutoGeneratorMappings;
	}
}
