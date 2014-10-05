package com.example.beetl;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;

public class TemplateVariable {
	public static void main(String[] args) {
		try {
			ClasspathResourceLoader loader = new ClasspathResourceLoader();
			Configuration configuration = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(loader, configuration);
			System.out.println(gt.getTemplate("/com/example/beetl/TemplateVariable.txt").render());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
