package com.example.designpattern.builder;

import java.util.ArrayList;
import java.util.List;

import com.example.designpattern.factorymethod.MailSender;
import com.example.designpattern.factorymethod.Sender;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 建造者模式将很多功能集成到一个类里，这个类可以创造出比较复杂的东西。
 * 所以与工厂模式的区别就是：工厂模式关注的是创建单个产品，而建造者模式则关注创建符合对象，多个部分。
 * 因此，是选择工厂模式还是建造者模式，依实际情况而定。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class Builder {
	private List<Sender> list = new ArrayList<Sender>();

	public void produceMailSender(int count) {
		for (int i = 0; i < count; i++) {
			list.add(new MailSender());
		}
	}
}
