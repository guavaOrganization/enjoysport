package com.example.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	对于PairManager类的结构，它的一些功能在基类中实现，并且其中一个或多个抽象方法在派生类中定义，这种结构在设计模式中称为"模版方法"
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public abstract class PairManager {
	AtomicInteger checkCounter = new AtomicInteger(0);
	protected Pair pair = new Pair();

	private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());

	public synchronized Pair getPair() {
		return new Pair(pair.getX(), pair.getY());
	}

	protected void store(Pair p) {
		storage.add(p);
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public abstract void increment();// 对于抽象方法，同步控制将在实现的时候进行处理
}
