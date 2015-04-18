package com.example.javanet.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SecureSocketTest {
	public static void main(String[] args) {
		SSLSocket socket = null;
		try {
			// 现根据系统安全属性"ssl.SocketFactory.provider"获取SocketFactory;如果没有获得,则通过SSLContext#getSocketFactory方法获取SocketFactory
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			// 返回默认情况下启用的密码套件的列表。
			// factory.getDefaultCipherSuites();//见sun/security/ssl/CipherSuite.java
			
			// 此连接上最初的握手可以用以下三种方式开始：
			// 1、调用 startHandshake 显式地开始握手，或者
			// 2、任何在此套接字上读取或写入应用数据的尝试将引起隐式握手，或者
			// 3、调用getSession尝试建立会话（如果当前不存在有效的会话）将进行隐式握手。
			socket = (SSLSocket)factory.createSocket("127.0.0.1", 1223);
			
			// 返回能够被启用以供 SSL 连接使用的协议的名称。可参考sun/security/ssl/ProtocolVersion.java
			String[] supportedProtocols = socket.getSupportedProtocols();
			
			// 在很少的情况下，你可能甚至希望重新认证一个连接(业绩是说，丢弃前面协商好的所有证书和密钥，重新开始一个新的会话)。startHandshake()方法可以做到这一点
			// 在此连接上开始 SSL 握手。这样做通常是因为需要使用新的加密密钥、需要更改密码套件或开始新的会话。若要强制完成重新验证，可以在开始此握手之前使当前会话无效。
			// 如果数据已经在连接上发送，则在此握手期间数据继续流动。当握手完成时，将使用事件来通知。 此方法对于连接上的初始握手是同步的，它在协商的握手完成时返回。一些协议可能不支持在一个已经存在的套接字上的多次握手，并可能抛出 SSLException。
			// socket.startHandshake();
			
			
			
			// 作为使用JSSE的程序员，利用会话时不需要任何而外的工作。如果在很短的一段时间内对一台主机的一个端口打开多个安全Socket，JSSE会自动重用这个会话的密钥。
			// 不过，在高安全性应用程序中，你可能希望禁止Socket之间的会话共享，或强制会话重新认证。
			// 在JSSE中，会话由SSLSession接口的实例表示，可以使用这个接口的方法来检查会话的创建时间和最后访问时间、将会话作废、得到会话的各种有关信息等
			
			// 不过，会话是性能和安全的一个这种。每一个事务都重新协商密钥会更加安全。如果你确实拥有能力超强的硬件，要保护你的系统免受同样坚定、富有、斗志昂扬而且精明能干的对手的攻击
			// 你可能希望避免会话。为避免Socket创建会话，要向setEnableSessionCreation()传入false。
			socket.setEnableSessionCreation(false);
			
			
			// setUseClientMode()方法确定Socket是否需要在第一次握手时使用认证。这个方法名有些误导性。
			// 客户端和服务器端Socket都可以使用这个方法。不过，传入true时，它表示Socket处于客户端模式(无论是否在客户端上)，因此不会提供自行认证。传入false时，则会尝试自行认证：

			// 此方法必须在发生任何握手之前调用。一旦握手开始，在此套接字的生存期内将不能再重置模式。
			// 服务器通常会验证本身，客户机则不要求这么做。
			// socket.setUseClientMode(false); // 这个属性对于任何指定Socket只能设置一次。视图第二次设置会抛出一个IllegalArgumentException异常。
			
			// HandshakeCompletedListener : 任何希望接收有关给定 SSL 连接上 SSL 协议握手结束通知的类实现此接口。
			// 当 SSL 握手结束时，新安全参数将会建立。这些参数始终包括用来保护报文的安全密钥。它们也可能包括和新会话 相关联的参数，例如经过验证的同位体标识和新的 SSL 密码套件。
			socket.addHandshakeCompletedListener(new HandshakeCompletedListener() {// 在握手成功后，SSLSocketImpl方法会启动线程NotifyHandshakeThread执行Listener
				@Override
				public void handshakeCompleted(HandshakeCompletedEvent event) {
				// HandshakeCompletedEvent : 此事件指示在给定的 SSL 连接上 SSL 握手结束。所有关于该握手结果的核心信息都通过 "SSLSession" 对象捕获。
				// 出于方便的考虑，此事件类提供对一些重要会话属性的直接访问。
				// 此事件的源是在其上刚刚结束握手的 SSLSocket。
					System.out.println("SSL握手结束...."); // 在调用flush之后开始握手
					SSLSession session = event.getSession();
					System.out.println(session.getId());
				}
			});
			
			// 密码套件提供"完整性保护"、"验证"和"机密性"
			// 密码套件分为4个部分：协议、密钥交换算法(验证)、加密算法(机密性)和校验和(完整性)
			String[] enabledCipherSuites = socket.getEnabledCipherSuites(); // 启用的 密码套件
			System.out.println(enabledCipherSuites.length);
			// 启用所有密码组
			String[] supported = socket.getSupportedCipherSuites();// 支持的 密码套件
			socket.setEnabledCipherSuites(supported);

			enabledCipherSuites = socket.getEnabledCipherSuites(); // 指出这个Socket允许使用哪些密码组
			System.out.println(enabledCipherSuites.length);
			for (String s : enabledCipherSuites) {
				// 每个名中的算法分为4个部分：协议、密钥交换算法、加密算法和校验和
				System.out.println(s);
			}
			
			// SSL常用于Web服务器，而且有很好的理由。Web连接一般是暂时的，每个页面需要单独的Socket。
			// 例如，在Amazon.com的安全服务器上结账需要加载7个单独的页面，如果要编辑地址或选择礼品包装，加载的页面还会更多。
			// 想象以下，如果每个页面都要多花10秒钟或更多的时间来协商一个安全连接，会出现什么情况，两台主机之间为简历安全通信需要完成握手，由于这个握手过程有很大的开销，SSL允许简历扩展到
			// 多个Socket的会话(session)。相同回话中的不同Socket使用一组相同的公开密钥和私有密钥。
			// 如果与Amazon.com的安全连接需要7个Socket，那么所有7个Socket都简历在同一个会话中，使用相同的密钥。只有回话中的第一个Socket需要承受生成和交换密钥带来的开销。
		
			// 返回此连接使用的 SSL 会话。它可以是持久会话，并且通常对应于某个用户的整个登录会话过程。该会话指定一个会话中所有连接主动使用的特定密码套件，以及会话客户机和服务器的标识。
			// 此方法将发起初始握手（如有必要），然后阻塞，直到握手已经建立。
			// 如果在初始握手期间出现错误，则此方法返回一个无效的会话对象，该对象会报告一个无效的密码套件 "SSL_NULL_WITH_NULL_NULL"。
			SSLSession session = socket.getSession();// 或进行隐式的握手 
			if(null == session)
				System.out.println("未完成连接,暂无会话信息...");
			else 
				System.out.println("SessionId is " + session.getId());
			
			Writer writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			// writer.write("GET http://127.0.0.1/ HTTP/1.1\r\n");
			// writer.write("Host: www.usps.com\r\n");
			writer.write("你好!");
			writer.write("\r\n");
			writer.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			String s;
			int length = Integer.MAX_VALUE;
			while (!(s = br.readLine()).equals("")){
				System.out.println(s);
				if (s.indexOf("Content-Length") >= 0) {
					length = Integer.parseInt(s.split(":")[1].trim());
				}
			}
			System.out.println();
			System.out.println("ContentLength : " + length);
			
			int c ;
			int i = 0;
			while ((c = br.read()) != -1 && i++ < length) { // 如何让read()结束，此处有点问题
				System.out.write(c);
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != socket)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
