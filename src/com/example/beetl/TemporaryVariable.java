package com.example.beetl;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

public class TemporaryVariable {
	public static void main(String[] args) {
		try {
			ClasspathResourceLoader loader = new ClasspathResourceLoader();
			Configuration configuration = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(loader, configuration);
			Template template = gt.getTemplate("/com/example/beetl/TemporaryVariable.txt");
			System.out.println(template.render());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
