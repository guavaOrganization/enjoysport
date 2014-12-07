package com.example.beetl.test;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

public class TestHtmlTag {
	public static void main(String[] args) throws IOException {
		ResourceLoader loader = new ClasspathResourceLoader();
		Configuration configuration = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(loader, configuration);
		Template template = gt.getTemplate("/com/example/beetl/TestHtmlTag.btl");
		template.binding("Company", "番石榴");
		// 如果在htmltag目录下找不到同名的模板文件，则beetl再次寻找是否有同名注册的标签函数，如果存在，则调用标签函数
		System.out.println(template.render());
	}
}
