package com.example.beetl.test;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.Context;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.VirtualAttributeEval;
import org.beetl.core.VirtualClassAttribute;
import org.beetl.core.resource.ClasspathResourceLoader;

public class TestBeetlCustomVirtualAttribute {
	public static void main(String[] args) throws IOException {
		ResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(loader, configuration);
		
		gt.registerVirtualAttributeClass(User.class, new VirtualClassAttribute() {
			@Override
			public Object eval(Object obj, String attributeName, Context ctx) {
				User user = (User) obj;
				if ("desc".equals(attributeName)) {
					if (user.getId() == 1) {
						return "班长";
					} else if (user.getId() == 2) {
						return "学习委员";
					} else {
						return "普通学生";
					}
				}
				return null;
			}
		});
		
		// 为一些类注册需要属性，VirtualAttributeEval.isSupport方法将判断是否应用虚拟属性到此类
		gt.registerVirtualAttributeEval(new VirtualAttributeEval() {
			@Override
			public Object eval(Object obj, String attributeName, Context paramContext) {
				return null;
			}
			@Override
			public boolean isSupport(Class cls, String attributeName) {
				return false;
			}
		});
		User user = new User(1, "八两俊");
		User zhuUser = new User(2,"猪古丽");
		User zengUser = new User(3,"神龟大侠");
		Template template = gt.getTemplate("/com/example/beetl/TestBeetlCustomVirtualAttribute.btl");
		template.binding("user", user);
		template.binding("zhuUser", zhuUser);
		template.binding("zengUser", zengUser);
		System.out.println(template.render());
	}
}
