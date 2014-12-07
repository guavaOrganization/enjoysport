package com.example.beetl.test;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

public class TestDirectInvokeJava {
	public static void main(String[] args) throws IOException {
		ResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(loader, configuration);
		Template template = gt
				.getTemplate("/com/example/beetl/TestDirectInvokeJava.btl");

		User user = new User(1, "八两俊");
		template.binding("user", user);
		System.out.println(template.render());
	}
}
