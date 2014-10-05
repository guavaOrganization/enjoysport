package com.example.beetl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

public class ShareVariable {
	public static void main(String[] args) {
		try {
			ClasspathResourceLoader loader = new ClasspathResourceLoader();
			Configuration configuration = Configuration.defaultConfiguration();
			GroupTemplate gt = new GroupTemplate(loader, configuration);
			Map<String, Object> shareMap = new HashMap<String, Object>();
			shareMap.put("name", "陈俊");
			gt.setSharedVars(shareMap);
			Template template = gt.getTemplate("/com/example/beetl/ShareVariable1.txt");
			System.out.println(template.render());
			template = gt.getTemplate("/com/example/beetl/ShareVariable2.txt");
			System.out.println(template.render());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
