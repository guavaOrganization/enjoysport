package com.example.spring.aop;

public class TestBeforeAdvice {
	public void testBeforeAdviceWithTopPointcut() {
		System.out.println("testBeforeAdviceWithTopPointcut....");
	}

	public void testBeforeAdviceWithInnerPointcut() {
		System.out.println("testBeforeAdviceWithInnerPointcut....");
	}

	public void testBeforeExpressionPointcut() {
		System.out.println("testBeforeExpressionPointcut....");
	}
}
