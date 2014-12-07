package com.example.beetl.test;

import java.io.IOException;

import org.beetl.core.Context;

public class BeetlCustomUtilFunction {
	public static String wipeOffBlank(String str) {
		if (null == str) {
			return str;
		}
		return str.trim();
	}

	public static void print(String str, Context ctx) throws IOException {
		ctx.byteWriter.writeString("\n" + str);
	}
}
