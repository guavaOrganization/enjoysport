package com.example.javaio;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 文件和目录路径名的抽象表示形式。
 * 
 * 用户界面和操作系统使用与系统相关的路径名字符串来命名文件和目录。此类呈现分层路径名的一个抽象的、与系统无关的视图。抽象路径名有两个组件：
 * 1、一个可选的与系统有关的前缀 字符串，比如盘符，"/" 表示 UNIX中的根目录，"\\\\" 表示 Microsoft Windows UNC 路径名。
 * 2、零个或更多字符串名称的序列。
 * 
 * 路径名字符串与抽象路径名之间的转换与系统有关。将抽象路径名转换为路径名字符串时，每个名称与下一个名称之间用一个默认分隔符 隔开。
 * 默认名称分隔符由系统属性 file.separator定义，可通过此类的公共静态字段 separator和 separatorChar使其可用。
 * 将路径名字符串转换为抽象路径名时，可以使用默认名称分隔符或者底层系统支持的任何其他名称分隔符来分隔其中的名称。
 * 
 * 无论是抽象路径名还是路径名字符串，都可以是绝对 路径名或相对 路径名。绝对路径名是完整的路径名，不需要任何其他信息就可以定位它所表示的文件。
 * 相反，相对路径名必须使用取自其他路径名的信息进行解释。
 * 默认情况下，【java.io包中的类总是根据当前用户目录来解析相对路径名。此目录由系统属性 user.dir指定，通常是 Java虚拟机的调用目录】。
 * 
 * File 类的实例是不可变的；也就是说，一旦创建，File对象表示的抽象路径名将永不改变。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class MyFile {
	
//	@Test
	public void fieldDesc() {
		// 与系统有关的路径分隔符，为了方便，它被表示为一个字符串。
		System.out.println(File.pathSeparator);
		// 与系统有关的路径分隔符。
		System.out.println(File.pathSeparatorChar);
		
		// 与系统有关的默认名称分隔符，为了方便，它被表示为一个字符串。
		System.out.println(File.separator);
		// 与系统有关的默认名称分隔符。
		System.out.println(File.separatorChar);
	}
	
//	@Test
	public void constructor() throws URISyntaxException {
		// 通过将给定路径名字符串转换为抽象路径名来创建一个新File实例。如果给定字符串是空字符串，那么结果是空抽象路径名。
		File ddir = new File("D:\\svnpt\\bin");
		System.out.println(ddir.exists());
		System.out.println(ddir.toURI());
		File childDir = new File(ddir,"mcfly");
		System.out.println(childDir.exists());
		
		File uriFile = new File(new URI("file:/D:/svnpt/bin/"));
		System.out.println(uriFile.exists());
	}
	
//	@Test
	public void test_someMethods() {
		File ddir = new File("D:\\svnpt\\bin\\");
		// 返回由此抽象路径名表示的文件或目录的名称。该名称是路径名名称序列中的最后一个名称。如果路径名名称序列为空，则返回空字符串
		System.out.println(ddir.getName());
		
		// 返回此抽象路径名父目录的路径名字符串；如果此路径名没有指定父目录，则返回 null。
		String parent = ddir.getParent();
		System.out.println(parent);
		// 返回此抽象路径名父目录的抽象路径名；如果此路径名没有指定父目录，则返回 null。
		File parentFile = ddir.getParentFile();
		System.out.println(parentFile.exists());
		
		// 将此抽象路径名转换为一个路径名字符串。所得字符串使用默认名称分隔符分隔名称序列中的名称。
		System.out.println(ddir.getPath());
		
		// 测试此抽象路径名是否为绝对路径名。绝对路径名的定义与系统有关。在 UNIX 系统上，如果路径名的前缀是 "/"，那么该路径名是绝对路径名。
		//在 Microsoft Windows 系统上，如果路径名的前缀是后跟 "\\" 的盘符，或者是 "\\\\"，那么该路径名是绝对路径名。
		System.out.println(ddir.isAbsolute());
	}
	
//	@Test
	public void create() {
		File ddir = new File("D:\\test\\mcfly");
		if (!ddir.exists()) {
			ddir.mkdir();
		}
		// 测试此抽象路径名表示的文件是否是一个目录。
		System.out.println(ddir.isDirectory());
		// 测试此抽象路径名表示的文件是否是一个标准文件。如果该文件不是一个目录，并且满足其他与系统有关的标准，那么该文件是标准 文件。
		// 由 Java 应用程序创建的所有非目录文件一定是标准文件。
		System.out.println(ddir.isFile());
		System.out.println("-------------------------------");
		System.out.println("-------------------------------");
		File dFile = new File("D:\\test\\mcfly\\HelloIO.java");
		System.out.println(dFile.exists());
		try {
			// 当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件。检查文件是否存在，若不存在则创建该文件，这是单个操作，对于其他所有可能影响该文件的文件系统活动来说，该操作是不可分的。
			// 注：此方法不应该 用于文件锁定，因为所得协议可能无法可靠地工作。应该使用 FileLock 机制替代。
			dFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(dFile.exists());
	}
	
	@Test
	public void list() {
		File file = new File("D:\\test\\mcfly");
		// 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中的文件和目录。
		String[] fileNames = file.list();
		for (String fileName : fileNames)
			System.out.println(fileName);
		System.out.println("------------------------");
		// 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录。除了返回数组中的字符串必须满足过滤器外，此方法的行为与
		// list() 方法相同。如果给定 filter 为 null，则接受所有名称
		String[] filterFileNames = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {// dir为被找到的文件所在的目录。
				return null == name ? false : name.startsWith("H") || name.endsWith("t");
			}
		});
		for (String fileName : filterFileNames) {
			System.out.println(fileName);
		}
		
		System.out.println("------------++++++++++------------");
		// 返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
		File[] files = file.listFiles();
		for (File tempFile : files) {
			System.out.println(tempFile.getName());
		}
		
		file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File arg0) {
				return false;
			}
		});
	}
}
