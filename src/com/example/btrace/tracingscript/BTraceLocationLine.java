package com.example.btrace.tracingscript;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.*;

@BTrace
public class BTraceLocationLine {
	@OnMethod(clazz = "com.example.btrace.targetmethod.TestLocationLine", location = @Location(value = Kind.LINE, line = -1))
	public static void online(@ProbeClassName String pcn, @ProbeMethodName String pmn, int line) {
		// 使用Kind.LINE，只有@OnMethod中的clazz属性有用
		// 用来将打印TestLocationLine中所有被追踪到的方法执行的行数
		print(Strings.strcat(pcn, "."));
		print(Strings.strcat(pmn, ":"));
		println(line);
	}
}
