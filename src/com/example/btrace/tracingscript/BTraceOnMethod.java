package com.example.btrace.tracingscript;

import static com.sun.btrace.BTraceUtils.classOf;
import static com.sun.btrace.BTraceUtils.identityStr;
import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;

import com.sun.btrace.BTraceUtils.Reflective;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Self;

@BTrace
public class BTraceOnMethod {
	@OnMethod(clazz = "com.example.btrace.targetmethod.TestOnMethod", method = "test", type = "void (java.lang.String)")
	public static void type(@Self Object obj, String text) {//type 用于精确匹配method的类型，目前看来只有参数[java.lang.String]能够用于精确匹配，返回值(void)不能用于精确匹配
		print(strcat("@Self Object's Class Name Is ", Reflective.name(classOf(obj))));
		print("...");
		print("text is ");
		println(text);
	}
	
	
//	@OnMethod(clazz = "com.example.btrace.targetmethod.TestOnMethod", method = "execute")
//	public static void a(int sleeptime) { // 可以跟踪到参数为int的execute方法
//		println(strcat("a(int sleeptime) #### sleeptime is ", str(sleeptime)));
//	}
//
//	@OnMethod(clazz = "com.example.btrace.targetmethod.TestOnMethod", method = "execute")
//	public static void b(@Self Object object, int sleeptime) {// 可以跟踪到参数为int的execute方法；Self表示被跟踪到的目标对象
//		println(strcat("b(@Self Object object, int sleeptime) ##### @Self Object is ", identityStr(object)));
//	}
//
//	@OnMethod(clazz = "com.example.btrace.targetmethod.TestOnMethod", method = "execute")
//	public static void c() { // 没有指定被追方法的参数，这样目标对象中的execute方法都会被追踪到
//		println("c()....");
//	}
//
//	@OnMethod(clazz = "com.example.btrace.targetmethod.User", method = "<init>")
//	public static void d(@ProbeClassName String pcn, @ProbeMethodName String pmn) { // 监听User的构造器方法
//		print("d(@ProbeClassName String pcn, @ProbeMethodName String pmn) #### ");
//		print(strcat("@ProbeClassName #### ", pcn));
//		println(strcat(" #### @ProbeMethodName #### ", pmn));
//	}
//	
//	@OnMethod(clazz = "com.example.btrace.targetmethod.User", method = "<init>")
//	public static void f(@ProbeClassName String pcn, @ProbeMethodName String pmn, int id, String name) { // 监听User指定参数构造器
//		print("f(@ProbeClassName String pcn, @ProbeMethodName String pmn, int id, String name) #### ");
//		print(strcat("id #### ", str(id)));
//		println(strcat(" #### name #### ", name));
//	}
}
