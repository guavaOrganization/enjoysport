package com.example.concurrent;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * ThreadPoolExecutor，它可另行安排在给定的延迟后运行命令，或者定期执行命令。需要多个辅助线程时，或者要求 ThreadPoolExecutor 具有额外的灵活性或功能时，此类要优于 Timer。
 * </p>
 * 
 * <p>
 * 一旦启用已延迟的任务就执行它，但是有关何时启用，启用后何时执行则没有任何实时保证。按照提交的先进先出 (FIFO) 顺序来启用那些被安排在同一执行时间的任务。
 * </p>
 * 
 * <p>
 * 虽然此类继承自 ThreadPoolExecutor，但是几个继承的调整方法对此类并无作用。
 * 特别是，因为它作为一个使用 corePoolSize 线程和一个无界队列的固定大小的池，所以调整 maximumPoolSize 没有什么效果。
 * </p>
 * 
 * <p>
 * 扩展注意事项：此类重写 AbstractExecutorService的submit方法，以生成内部对象控制每个任务的延迟和调度。
 * 若要保留功能性，子类中任何进一步重写的这些方法都必须调用超类版本，超类版本有效地禁用附加任务的定制。
 * 但是，此类提供替代受保护的扩展方法decorateTask（为 Runnable和 Callable各提供一种版本），可定制用于通过execute、submit、schedule、scheduleAtFixedRate
 * 和 scheduleWithFixedDelay进入的执行命令的具体任务类型。默认情况下，ScheduledThreadPoolExecutor使用一个扩展 FutureTask的任务类型。
 * 但是，可以使用下列形式的子类修改或替换该类型。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class TestScheduledThreadPoolExecutor {
	
	public void testDecorateTask() {
		final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		
		final ScheduledFuture future = executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("aaaaaaaaaaaa");
			}
		}, 2, 2, TimeUnit.SECONDS);
		
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemove() {
		final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("zzzzzzzzzzzzz");
			}
		}, 2, 2, TimeUnit.SECONDS);
		
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("pppppppppp");
			}
		}, 2, 2, TimeUnit.SECONDS);
		
		final Runnable a = new Runnable() {
			@Override
			public void run() {
				System.out.println("aaaaaaaaaaaaaaaaa");
			}
		};
		
		final ScheduledFuture<?> future = executor.scheduleAtFixedRate(a, 2, 3, TimeUnit.SECONDS);
		
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("移除Runnable[a].............");
				/**
				 * <p>
				 * 从执行程序的内部队列中移除此任务（如果存在），从而如果尚未开始，则其不再运行。
				 * 此方法可用作取消方案的一部分。它可能无法移除在放置到内部队列之前已经转换为其他形式的任务。例如，使用 submit 输入的任务可能被转换为维护 Future 状态的形式。
				 * 但是，在此情况下，ThreadPoolExecutor.purge() 方法可用于移除那些已被取消的 Future。
				 * </p>
				 */
//				executor.remove(a);	
				future.cancel(true);
				/**
				 * <p>
				 * 尝试从工作队列移除所有已取消的 Future任务。此方法可用作存储回收操作，它对功能没有任何影响。取消的任务不会再次执行，但是它们可能在工作队列中累积，
				 * 直到 worker线程主动将其移除。调用此方法将试图立即移除它们。但是，如果出现其他线程的干预，那么此方法移除任务将失败。
				 * </p>
				 */
				executor.purge();
			}
		}, 12, TimeUnit.SECONDS);
		
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
