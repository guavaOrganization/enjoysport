package com.example.btrace.tracingscript;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.BTraceUtils.Reflective;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Self;

@BTrace
public class BTraceLocationReturn {
	@OnMethod(clazz = "com.example.btrace.targetmethod.TestLocationReturn", method = "testReturn", location = @Location(Kind.RETURN))
	public static void a(@Self Object obj, @ProbeClassName String pcn, @ProbeMethodName String pmn, @Return int result) {
		print(strcat("@Self Object's Class Name Is ", Reflective.name(classOf(obj))));
		print("...");
		print(strcat("@ProbeClassName is ", pcn));// @ProbeClassName (since 1.1)，用来指定被trace的类名
		print("...");
		print(strcat("@ProbeMethodName is ", pmn));// @ProbeMethodName用来指定被trace的方法名
		print("...");
		println(strcat("@Return >>>> result is --> ", str(result)));
	}
}
