package com.example.beetl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.example.beetl.javabeans.User;

public class CirculationForIn {
	public static void main(String[] args) throws IOException {
		ClasspathResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(loader, configuration);
		Template template = gt.getTemplate("/com/example/beetl/CirculationForIn.txt");
		User user = new User("陈俊", 26);
		User liUser = new User("吴丽", 25);
		List<User> list = new ArrayList<User>();
		list.add(user);
		list.add(liUser);
		template.binding("list",list);
		System.out.println(template.render());
	}
}
