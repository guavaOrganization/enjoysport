package com.example.btrace.tracingscript;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

@BTrace
public class BTraceArgArray {
	@OnMethod(clazz = "com.example.btrace.targetmethod.TestArgArray", method = "/read.*/")
	public static void anyRead(@ProbeClassName String pcn, @ProbeMethodName String pmn, AnyType[] args) {
		// 追踪clazz下所有以read开头的方法
		print(pcn);
		print(".");
		print(pmn);
		printArray(args);
	}
}
