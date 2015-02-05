package com.example.btrace.tracingscript;

import static com.sun.btrace.BTraceUtils.*;

import java.util.HashMap;

import com.example.btrace.targetmethod.TestHello;
import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Self;
import com.sun.btrace.annotations.TargetInstance;
import com.sun.btrace.annotations.TargetMethodOrField;

@BTrace
public class BTraceTestHello {
	@OnMethod(clazz = "java.util.HashMap", method = "put")
	public static void m(@Self HashMap map, Object key, Object value) { // @Self只被追踪方法对应的this
		// println("============xxxxxxxxxxxxxxx============");
		// print(key);
		// print(":");
		// println(value);
		// println("============xxxxxxxxxxxxxxx============");
	}

	@OnMethod(clazz = "com.example.btrace.targetmethod.TestHello", method = "test", location = @Location(value = Kind.RETURN))
	public static void d(@Self TestHello obj, int age, @Return boolean result) {// 被追踪方法的返回值
		// print("test method is called, input age=");
		// print(age);
		// print("***************result is ");
		// print(result);
		// println("***********************");
	}

	// 被追踪方法中调用@Location中clazz和method方法示例
	@OnMethod(clazz = "com.example.btrace.targetmethod.TestHello", method = "test", location = @Location(value = Kind.CALL, clazz = "/.*/", method = "put"))
	public static void f(@Self Object self, @ProbeClassName String pcn,
			@ProbeMethodName String pmn, @TargetInstance Object ti,
			@TargetMethodOrField String tmof) {
		println("********************************++++++++++++++++++++++++++++++++++++********************************");
		print(strcat("ProbeClassName: ", pcn));
		print(strcat(",ProbeMethodName: ", pmn));
		print(strcat(",TargetInstance: ", str(classOf(ti))));
		print(strcat(",TargetMethodOrField : ", str(tmof)));
		println(strcat(",Self : ", str(classOf(self))));
		println("********************************++++++++++++++++++++++++++++++++++++********************************");
	}

	@OnMethod(clazz = "com.example.btrace.targetmethod.TestHello", method = "test", location = @Location(value = Kind.CALL, clazz = "/.*/", method = "put"))
	public static void g(@Self Object self, @ProbeMethodName String pmn, AnyType[] args) {
		// println("********************************++++++++++++++++++++++++++++++++++++********************************");
		// printArray(args);
		// println("********************************++++++++++++++++++++++++++++++++++++********************************");
	}
}
