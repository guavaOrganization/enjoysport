package com.guava.codeautogenerator.core.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.core.ReturnValueHolder;
import com.guava.codeautogenerator.core.exception.CodeAutoGeneratorException;
import com.guava.codeautogenerator.core.mapping.CodeAutoGeneratorMapping;
import com.guava.codeautogenerator.core.support.PropertiesUtils;
import com.guava.codeautogenerator.core.support.StringUtils;
import com.guava.codeautogenerator.core.templateengine.BeetlTemplateEngine;
import com.guava.codeautogenerator.core.templateengine.TemplateEngine;

public abstract class AbstractCodeAutoGeneratorHandler implements CodeAutoGeneratorHandler {
	private transient static Log log = LogFactory.getLog(AbstractCodeAutoGeneratorHandler.class);

	public static final String CODE_STYLE_DEFAULT = "SpringJdbc";
	public static final String DEFAULT_SEPARATOR = ",";
	public static final String DEFAULT_PROPERTIES_PATH = "/com/guava/codeautogenerator/core/CodeAutoGeneratorDefault.properties";
	public static final String KEY_ISCONCURRENCE = "CodeAutoGenerator.IsConcurrence";
	public static final String KEY_CODESTYLE= "CodeAutoGenerator.CodeStyle";
	public static final String KEY_TEMPLATEENGINE = "CodeAutoGenerator.TemplateEngine";
	public static final String KEY_CODEMODULENAME = "CodeAutoGenerator.CodeModuleName";
	public static final String KEY_CODEAUTOGENERATORMAPPINGS = "CodeAutoGenerator.CodeAutoGeneratorMapping";
	public static final String KEY_FILESUFFIX = "CodeAutoGenerator.FileSuffix";
	public static final String KEY_THE_END_FILENAME = "CodeAutoGenerator.TheEndFileName";
	public static final String KEY_THE_END_BUFFERSIZE = "CodeAutoGenerator.BufferSize";
	public static final String PROJECT_ROOT_PATH = System.getProperty("user.dir");
	
	private boolean isConcurrence;
	private String codeStyle = CODE_STYLE_DEFAULT;// 代码风格，如：spring-jdbc、spring-hibernate
	private TemplateEngine templateEngine = new BeetlTemplateEngine(); // 指定模版引擎
	private String codeModuleName = "com.guava.codeautogenerator.testing";// 模块名
	private List<CodeAutoGeneratorMapping> codeAutoGeneratorMappings = new ArrayList<CodeAutoGeneratorMapping>();
	private boolean needCheckParameter = true; // 是否要校验ParameterHolder
	private Properties properties = new Properties();
	private Set<String> ignorePropertieKeys;
	private String fileSuffix = ".java";
	private String theEndFileName = "tempDir.zip";
	private int bufSize = 1024;
	
	private List<InputStream> iss = new ArrayList<InputStream>();

	public AbstractCodeAutoGeneratorHandler() throws IOException {
		loadDefaultProperties();// 初始化默认配置
		parsePropertiesAndDecorateHandler();
	}
	
	private void loadDefaultProperties() throws IOException{
		properties = PropertiesUtils.loadProperties(DEFAULT_PROPERTIES_PATH);// 加载DEFAULT_PROPERTIES_PATH属性文件
		Properties customProperties = loadCustomProperties();// 获取自定义的属性信息
		PropertiesUtils.mergeProperties(properties, customProperties, ignorePropertieKeys);// 合并属性
	}
	
	protected Properties loadCustomProperties() throws IOException{// 加载自定义配置
		return null;
	}
	
