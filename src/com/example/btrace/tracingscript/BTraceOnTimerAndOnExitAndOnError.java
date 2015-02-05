package com.example.btrace.tracingscript;

import static com.sun.btrace.BTraceUtils.classOf;
import static com.sun.btrace.BTraceUtils.printNumberMap;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.sun.btrace.BTraceUtils.Atomic;
import com.sun.btrace.BTraceUtils.Collections;
import com.sun.btrace.BTraceUtils.Reflective;
import com.sun.btrace.BTraceUtils.Sys;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnError;
import com.sun.btrace.annotations.OnExit;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.Self;

@BTrace
public class BTraceOnTimerAndOnExitAndOnError {
	private static Map<String, AtomicInteger> timers = Collections.newHashMap();
	private static volatile int i;
	@OnMethod(clazz = "+com.example.btrace.targetmethod.MyInterface", method = "test")
	public static void onTrace(@Self Object obj) {
		String classname = Reflective.name(classOf(obj));
		AtomicInteger ai = Collections.get(timers, classname);
		if (ai == null) {
			ai = Atomic.newAtomicInteger(1);
			Collections.put(timers, classname, ai);
		} else {
			Atomic.incrementAndGet(ai);
		}
	}

	@OnTimer(2000)
	public static void print() {// @OnTimer定时触发Trace
		if (Collections.size(timers) != 0) {
			printNumberMap("timers >>>> ", timers);
        }
		if (i == 5)
			Sys.exit(10);
		i++;
	}
	
	@OnExit
	public static void onexit(int code){ // 当trace完毕(可能是正常trace结束也可能是Sys.exit(int)方法导致trace结束);
		println(strcat("BTrace program exits!>>>>>>>>>> code is ", str(code)));
	}
	
	// @OnError 当trace代码抛异常或者错误时，该注解的方法会被执行. 如果同一个trace脚本中其他方法抛异常, 该注解方法也会被执行。
	@OnError
	public static void onError() {
	}
}
