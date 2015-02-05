package com.example.btrace.tracingscript;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

@BTrace
public class BTraceSubtypeTracer {
	@OnMethod(clazz = "+com.example.btrace.targetmethod.MyInterface", method = "test")
	public static void g(@ProbeClassName String pcn, @ProbeMethodName String pmn) {
		print("g(@ProbeClassName String pcn, @ProbeMethodName String pmn),");
		print(pcn);
		print(" #### ");
		println(pmn);
	}
}
