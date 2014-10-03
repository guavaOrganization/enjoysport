package com.example.beetl;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

public class HelloBeetl {
	public static void main(String[] args) {
		try {
			/**
			 *  FileResourceLoader：文件模板加载器，需要一个根目录作为参数构造，，传入getTemplate方法的String是模板文件相对于Root目录的相对路径
			    ClasspathResourceLoader：文件模板加载器，模板文件位于Classpath里
				WebAppResourceLoader：用于webapp集成，假定模板跟目录就是WebRoot目录
			 */
			// 字符串模板加载器，用于加载字符串模板
			StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();// 一个是模板资源加载器
			Configuration configuration = Configuration.defaultConfiguration();// 一个是配置类
			GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, configuration);
			Template template = groupTemplate.getTemplate("hello,${name}");
			template.binding("name", "beelt");
			System.out.println(template.render());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
