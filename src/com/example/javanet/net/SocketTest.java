package com.example.javanet.net;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class SocketTest {
	/**
	 * Socket.close()方法同事关闭Socket的输出和输入。有时你可能希望只关闭连接的一般，即输入或者输出。shutdownInput()和shutdownOutput()方法可以只关闭连接一半。
	 * 这并不关闭Socket。实际上，它会调整与Socket连接的流，使它认为已经到了流的末端。
	 * 关闭输入之后再读取输入流会返回-1.关闭输出之后再写入Socket会抛出一个IOException异常。
	 * 注意：即使半关闭了连接，或将连接的两半都关闭，使用结束后仍需要关闭该Socket。shutdown方法只影响Socket的流。它们并不释放与Socket关联的资源，如占用的端口等。
	 */
	
	public static void main(String[] args) {
		// Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 1223)
		/**
		// 构造但不连接，之所以有这个构造函数，是为了支持不同类型的Socket，还需要用它设置一个socket选项，这个选项只能在socket连接之前改变
		Socket connection = new Socket();
		// ... 填入socket选项
		
		// SocketAddress类表示一个连接端点。这是一个空的抽象类，除了一个默认构造函数外没有其他方法。
		// 至少在理论上，SocketAddress类可以用于TCP和非TCP socket。实际上，当前只支持TCP/IP Socket。
		//  SocketAddress类的主要用途是为暂时的socket连接信息提供一个方便的存储，即使最初的socket已断开并被垃圾回收，这些信息也可以重用来创建新的Socket。
		SocketAddress address;
		try {
			address = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 1223);// 通过DNS解析主机
			address = InetSocketAddress.createUnresolved("127.0.0.1", 1223); // 不再在DNS中查找主机
			connection.bind(address);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		System.out.println("*******************代理部分*******************");
		// 一般情况下，Socket使用的代理服务器由socksProxyHost和socksProxyPort系统属性控制，这些属性应用于系统中的所有Socket。
		// 但是这个构造函数创建的socket会使用指定的代理服务器。最值得一提的是，可以为参数传入Proxy.NO_PROXY，完全绕过所有代理服务器，而直接连接远程主机。
		// 当然如果防火墙禁止直接连接，Java将无能为力，连接就会失败
//		Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("代理服务器", 8080));
//		Socket socket = new Socket(proxy);
//		socket.connect(new InetSocketAddress("目标服务器地址", 1223));
		System.out.println("*******************代理部分*******************");
		// 这不只是创建Socket对象。实际上它会在网络上建立。如果连接超时，或由于服务未在端口1223上监听而失败，构造函数会抛出一个IOException异常
		try (Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 1223)) {
			// 启用/禁用带有指定超时值的SO_TIMEOUT，以毫秒为单位。将此选项设为非零的超时值时，在与此 Socket关联的InputStream上调用 read()将只阻塞此时间长度。
			// 如果超过超时值，将引发java.net.SocketTimeoutException，虽然 Socket仍旧有效。选项必须在进入阻塞操作前被启用才能生效。超时值必须是 > 0 的数。超时值为 0 被解释为无穷大超时值。
			// socket.setSoTimeout(15000);// 15s
			System.out.println("**********Socket 相关信息**********");
			System.out.println(socket.getLocalAddress());
			System.out.println(socket.getRemoteSocketAddress());
			System.out.println("**********Socket 相关信息**********");
			
			Reader reader = new InputStreamReader(new BufferedInputStream(socket.getInputStream()), "UTF-8");
			Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			writer.write("小样，现在是几点？");
			writer.write("快点告诉我！\r\n");
			writer.flush();// 刷新输出，从而确保命令会通过网络发送

			System.out.println("读取Socket中的数据....");
			StringBuilder sb = new StringBuilder(80);
			while (true) {
				int c = reader.read();
				if(c == '\r' || c == '\n' || c == -1)
					break;
				sb.append((char)c);
			}
			System.out.println(sb.toString());
			System.out.println("服务器响应我的请求了，好开心咯");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
