package com.example.beetl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.example.beetl.javabeans.User;

public class ReferenceAttribute {
	public static void main(String[] args) throws IOException {
		ClasspathResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(loader, configuration);
		Template template = gt.getTemplate("/com/example/beetl/ReferenceAttribute.txt");
		User user = new User("陈俊", 26);
		List<User> list = new ArrayList<User>();
		list.add(user);
		template.binding("list", list);
		template.binding("user", user);
		Map<String, String> map = new HashMap<String, String>();
		map.put("hobby", "basketball");
		template.binding("map", map);
		System.out.println(template.render());
	}
}
