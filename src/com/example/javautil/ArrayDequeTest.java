package com.example.javautil;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * Deque接口的【大小可变数组的实现】。【数组双端队列没有容量限制】；它们可根据需要增加以支持使用。它们不是线程安全的；
 * 在没有外部同步时，它们不支持多个线程的并发访问。禁止 null元素。此类很可能在用作堆栈时快于Stack，在用作队列时快于LinkedList。
 * 
 * 大多数 ArrayDeque操作以摊销的固定时间运行。异常包括remove、removeFirstOccurrence、removeLastOccurrence、
 * contains、iterator.remove()以及批量操作，【它们均以线性时间运行】。
 * 
 * 此类的iterator方法返回的迭代器是快速失败的：如果在创建迭代器后的任意时间通过除迭代器本身的remove方法之外的任何其他方式修改了双端队列，
 * 则迭代器通常将抛出ConcurrentModificationException。因此，面对并发修改，迭代器很快就会完全失败，而不是冒着在将来不确定的时刻任意发生不确定行为的风险。
 * 
 * 注意，迭代器的快速失败行为不能得到保证，一般来说，存在不同步的并发修改时，不可能作出任何坚决的保证。快速失败迭代器尽最大努力抛出ConcurrentModificationException。
 * 因此，编写依赖于此异常的程序是错误的，正确做法是：迭代器的快速失败行为应该仅用于检测 bug。
 * 
 * 此类及其迭代器实现 Collection 和 Iterator 接口的所有可选 方法。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class ArrayDequeTest {
	public static void main(String[] args) {
		ArrayDeque<ArrayDequeElement> arrayDeque = new ArrayDeque<ArrayDequeElement>();
		arrayDeque.addFirst(new ArrayDequeElement(2));
		arrayDeque.addFirst(new ArrayDequeElement(3));
		arrayDeque.addLast(new ArrayDequeElement(4));
		print(arrayDeque);
		// 内部使用一个数组(E[] elements)存储元素，通过head和tail两个指针来实现双端队列控制。
		// 无参构造器的默认容量是16，如果在添加新元素时容量达到最大，则对数组进行扩容
	}
	
	private static void print(ArrayDeque<ArrayDequeElement> arrayDeque) {
		Iterator<ArrayDequeElement> it = arrayDeque.iterator();
		while (it.hasNext())
			System.out.println(it.next());
	}
}