	private void parsePropertiesAndDecorateHandler() {
		Set<String> proKeys = properties.stringPropertyNames();
		Iterator<String> proIte =  proKeys.iterator();
		while (proIte.hasNext()) {
			String key = proIte.next();
			if (KEY_ISCONCURRENCE.equals(key)) {
				this.isConcurrence = PropertiesUtils.getProperty(properties, KEY_ISCONCURRENCE, false);
			} else if (KEY_FILESUFFIX.equals(key)) {
				this.fileSuffix = PropertiesUtils.getProperty(properties, KEY_FILESUFFIX, fileSuffix);
			} else if(KEY_THE_END_FILENAME.equals(key)){
				this.theEndFileName = PropertiesUtils.getProperty(properties, KEY_THE_END_FILENAME, theEndFileName);
			} else if (KEY_CODESTYLE.equals(key)) {
				this.codeStyle = PropertiesUtils.getProperty(properties, KEY_CODESTYLE, CODE_STYLE_DEFAULT);
			} else if (KEY_THE_END_BUFFERSIZE.equals(key)) {				
				this.bufSize = PropertiesUtils.getProperty(properties, KEY_THE_END_BUFFERSIZE, bufSize);
			} else if (KEY_TEMPLATEENGINE.equals(key)) {
				String templateEngineStr = PropertiesUtils.getProperty(properties, key, StringUtils.EMPTY);
				try {
					if (!StringUtils.EMPTY.equals(templateEngineStr)) {
						Class cls = Class.forName(templateEngineStr);
						this.templateEngine = (TemplateEngine) cls.newInstance();
					}
				} catch (Exception e) {
					if (log.isErrorEnabled()) {
						log.error("Please Check Your Properties,Key[CodeAutoGenerator.TemplateEngine] Value Need Implements TemplateEngine");
					}
					this.templateEngine = new BeetlTemplateEngine();
				}
			} else if (KEY_CODEMODULENAME.equals(key)) {
				this.codeModuleName = PropertiesUtils.getProperty(properties, KEY_CODEMODULENAME, codeModuleName);
			} else if (KEY_CODEAUTOGENERATORMAPPINGS.equals(key)) {
				List<String> mappings = PropertiesUtils.getProperty(properties, key, null, DEFAULT_SEPARATOR);
				for (String mapping : mappings) {
					try {
						Class cls = Class.forName(mapping);
						this.codeAutoGeneratorMappings.add((CodeAutoGeneratorMapping) cls.newInstance());
					} catch (Exception e) {
						if (log.isErrorEnabled()) {
							log.error("Please Check You Properties,Key[CodeAutoGenerator.CodeAutoGeneratorMapping] Value Need Implements CodeAutoGeneratorMapping");
						}
					}
				}
			}
		}
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
		if(StringUtils.isBlank(parameterHolder.getCodeStyle()))
			parameterHolder.setCodeStyle(this.codeStyle);
		if (null == parameterHolder.getTemplateEngine())
			parameterHolder.setTemplateEngine(this.templateEngine);
		if (StringUtils.isBlank(parameterHolder.getCodeModuleName()))
			parameterHolder.setCodeModuleName(this.codeModuleName);
		if (null == parameterHolder.getCodeAutoGeneratorMappings())
			parameterHolder.setCodeAutoGeneratorMappings(this.codeAutoGeneratorMappings);
		if(StringUtils.isBlank(parameterHolder.getFileSuffix()))
			parameterHolder.setFileSuffix(this.fileSuffix);
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
	
	private void postprocessingAutoGeneratorCode(ParameterHolder parameterHolder) throws CodeAutoGeneratorException {
		for (int i = 0; null != iss && i < iss.size(); i++) {
			try {
				iss.get(i).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		iss.clear();
	}

	protected void handleAutoGeneratorCodeInternal(ParameterHolder parameterHolder) throws CodeAutoGeneratorException{
			try {
				ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(getTheEndFileFullPathAndName()));
				byte[] buf = new byte[bufSize];
				if (parameterHolder.isConcurrence() || this.isConcurrence) {// 使用多线程并发模式生成文件
					
				} else {
					for (CodeAutoGeneratorMapping mapping : parameterHolder.getCodeAutoGeneratorMappings()) {
						ReturnValueHolder returnValueHolder = parameterHolder.getTemplateEngine().render(parameterHolder, mapping);
						InputStream stream = returnValueHolder.getIs();
						zipOutputStream.putNextEntry(new ZipEntry(returnValueHolder.getFileName() + parameterHolder.getFileSuffix()));
						int len;
						while ((len = stream.read(buf)) > 0) {
							zipOutputStream.write(buf, 0, len);
						}
						iss.add(stream);
					}
				}
				zipOutputStream.closeEntry();
				zipOutputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new CodeAutoGeneratorException(e.getMessage(), e.getCause());
			} catch (IOException e) {
				e.printStackTrace();
				throw new CodeAutoGeneratorException(e.getMessage(), e.getCause());
			}
	}
	
	protected String getTheEndFileFullPathAndName(){
		return PROJECT_ROOT_PATH + File.separatorChar + this.theEndFileName;
	}

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

	public Set<String> getIgnorePropertieKeys() {
		return ignorePropertieKeys;
	}

	public void setIgnorePropertieKeys(Set<String> ignorePropertieKeys) {
		this.ignorePropertieKeys = ignorePropertieKeys;
	}

	public boolean isConcurrence() {
		return isConcurrence;
	}

	public void setConcurrence(boolean isConcurrence) {
		this.isConcurrence = isConcurrence;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public String getTheEndFileName() {
		return theEndFileName;
	}
}
