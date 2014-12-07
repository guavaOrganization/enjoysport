package com.example.beetl.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

public class TestShareVariable {
	public static void main(String[] args) throws IOException {
		ResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(loader, configuration);
		
		Map<String, Object> sharedVars = new HashMap<String, Object>();
		sharedVars.put("name", "beetl");
		gt.setSharedVars(sharedVars);
		
		
		Template templateA = gt.getTemplate("/com/example/beetl/TestShareVariable_A.btl");
		templateA.binding("age", "26");
		
		Template templateB = gt.getTemplate("/com/example/beetl/TestShareVariable_B.btl");
		templateB.binding("age", "27");
		System.out.println(templateA.render());
		System.out.println(templateB.render());
	}
}
