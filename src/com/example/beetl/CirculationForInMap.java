package com.example.beetl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

public class CirculationForInMap {
	public static void main(String[] args) throws IOException {
		ClasspathResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(loader, configuration);
		Template template = gt.getTemplate("/com/example/beetl/CirculationForInMap.txt");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "陈俊");
		map.put("age", "26");
		template.binding("map", map);
		System.out.println(template.render());
	}
}
