package com.example.javautil;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 一个基于优先级堆的【无界优先级队列】。优先级队列的元素按照其自然顺序进行排序，或者根据构造队列时提供的Comparator进行排序，具体取决于所使用的构造方法。【优先级队列不允许使用 null元素】。
 * 依靠自然顺序的优先级队列还不允许插入不可比较的对象（这样做可能导致 ClassCastException）。
 * 
 * 此队列的头是按指定排序方式确定的最小元素。如果多个元素都是最小值，则头是其中一个元素——选择方法是任意的。队列获取操作 poll、remove、peek 和 element 访问处于队列头的元素。
 * 
 * 优先级队列是无界的，但是有一个内部容量，控制着用于存储队列元素的数组大小。它通常至少等于队列的大小。【随着不断向优先级队列添加元素，其容量会自动增加。无需指定容量增加策略的细节】。
 * 
 * 此类及其迭代器实现了Collection和Iterator接口的所有可选 方法。方法 iterator()中提供的迭代器不保证以任何特定的顺序遍历优先级队列中的元素。
 * 如果需要按顺序遍历，请考虑使用 Arrays.sort(pq.toArray())。
 * 
 * 注意，【此实现不是同步的】。如果多个线程中的任意线程修改了队列，则这些线程不应同时访问PriorityQueue实例。相反，请使用线程安全的【PriorityBlockingQueue】类。
 * 
 * 实现注意事项：此实现为排队和出队方法（offer、poll、remove() 和 add）提供 O(log(n)) 时间；为 remove(Object) 和 contains(Object)方法提供线性时间；
 * 为获取方法（peek、element 和 size）提供固定时间。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class PriorityQueueTest {
	public static void main(String[] args) {
		PriorityQueue<PriorityQueueBean> idPriorityQueue =new PriorityQueue<PriorityQueueBean>(16, new Comparator<PriorityQueueBean>() {
			@Override
			public int compare(PriorityQueueBean bean1, PriorityQueueBean bean2) {
				return bean1.getId() - bean2.getId();
			}
		});
		
		idPriorityQueue.add(new PriorityQueueBean(2, 26, "猪古丽"));
		idPriorityQueue.add(new PriorityQueueBean(1, 27, "八两俊"));
		idPriorityQueue.add(new PriorityQueueBean(3, 0, "陈小左"));
		print(idPriorityQueue);
		System.out.println("++++++++++++++++++++++++++++++++++++");
		// 如果使用了无参构造函数实例化PriorityQueue，那么添加到PriorityQueue中的元素需要实现Comparable接口
		PriorityQueue<PriorityQueueBean> agePriorityQueue = new PriorityQueue<PriorityQueueBean>();
		agePriorityQueue.add(new PriorityQueueBean(2, 26, "猪古丽"));
		agePriorityQueue.add(new PriorityQueueBean(1, 27, "八两俊"));
		agePriorityQueue.add(new PriorityQueueBean(3, 0, "陈小左"));
		print(agePriorityQueue);
		
		// PriorityQueueBean的默认容量大小为11,最大容量为Integer.MAX_VALUE，会自动进行扩容
		// 内部实现采用了一个对象数组(Object[] queue)来存储元素
	}
	
	private static void print(PriorityQueue<PriorityQueueBean> beans) {
		Iterator<PriorityQueueBean> iterator = beans.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
}
