package com.example.beetl.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

public class TestCirculationStatement {
	public static void main(String[] args) throws IOException {
		ResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(loader, configuration);
		Template template = gt.getTemplate("/com/example/beetl/TestCirculationStatement.btl");
		
		User user = new User(1, "八两俊");
		template.binding("user", user);
		
		List<User> list = new ArrayList<User>();
		list.add(user);
		User womanUser = new User(2, "猪古丽");
		list.add(womanUser);
		template.binding("list", list);
		
		Map<String, String> companyName = new HashMap<String, String>();
		companyName.put("Asiainfo", "亚信科技");
		companyName.put("Tencent", "腾讯科技");
		template.binding("companyName", companyName);
		
		System.out.println(template.render());
	}
}
