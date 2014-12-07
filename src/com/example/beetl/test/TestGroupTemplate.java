package com.example.beetl.test;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

public class TestGroupTemplate {
	public static void main(String[] args) throws IOException {
		Configuration cfg = Configuration.defaultConfiguration();
		ResourceLoader loader = new StringTemplateResourceLoader();
		GroupTemplate gt = new GroupTemplate(loader, cfg);
		Template template = gt.getTemplate("hello,${tip}");
		template.binding("tip", "world");
		System.out.println(template.render());
	}
}
