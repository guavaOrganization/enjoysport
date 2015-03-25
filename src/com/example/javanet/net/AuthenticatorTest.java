package com.example.javanet.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

public class AuthenticatorTest {
	public static void main(String[] args) {
		Authenticator.setDefault(new DefaultAuthenticator());
		try {
			URL url = new URL("http://home.asiainfo.com/AIPRT");
			try (InputStream is = new BufferedInputStream(url.openStream())) {
				Reader r = new InputStreamReader(is);
				int c;
				while ((c = r.read()) != -1) {
					System.out.println((char) c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static class DefaultAuthenticator extends Authenticator {
		// PasswordAuthentication 类是供 Authenticator 使用的数据持有者。它只是用户名和密码的存储库。
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			PasswordAuthentication authentication = new PasswordAuthentication("chenjun12", "mcfly880707#".toCharArray());
			return authentication;
		}
	}
}
