package com.example.btrace.tracingscript;
import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils.Reflective;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Self;
import com.sun.btrace.annotations.TargetInstance;
import com.sun.btrace.annotations.TargetMethodOrField;

@BTrace
public class BTraceLocationCall {
//	@OnMethod(clazz = "com.example.btrace.targetmethod.TestLocationCall", method = "testLocationCall", location = @Location(value = Kind.CALL, clazz = "/.*/", method = "call"))
//	public static void a(@Self Object obj, @ProbeClassName String pcn,
//			@ProbeMethodName String pmn, @TargetInstance Object instance,
//			@TargetMethodOrField String method, AnyType[] args) {// text为入参
//		// TestLocationCall.testLocationCall方法中调用了*.call的地方
//		// @TargetInstance和@TargetMethodOrField注解只能在使用了Location(value=Kind.CALL)的时候使用
//		print(strcat("@Self Object's Class Name Is ", Reflective.name(classOf(obj))));
//		print("...");
//		print(strcat("@ProbeClassName is ", pcn));// @ProbeClassName (since 1.1)，用来指定被trace的类名
//		print("...");
//		print(strcat("@ProbeMethodName is ", pmn));// @ProbeMethodName用来指定被trace的方法名
//		print("...");
//		print(strcat("@TargetInstance Object's Class Name Is ", Reflective.name(classOf(instance))));
//		print("...");
//		print(strcat("@TargetMethodOrField is ", method));
//		print("...");
//        printArray(args);
//        println();
//	}
	
	@OnMethod(clazz = "com.example.btrace.targetmethod.TestLocationCall", method = "testLocationCall", location = @Location(value = Kind.CALL, clazz = "/.*/", method = "call"))
	public static void a(@Self Object obj, @ProbeClassName String pcn,
			@ProbeMethodName String pmn, @TargetInstance Object instance,
			@TargetMethodOrField String method, String text) {// text为入参
		// TestLocationCall.testLocationCall方法中调用了*.call的地方
		// @TargetInstance和@TargetMethodOrField注解只能在使用了Location(value=Kind.CALL)的时候使用
		print(strcat("@Self Object's Class Name Is ", Reflective.name(classOf(obj))));
		print("...");
		print(strcat("@ProbeClassName is ", pcn));// @ProbeClassName (since 1.1)，用来指定被trace的类名
		print("...");
		print(strcat("@ProbeMethodName is ", pmn));// @ProbeMethodName用来指定被trace的方法名
		print("...");
		print(strcat("@TargetInstance Object's Class Name Is ", Reflective.name(classOf(instance))));
		print("...");
		print(strcat("@TargetMethodOrField is ", method));
		print("...");
        println(text);
	}
	
	
	// @OnMethod(clazz = "/.*/", method = "call")
	// public static void b(@Self Object object, String text) {
	// println(strcat("text >>>> ", text));
	// }
}
