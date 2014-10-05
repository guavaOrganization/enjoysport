package com.example.beetl;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

public class StringTemplateTest {
	public static void main(String[] args) {
		try {
			StringTemplateResourceLoader loader = new StringTemplateResourceLoader();
			Configuration configuration = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(loader, configuration);
			Template template = gt.getTemplate("${[1,2,3]}");// 输出一个json列表
			System.out.println(template.render());
			template = gt.getTemplate("${ {key : 1,value : 2 \\} } ");// 输出一个json map,}需要加上\
			System.out.println(template.render());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
