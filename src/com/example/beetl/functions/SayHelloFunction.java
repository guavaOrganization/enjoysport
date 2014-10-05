package com.example.beetl.functions;

import java.io.IOException;

import org.beetl.core.Context;
import org.beetl.core.Function;

public class SayHelloFunction implements Function {
	@Override
	public String call(Object[] paramArrayOfObject, Context paramContext) {
		if (null == paramArrayOfObject || paramArrayOfObject.length <= 0 || 1 != paramArrayOfObject.length || null == paramArrayOfObject[0]) {
			throw new RuntimeException("传入的参数不符合要求");
		}
		try {
			paramContext.byteWriter.writeString(paramArrayOfObject[0].toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
