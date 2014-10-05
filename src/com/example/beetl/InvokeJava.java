package com.example.beetl;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.example.beetl.javabeans.User;

public class InvokeJava {
	public static void main(String[] args) throws IOException {
		ClasspathResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		configuration.addPkg("com.example.beetl.javabeans");// 申明模版文件中类的搜索路径
		GroupTemplate gt = new GroupTemplate(loader, configuration);
		Template template = gt.getTemplate("/com/example/beetl/InvokeJava.txt");
		User user = new User("陈俊", 26);
		template.binding("user", user);
		System.out.println(template.render());
	}
}
