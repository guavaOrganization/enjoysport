package com.example.spring.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.spring.aop.TestAfterReturning;
import com.example.spring.aop.TestAfterThrowing;
import com.example.spring.aop.TestAroundAdvice;
import com.example.spring.aop.TestBeforeAdvice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:SpringAOPSchemaContext.xml")
public class SpringAspectJContext {

	@Autowired
	TestAroundAdvice testAroundAdvice;

	@Autowired
	TestBeforeAdvice testBeforeAdvice;

	@Autowired
	TestAfterReturning testAfterReturning;

	@Autowired
	TestAfterThrowing testAfterThrowing;

	// @Test
	public void testAroundAdvice() { // 环绕通知
		testAroundAdvice.newUser("八两俊", 26);
		testBeforeAdvice.testBeforeAdviceWithTopPointcut();
	}

	// @Test
	public void testBeforeAdviceWithTopPointcut() { // 前置通知，顶级的切入点
		testBeforeAdvice.testBeforeAdviceWithTopPointcut();
	}

	// @Test
	public void testBeforeAdviceWithInnerPointcut() { // 前置通知，内置的切入点
		testBeforeAdvice.testBeforeAdviceWithInnerPointcut();
	}

	// @Test
	public void testBeforeExpressionPointcut() { // 前置通知，内置的切入点表达式
		testBeforeAdvice.testBeforeExpressionPointcut();
	}

	// @Test
	public void testAfterReturning() {// 后置通知
		testAfterReturning.testAfterReturning();
	}

	@Test
	public void testAfterThrowing() throws Exception {
		testAfterThrowing.testAfterThrowing("八两俊");
	}
}
