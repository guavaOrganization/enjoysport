package com.example.designpattern.observer;

import java.util.ArrayList;

/**
 * Observer模式的定义如下: “定义对象间的一种一对多的依赖关系,当一个对象的状态发生改变时,所有依赖于它的对象都得到通知并被自动更新”
 * 
 * Observer模式实现的关键点有两个:
 * 
 * 发布:又可以称为通知,即:当某个对象的状态发生变化时,需要将变化的事件通知其它对象,就像某个
 * 厂商开发了一款新产品,需要“广而告之”一样。我们称发出通知的对象为“发布者”。
 * 
 * 订阅:又可以称为观察,即:某个对象对某个“发布者”感兴趣,需要观察或者监视发布者的相关状态变
 * 化。当发布者发出通知后,订阅者需要作出相应的响应,我们称响应通知的对象为“订阅者”或者“观察者”。
 * 
 * 对应到我们上面给出的微博样例,“Twitter”类其实就是一个“发布者”,而“Audit”、“Recommender”、
 * “Statistics”类就是“订阅者”。 通过“发布者”发布通知,“订阅者”响应通知这种方式,“发布者”不需要知道具体的“订阅者”是哪些,
 * 也不需要知道“订阅者”究竟要如何响应。当“订阅者”数量上的增加或者删除,或者某个“订阅者”内 部实现变化,都不会影响“发布者”。
 * 
 * @author mcfly
 *
 */
public class Subject { // 发布者
	protected ArrayList<Observer> observers = new ArrayList<Observer>();

	/**
	 * 添加“观察者”
	 */
	public void attach(Observer o) {
		observers.add(o);
	}

	/**
	 * 删除“观察者”
	 */
	public void detach(Observer o) {
		observers.remove(o);
	}

	/**
	 * 通知所有的“观察者”
	 */
	public void notifyObservers() {
		for (Observer o : observers) {
			//此处只需要调用“观察者”提供的通知函数,并不需要知道“观察者”具体需要做什么
			o.update();
		}
	}
}
