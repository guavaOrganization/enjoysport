package com.guava.codeautogenerator.core;

import java.util.ArrayList;
import java.util.List;

public class ParameterHolder {
	private List<CodeAutoGeneratorMapping> codeAutoGeneratorMappings = new ArrayList<CodeAutoGeneratorMapping>();
	private String codeModuleName;
	private String codeStyle;
	private TemplateEngine templateEngine;
	private Object customParameter;

	public List<CodeAutoGeneratorMapping> getCodeAutoGeneratorMappings() {
		return codeAutoGeneratorMappings;
	}

	public void setCodeAutoGeneratorMappings( List<CodeAutoGeneratorMapping> codeAutoGeneratorMappings) {
		this.codeAutoGeneratorMappings = codeAutoGeneratorMappings;
	}

	public String getCodeModuleName() {
		return codeModuleName;
	}

	public void setCodeModuleName(String codeModuleName) {
		this.codeModuleName = codeModuleName;
	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public Object getCustomParameter() {
		return customParameter;
	}

	public void setCustomParameter(Object customParameter) {
		this.customParameter = customParameter;
	}

	public String getCodeStyle() {
		return codeStyle;
	}

	public void setCodeStyle(String codeStyle) {
		this.codeStyle = codeStyle;
	}
}
