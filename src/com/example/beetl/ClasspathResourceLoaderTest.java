package com.example.beetl;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

public class ClasspathResourceLoaderTest {
	public static void main(String[] args) {
		try {
			ClasspathResourceLoader loader = new ClasspathResourceLoader();
			Configuration configuration = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(loader, configuration);
			Template template = gt.getTemplate("/com/example/beetl/FileResourceLoaderTest.txt");
			template.binding("name", "陈俊");
			template.binding("area", "江西省抚州市");
			System.out.println(template.render());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
