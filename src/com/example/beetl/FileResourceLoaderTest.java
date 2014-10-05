package com.example.beetl;

import java.io.File;
import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;

public class FileResourceLoaderTest {
	public static void main(String[] args) {
		try {
			String root = System.getProperty("user.dir") + File.separator + "config\\com\\example\\beetl";
			FileResourceLoader loader = new FileResourceLoader(root, "UTF-8");
			Configuration configuration;
			configuration = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(loader, configuration);
			Template template = gt.getTemplate("FileResourceLoaderTest.txt");
			template.binding("name", "陈俊");
			template.binding("area", "江西省抚州市");
			System.out.println(template.render());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
