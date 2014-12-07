package com.example.beetl.test;

import java.io.IOException;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.Template;

public class BeetlCustomFunction implements Function {
	@Override
	public Object call(Object[] paras, Context ctx) {
		// paras 对应模版中传入的参数
		// 模版上下文
		try {
			if (null != paras && paras.length > 0) {
				ctx.byteWriter.writeString(String.valueOf(paras[0]));// byteWriter:输出流
			}
			Template template = ctx.template;// 模版本身
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
