package com.example.beetl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.example.beetl.javabeans.User;

public class GlobalVariable {
	public static void main(String[] args) {
		try {
			ClasspathResourceLoader loader = new ClasspathResourceLoader();
			Configuration configuration = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(loader, configuration);
			Template template = gt.getTemplate("/com/example/beetl/GlobalVariable.txt");
			List<User> list = new ArrayList<User>();
			list.add(new User("陈俊", 26));
			list.add(new User("吴丽", 24));
			template.binding("list", list);
			System.out.println(template.render());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
