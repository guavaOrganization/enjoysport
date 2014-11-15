package com.guava.codeautogenerator.core;

import java.util.List;

import com.guava.codeautogenerator.core.mapping.CodeAutoGeneratorMapping;
import com.guava.codeautogenerator.core.templateengine.TemplateEngine;

public class ParameterHolder {
	private List<CodeAutoGeneratorMapping> codeAutoGeneratorMappings;
	private String codeModuleName;
	private String codeStyle;
	private TemplateEngine templateEngine;
	private boolean isConcurrence;
	private String fileSuffix;
	private String fileModuleName;

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

	public String getCodeStyle() {
		return codeStyle;
	}

	public void setCodeStyle(String codeStyle) {
		this.codeStyle = codeStyle;
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

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getFileModuleName() {
		return fileModuleName;
	}

	public void setFileModuleName(String fileModuleName) {
		this.fileModuleName = fileModuleName;
	}
}
