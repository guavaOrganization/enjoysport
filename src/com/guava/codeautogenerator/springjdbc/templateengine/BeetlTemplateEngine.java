package com.guava.codeautogenerator.springjdbc.templateengine;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.guava.codeautogenerator.core.CodeAutoGeneratorMapping;
import com.guava.codeautogenerator.core.ParameterHolder;
import com.guava.codeautogenerator.core.ReturnValueHolder;
import com.guava.codeautogenerator.core.TemplateEngine;
import com.guava.codeautogenerator.exception.CodeAutoGeneratorException;

public class BeetlTemplateEngine implements TemplateEngine {
	public ReturnValueHolder render(ParameterHolder parameterHolder, CodeAutoGeneratorMapping autoGeneratorMapping) throws CodeAutoGeneratorException {
		try {
			ClasspathResourceLoader loader = new ClasspathResourceLoader();
			Configuration configuration = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(loader, configuration);
			Template template = gt.getTemplate(autoGeneratorMapping.getTemplateFilePath());
			template.binding(autoGeneratorMapping.getTemplateParam(parameterHolder));
			String content = template.render();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CodeAutoGeneratorException(e.getMessage(), e.getCause());
		}
	}
}
