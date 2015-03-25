package com.example.javanet.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLEncoderTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode("陈俊", "UTF-8"));
		System.out.println(URLEncoder.encode("this*string*has*as", "UTF-8"));
		// this+string+has+as
		System.out.println(URLEncoder.encode("this string has as", "UTF-8"));
		// this%25string%25has%25as
		System.out.println(URLEncoder.encode("this%string%has%as", "UTF-8"));
	}
}
