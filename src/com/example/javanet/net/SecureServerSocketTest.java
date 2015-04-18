package com.example.javanet.net;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public class SecureServerSocketTest {
	public final static int PORT = 1223;
	public final static String ALGORITHM = "SSL";
	
	public static void main(String[] args) {

		// 具体细节随着JSSE实现的不同而有所差别，但是要在参考实现中创建一个安全服务器Socket，必须完成以下步骤：
		// 1、使用keytool生成公开密钥和证书
		// 2、花钱请可信任的第三方(如Comodo)认证你的证书
		
		// 3、为你使用的算法创建一个SSLContext
		// 4、为你要使用的证书源创建一个TrustManagerFactory
		// 5、为你要使用的密钥类型创建一个KeyManagerFactory
		// 6、为密钥和证书数据库创建一个KeyStore对象(Oracle的默认值是JKS)
		// 7、用密钥和证书填充KeyStore对象。例如，使用加密所用的口令短语从文件系统加载
		// 8、用KeyStore及其口令短语初始化KeyManagerFactory
		// 9、用KeyManagerFactory中的密钥管理器(必要)、TrustManagerFactory中的新人管理器和一个随机源来初始化上下文。

		try {
			// SSLContext：此类的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine的工厂。用可选的一组密钥和信任管理器及安全随机字节源初始化此类
			// 内部采用委派模式，见#SSLContextSpi
			// 所请求协议的标准名称：
			//SSL		Supports some version of SSL; may support other versions
			//SSLv2		Supports SSL version 2 or higher; may support other versions
			//SSLv3		Supports SSL version 3; may support other versions
			//TLS		Supports some version of TLS; may support other versions
			//TLSv1		Supports RFC 2246: TLS version 1.0 ; may support other versions
			//TLSv1.1	Supports RFC 4346: TLS version 1.1 ; may support other versions
			SSLContext context = SSLContext.getInstance(ALGORITHM);
			
			// 参考实现只支持X.509证书
			// 此类充当基于密钥内容源的密钥管理器的工厂。每个密钥管理器管理特定类型的、由安全套接字所使用的密钥内容。密钥内容是基于 KeyStore 和/或提供者特定的源。
			// 内部采用委派模式，见#KeyManagerFactorySpi
			// 算法有：PKIX, SunX509
			// 可以通过"ssl.KeyManagerFactory.algorithm"属性来改变算法
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");//X509证书
			// 默认的算法可以在运行时改变，方法是通过把 "ssl.KeyManagerFactory.algorithm"
			// 安全属性(在Java安全属性文件或通过调用 Security.setProperty(java.lang.String, java.lang.String)来设置)的值设置为所需的算法名称
			// System.out.println(kmf.getDefaultAlgorithm());
			
			// KeyStore表示密钥和证书的存储设施。
			// jceks	The proprietary keystore implementation provided by the SunJCE provider.
			// jks		The proprietary keystore implementation provided by the SUN provider.
			// pkcs12	The transfer syntax for personal identity information as defined in PKCS #12.
			// Java 安全属性文件位于名为 <JAVA_HOME>/lib/security/java.security 的文件中。<JAVA_HOME>引用java.home系统属性的值，并指定安装JRE的目录。
			KeyStore ks = KeyStore.getInstance("JKS");// Oracle的默认密钥库类型
			// 处于安全考虑，每个密钥库都必须用口令短语加密，在从磁盘加载前必须提供这个扣了。
			// 口令短语以char[]数组形式存储，所以可以很快地从内存中擦除，而不是等待垃圾回收
			char[] password = "coolguava0792".toCharArray();
			FileInputStream fis =null;
			try {
				fis = new FileInputStream("src/com/example/javanet/net/jnp4e.key");
				// 从给定输入流中加载此 KeyStore。
				// 可以给定一个密码来解锁 keystore（例如，驻留在硬件标记设备上的 keystore）或检验 keystore数据的完整性。如果没有指定用于完整性检验的密码，则不会执行完整性检验。
				// 如果要创建空 keystore，或者不能从流中初始化 keystore，则传递 null 作为 fis 的参数。
				ks.load(fis, password);
			} catch (CertificateException e) {
				if (null != fis)
					fis.close();
			}
			
			// 方式一：
			// 使用密钥内容源初始化此工厂。
			// 提供者通常使用KeyStore来获取在安全套接字协商期间所使用的密钥内容。KeyStore通常是受密码保护的。
			kmf.init(ks, password);
			// 方式二：
			// kmf.init(new KeyStoreBuilderParameters(paramBuilder));
			
			// 初始化此上下文。前两个参数都可以为 null，在这种情况下将搜索装入的安全提供者来寻找适当工厂的最高优先级实现。
			// 同样，安全的 random 参数也可以为 null，在这种情况下将使用默认的实现。
			context.init(kmf.getKeyManagers(), null, null);
			// 擦除口令
			Arrays.fill(password, '0');
			
			// SSLServerSocketFactory.getDefault()返回的工厂一般只支持服务器认证。它不支持加密。为了同时支持加密，服务器端安全socket需要更多的初始化和设置。
			// 具体如何设置与实现有关。在Sun的参考实例中，要由一个com.sun.net.ssl.SSLContext对象负责创建已经充分配置和初始化的安全服务器Socket。
			// SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			SSLServerSocketFactory factory = context.getServerSocketFactory();
			SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(PORT);
			
			// 增加匿名(未认证密码组)
			String[] supported = serverSocket.getEnabledCipherSuites();
			String[] annoCipherSuitesSupported = new String[supported.length];
			int numAnonCipherSuitesSuported = 0;
			for (int i = 0; i < supported.length; i++) {
				// 不需要认证的密码组，默认情况下没有启用这些密码组，因为它们很容易收到"中间人"攻击，但至少这些密码组允许你编写简单的程序而不需要付钱
				if(supported[i].indexOf("_anon_") > 0){ 
					annoCipherSuitesSupported[numAnonCipherSuitesSuported++] = supported[i];
				}
			}
			String[] oldEnabled = serverSocket.getEnabledCipherSuites();
			String[] newEnabled = new String[oldEnabled.length + numAnonCipherSuitesSuported];
			System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
			System.arraycopy(annoCipherSuitesSupported, 0, newEnabled, oldEnabled.length, numAnonCipherSuitesSuported);
			serverSocket.setEnabledCipherSuites(newEnabled);
			
			// 会话管理
			// 客户端和服务器必须都同一建立一个会话。服务器端使用setEnableSessionCreation()方法指定是否哦允许建立会话，
			// 另外使用getEnableSessionCreation()方法确定当前是否允许建立会话
			// 默认情况下是允许创建会话的。如果服务器禁止创建会话，需要会话的客户端仍能够连接。只不过它不会得到一个会话，而必须为每一个Socket再次完成握手。
			// 类似地，如果客户端拒绝会话而服务器允许，它们仍能在没有会话的情况下相互对话。
			serverSocket.setEnableSessionCreation(true);

			// 服务器端的安全Socket(即SSLServerSocket的accept()方法返回的Socket)可以使用setNeedClientAuth()方法，要求与它连接的所有客户端都要自行认证(或不认证)
			// 通过向setNeedClientAuth方法传递true，可以指定只有客户端能够认证自己的连接才会被接受。
			// 如果穿false，则指定不需要客户端进行认证。默认值为false。如果出于某种原因需要了解这个属性的当前状态，getNeedClientAuth()方法可以告诉你
			// serverSocket.setNeedClientAuth(true);//如果Socket不在服务器端，这个方法会抛出一个IllegalArgumentException异常。
			
			// 现在所有初始化工作已经完成，可以集中进行实际通信了
			while (true) {
				try (SSLSocket socket = (SSLSocket) serverSocket.accept()) {
					InputStream is = socket.getInputStream();
					// 在SSLSession中绑定值，SessionBinderValue实现了#SSLSessionBindingListener接口
					SSLSession session = socket.getSession();
					session.putValue("SessionBinderValue", new SessionBinderValue());
					
					// 封装 SSL/TLS连接的参数。参数是SSL/TLS握手中接受的密码套件列表、允许的协议列表，以及 SSL/TLS服务器是否应该请求或要求客户机验证。
					SSLParameters sslParameters = socket.getSSLParameters();
					System.out.println(sslParameters.getNeedClientAuth());
					
					int c;
					while ((c = is.read()) != -1) {
						System.out.write(c);
					}
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}
}
