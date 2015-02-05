package com.example.btrace.tracingscript;

import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

@BTrace
public class BTraceRegularExpression {
	@OnMethod(clazz = "/com\\.example\\..+/", method = "test")
	public static void h(@ProbeClassName String pcn, @ProbeMethodName String pmn) { // 正则表达式
		print("h(@ProbeClassName String pcn, @ProbeMethodName String pmn),");
		print(pcn);
		print(" #### ");
		println(pmn);
	}
}
