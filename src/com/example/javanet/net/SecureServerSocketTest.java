package com.example.javanet.net;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class SecureServerSocketTest {
	public final static int PORT = 1223;
	public final static String ALGORITHM = "SSL";
	public static void main(String[] args) {
		// SSLServerSocketFactory.getDefault()返回的工厂一般只支持服务器认证。它不支持加密。为了同时支持加密，服务器端安全socket需要更多的初始化和设置。
		// 具体如何设置与实现有关。在Sun的参考实例中，要由一个com.sun.net.ssl.SSLContext对象负责创建已经充分配置和初始化的安全服务器Socket。

		// 具体细节随着JSSE实现的不同而有所差别，但是要在参考实现中创建一个安全服务器Socket，必须完成以下步骤：
		// 1、使用keytool生成公开密钥和证书
		// 2、还钱请可信任的第三方(如Comodo)认证你的证书
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
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
