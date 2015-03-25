package com.example.javaio;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DirList {
	public static void main(String[] args) {
		/**
		 * File类：文件和目录路径名的抽象表示形式。
		 */
		File path = new File(".");
		String[] list = null;
		if (args.length == 0)
			list = path.list();// 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中的文件和目录。
		else
			// list(FilenameFilter)方法是一个"策略模式"的例子，list()实现了基本的功能，而且按照FilenameFilter的形式提供了这个策略，以便完善list()在提供服务时
			// 所需的算法。
			list = path.list(new DirFilter(args[0])); // 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录。

		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (String dirItem : list)
			System.out.println(dirItem);
	}

	/**
	 * FilenameFilter接口的类实例可用于过滤器文件名。Abstract Window Toolkit 的文件对话框组件使用这些实例过滤 File 类的list方法中的目录清单。
	 * @Copyright (c) 2011-2014 guava(番石榴工作室)
	 * <p></p>
	 * 
	 * @author 八两俊 
	 * @since
	 */
	static class DirFilter implements FilenameFilter {
		private Pattern pattern;

		public DirFilter(String regex) {
			pattern = Pattern.compile(regex);
		}

		@Override
		public boolean accept(File dir, String name) {// 测试指定文件是否应该包含在某一文件列表中。
			return pattern.matcher(name).matches();
		}
	}
}
