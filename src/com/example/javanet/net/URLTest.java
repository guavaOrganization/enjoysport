package com.example.javanet.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 *  <p>
 * 	URI的语法由一个模式和一个模式特定部分组成，模式和模式特定部分用一个冒号分隔
 * 	当前模式包括：
 * 	data：链接中直接包含的Base64编码数据
 * 	file：本地磁盘上的文件
 * 	ftp：FTP服务器
 * 	http：使用超文本传输协议的国际互联网服务器
 * 	mailto：电子邮件地址
 * 	magnet：可以通过对等网络下载的资源
 * 	telnet：与基于Telnet的服务的连接
 * 	urn：统一资源名
 *	</p>
 * 
 *	<p>
 *	URL 是对统一资源定位符的抽象，扩展类java.lang.Object，是一个final类，不能对其派生子类。
 *	它不依赖于继承来配置不同类型URL的实例，而使用了策略设计模式。协议处理器就是策略，URL类构成上下文，通过它来选择不同的策略。
 *	这个对象的字段包括模式(也就是协议)、主机名、端口、路径、查询字符串和片段标识符(也称为ref)，每个字段可以单独设置。
 *  URL是不可变的。构造一个URL对象后，其字段不再改变。还有一个副作用：可以保证它们是"线程安全"的
 *	</p>
 * @author 八两俊
 * @since
 */
public class URLTest {
	// @Test
	public void testURL() {
		InputStream is = null;
		try {
			URL url = new URL("http://www.baidu.com");
			// openStream()方法连接到URL所引用的资源，在客户端和服务器之间完成必要的握手，返回一个InputStream，可以由此读取数据。
			// 从这个InputStream获得的数据是URL引用的原始内容（即未经解释的内容）
			is = url.openStream();
			int c;
			while ((c = is.read()) != -1)
				System.out.println(c);
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try { // Java 7之前的关闭模式
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

//	 @Test
	public void testURL_InputStreamClose() {
		try {
			URL url = new URL("http://www.baidu.com");
			// 打开到此URL的连接并返回一个用于从该连接读入的InputStream(返回从URL连接读入的输入流)。
			// openStream()方法连接到URL所引用的资源，在客户端和服务器之间完成必要的握手，返回一个InputStream，可以由此读取数据。
			try (BufferedReader is = new BufferedReader(new InputStreamReader(url.openStream()))) { // Java 7及以上版本关闭模式，因为InputStream实现了{#Closeable}接口
				String line = null;
				while ((line = is.readLine()) != null)
					System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void testReadAPage() {
		InputStream is = null;
		try {
			URL url = new URL("http://www.baidu.com");
			is = new BufferedInputStream(url.openStream());// 过滤器,换从输入以提高性能
			Reader r = new InputStreamReader(is);// 将一个InputStream串联到一个Reader上
			int c;
			while ((c = r.read()) != -1)
				System.out.print((char) c);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	// @Test
	/**
	 * URL.openConnection()
	 * 返回一个 URLConnection对象，它表示到 URL所引用的远程对象的连接。 每次调用此URL的协议处理程序的openConnection方法都打开一个新的连接。
	 * 如果URL的协议（例如，HTTP或 JAR）存在属于以下包或其子包之一的公共、专用 URLConnection
	 * 子类：java.lang、java.io、java.util、java.net，返回的连接将为该子类的类型。
	 * 例如，对于 HTTP，将返回HttpURLConnection，对于 JAR，将返回 JarURLConnection
	 */
	public void testMethod_openConnection() {
		try {
			URL url = new URL("http://www.baidu.com/");
			try {
				//  openConnection()方法为指定的URL打开一个socket，并返回一个URLConnection对象。
				// URLConnection表示一个网络资源的打开的连接。如果调用失败，则openConnection()会抛出一个IOException异常。
				URLConnection connection = url.openConnection();
				try (InputStream is = new BufferedInputStream(connection.getInputStream())) {
					Reader r = new InputStreamReader(is);// 将一个InputStream串联到一个Reader上
					int c;
					while ((c = r.read()) != -1)
						System.out.print((char) c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testMethod_getContent(){
		try {
//			URL url = new URL("http://www.baidu.com/");
			URL url = new URL("http://img.sootuu.com/vector/2006-4/2006420114533659.jpg");
			// getContent()方法是下载URL引用数据的第三种方法。getContent()方法获取由URL引用的数据，尝试由
			// 它简历某种类型的对象。如果URL指示某种文本(如ASCII或HTML文件)，返回的对象通常是某种InputStream。
			// 如果URL指示一个图像(如GIF或JPEG文件)，getContent()通常返回一个java.awt.ImageProducer
			Object object = url.getContent();// getContent()方法的最大问题是：很难预测将获得哪种对象。
			System.out.println(object.getClass());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testUrlSomeMethod() {
		try {
			URL url = new URL("http://www.baidu.com");
			// getProtocol()方法返回一个String，其中包含URL的模式(如http、https或file)
			System.out.println(url.getProtocol());
			// getHost()方法返回一个String,其中包含URL的主机名
			System.out.println(url.getHost());
			
			// getPort()方法将URL中指定的端口号作为一个int返回。如果URL中没有指定端口，getPort()返回-1，表示这个URL没有显式
			// 指定端口，将使用该协议的默认端口。
			System.out.println(url.getPort());
			// URL中没有指定端口时，getDefaultPort()方法返回这个URL的协议所使用的默认端口。
			// 如果没有为这个协议定义默认端口，getDefaultPort()将返回-1.
			System.out.println(url.getDefaultPort());
			
			// getFile()方法返回一个String，其中包含URL的路径部分；
			// 要记住，Java不会把URL分解为单独的路径和文件部分。从主机名后的第一个斜线(/)一直到开始片段标识符的#号之前的字符，
			// 都被认为是文件部分。
			System.out.println(url.getFile());
			
			// getPath()方法几乎是getFile()的同义词。也就是说，它返回一个String，其中包含URL的路径和文件部分。
			// 但是与getFile()不同，它返回的String中不包含查询字符串，只有路径。
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_equals() {
		// URL类包含通常的equals和hashCode()方法。
		// 仅当两个URL都指向相同主机、端口和路径上的相同资源，而且有相同的片段标识符和查询字段，才认为这两个URL相等。
		// 实际上equals()方法会尝试用DNS解析主机，来判断两个主机是否相同、这说明此方法可能是一个阻塞的I/O操作！
		// 出于这个原因，应当避免将URL存储在依赖equals()，如java.util.HashMap。更好的选择是java.net.URI。
	}
}
