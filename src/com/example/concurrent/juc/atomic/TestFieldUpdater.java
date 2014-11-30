package com.example.concurrent.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import org.junit.Test;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	AtomicIntegerFieldUpdater<T>/AtomicLongFieldUpdater<T>/AtomicReferenceFieldUpdater<T,V>
 *  是基于反射的原子更新字段的值。
 *  相应的API也是非常简单的，但是也是有一些约束的。
 *  <span>
 *  （1）字段必须是volatile类型的。
 *  </span>
 *  <span>
 *  （2）字段的描述类型（修饰符public/protected/default/private）是与调用者与操作对象字段的关系一致。也就是说调用者能够直接操作对象字段，那么就可以反射进行原子操作。
 *   但是对于父类的字段，子类是不能直接操作的，尽管子类可以访问父类的字段。
 *  </span>
 *  <span>
 *  只能是实例变量，不能是类变量，也就是说不能加static关键字。
 *  </span>
 *  <span>
 *  只能是可修改变量，不能使final变量，因为final的语义就是不可修改。实际上final的语义和volatile是有冲突的，这两个关键字不能同时存在。
 *  </span>
 *  <span>
 *  （5）对于AtomicIntegerFieldUpdater和AtomicLongFieldUpdater只能修改int/long类型的字段，不能修改其包装类型（Integer/Long）。
 *  如果要修改包装类型就需要使用AtomicReferenceFieldUpdater。
 *  </span>
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class TestFieldUpdater {
	class DemoData {
		public volatile int value1 = 1;
		volatile int value2 = 2;
		/**
		 * <p>
		 * value3/value4对于TestFieldUpdater类是不可见的，因此通过反射是不能直接修改其值的。
		 * </p> 
		 */
		protected volatile int value3 = 3;
		private volatile int value4 = 4;
		
		volatile Integer value5 = 5;
	}
	
	AtomicIntegerFieldUpdater<DemoData> getIntegerUpdater(String fieldName){
		return AtomicIntegerFieldUpdater.newUpdater(DemoData.class, fieldName);
	}
	
	AtomicReferenceFieldUpdater<DemoData, Integer> getAtomicReferenceFieldUpdater(String fieldName) {
		return AtomicReferenceFieldUpdater.newUpdater(DemoData.class, Integer.class, fieldName);
	}
	@Test
	public void testAtomicIntegerFieldUpdater() {
        DemoData data = new DemoData();
		System.out.println(getIntegerUpdater("value1").get(data));
		System.out.println(getIntegerUpdater("value2").decrementAndGet(data));
		// System.out.println(getIntegerUpdater("value3").decrementAndGet(data));
		System.out.println(getAtomicReferenceFieldUpdater("value5").get(data));
	}
}
