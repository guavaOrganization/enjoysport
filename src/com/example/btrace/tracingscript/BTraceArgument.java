package com.example.btrace.tracingscript;

import static com.sun.btrace.BTraceUtils.classOf;
import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.strcat;

import com.sun.btrace.BTraceUtils.Reflective;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Self;

@BTrace
public class BTraceArgument {
	@OnMethod(clazz = "/.*/", method = "test")
	public static void a(@Self Object obj, @ProbeClassName String pcn, @ProbeMethodName String pmn) {
		print("method test(..) was traced...");
		print(strcat("@Self Object's Class Name Is ", Reflective.name(classOf(obj))));
		print("...");
		print(strcat("@ProbeClassName is ", pcn));// @ProbeClassName (since 1.1)，用来指定被trace的类名
		print("...");
		println(strcat("@ProbeMethodName is ", pmn));// @ProbeMethodName用来指定被trace的方法名
	}
}
