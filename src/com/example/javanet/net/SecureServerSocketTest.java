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
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
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
			SSLContext context = SSLContext.getInstance(ALGORITHM);
			// 参考实现只支持X.509密钥
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			// Oracle的默认密钥库类型
			KeyStore ks = KeyStore.getInstance("JKS");
			
			// 处于安全考虑，每个密钥库都必须用口令短语加密，在从磁盘加载前必须提供这个扣了。
			// 口令短语以char[]数组形式存储，所以可以很快地从内存中擦除，而不是等待垃圾回收
			char[] password = "coolguava0792".toCharArray();
			ks.load(new FileInputStream("src/com/example/javanet/net/jnp4e.key"), password);
			kmf.init(ks, password);
			context.init(kmf.getKeyManagers(), null, null);
			// 擦除口令
			Arrays.fill(password, '0');
			
			// SSLServerSocketFactory.getDefault()返回的工厂一般只支持服务器认证。它不支持加密。为了同时支持加密，服务器端安全socket需要更多的初始化和设置。
			// 具体如何设置与实现有关。在Sun的参考实例中，要由一个com.sun.net.ssl.SSLContext对象负责创建已经充分配置和初始化的安全服务器Socket。
			SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(1223);
			
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
				try(SSLSocket socket = (SSLSocket) serverSocket.accept()){
					InputStream is = socket.getInputStream();
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
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}
}
