package com.example.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 启动AspectJ支持后，可以使用@Aspect来定义一个切面
 * </p>
 * 
 * @author 八两俊 
 * @since
 */

/** 定义一个切面 */
@Aspect
public class NotVeryUsefulAspect {
	
	// 切入点决定了连接点(Join Point)关注的内容，使得我们可以控制通知什么时候执行，Spring AOP只支持Spring bean的方法执行连接点。
	// 所以可以把切入点看做是Spring bean上方法执行的匹配。
	// 一个切入点声明有两个部分
	// 1、包含名字和任意参数的签名
	// 2、一个切入点表达式
	@Pointcut("execution(* com.example.spring.aop.Aop*.*(..))")
	private void anyOldTransfer() { // 作为切入点签名的方法必须返回void类型

	}
}
