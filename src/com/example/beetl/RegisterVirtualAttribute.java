package com.example.beetl;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.Context;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.VirtualClassAttribute;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.example.beetl.javabeans.User;

public class RegisterVirtualAttribute {
	public static void main(String[] args) throws IOException {
		ClasspathResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(loader, configuration);
		gt.registerVirtualAttributeClass(User.class, new VirtualClassAttribute() {
			@Override
			public Object eval(Object obj, String attributeName, Context ctx) {
				User user = (User) obj;
				if (attributeName.equals("ageDesc") && user.getAge() < 20) {
					return "young";
				}
				return "old";
			}
		});
		User user = new User("陈俊", 12);
		Template template = gt.getTemplate("/com/example/beetl/RegisterVirtualAttribute.btl");
		template.binding("user", user);
		System.out.println(template.render());
	}
}
