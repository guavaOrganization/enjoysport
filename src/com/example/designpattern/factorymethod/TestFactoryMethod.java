package com.example.designpattern.factorymethod;

import org.junit.Test;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	Java设计模式之工厂方法模式
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class TestFactoryMethod {
	@Test
	/**
	 * <p>
	 * Java设计模式之工厂方法模式--普通工厂模式
	 * </p>
	 * <p>
	 * 普通工厂模式，就是建立一个工厂类，对实现了同一接口的一些类进行实例的创建
	 * </p>
	 * @since
	 * @throws
	 */
	public void testNormalFactoryMethod() throws Exception {
		SenderFactory factory = new SenderFactory();
		Sender sender = factory.produceSender(SenderFactory.SENDER_TYPE_MAIL);
		sender.send();
	}
	
	/**
	 * <p>
	 * Java设计模式之工厂方法模式--多个工厂方法模式
	 * </p> 
	 * 
	 * <p>
	 * 是对普通工厂方法模式的改进，在普通工厂方法模式中，如果传递的字符串出错，则不能正确创建对象，而多个工厂方法模式是提供多个工厂方法，分别创建对象。
	 * </p>
	 * @since
	 * @throws
	 */
	public void testMutliFactoryMethod(){
		MutliSenderFactory factory = new MutliSenderFactory();
		factory.produceSmsSender().send();
	}
	
	/**
	 * <p>
	 * Java设计模式之工厂方法模式--静态工厂方法模式
	 * </p>
	 * @since
	 * @throws
	 */
	public void testStaticFactoryMethod(){
		StaticSenderFactory.produceMail().send();
	}
}
