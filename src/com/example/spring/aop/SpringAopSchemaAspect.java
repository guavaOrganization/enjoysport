package com.example.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

public class SpringAopSchemaAspect {
	/**
	 * 环绕通知
	 * 
	 * @since
	 * @throws
	 */
	public Object monitorAround(ProceedingJoinPoint call, String name, int age)
			throws Throwable {
		System.out.println("Around Advice Enter...");
		Object ret = call.proceed();
		System.out.println("Around Advice Outer...ret : " + ret);
		return ret;
	}

	/**
	 * 前置通知
	 * 
	 * @since
	 * @throws
	 */
	public void monitorBeforeTopPointcut() {
		System.out
				.println("Before Advice, method is monitorBeforeTopPointcut() Enter");
	}

	/**
	 * 前置通知
	 * 
	 * @since
	 * @throws
	 */
	public void monitorBeforeInnerPointcut() {
		System.out
				.println("Before Advice, method is monitorBeforeInnerPointcut() Enter");
	}

	/**
	 * 前置通知
	 * 
	 * @since
	 * @throws
	 */
	public void monitorBeforeExpressionPointcut() {
		System.out
				.println("Before Advice, method is monitorBeforeExpressionPointcut() Enter");

	}

	/**
	 * 后置通知
	 * 
	 * @since
	 * @throws
	 */
	public void monitorAfterReturning(Object retVal) {
		retVal = "<< retVal >>";
		System.out
				.println("AfterReturning Advice, method is monitorAfterReturning() Enter");
	}
	
	/**
	 * 异常通知
	 * @since
	 * @throws
	 */
	public void monitorAfterThrowing(Object vinoEx) {
		System.out.println("AfterThrowing Advice, method is monitorAfterThrowing() Enter");
		if (null != vinoEx) {
			Exception exception = (Exception)vinoEx;
			System.out.println(exception.getMessage());

		}
	}
	
	/**
	 * 最终通知
	 * <p>
	 * 	任何通知方法可以将第一个参数定义为org.aspectj.lang.JoinPoint类型（但是环绕通知需要第一个参数为ProceedingJoinPoint类型，它是JoinPoint的一个子类）。
	 * </p>
	 * @since
	 * @throws
	 */
	public void monitorAfter(JoinPoint joinPoint, String name) { // 绑定连接点方法
		System.out.println("name is : " + name);
		Object[] args = joinPoint.getArgs();// 返回方法参数
		Object proxyObject = joinPoint.getThis();// 返回代理对象
		Object targetObject = joinPoint.getTarget();// 返回目标对象
		Signature signature = joinPoint.getSignature();// 返回正在被通知的方法相关信息
		System.out.println("After Advice, method is monitorAfter() Enter");
	}
}
