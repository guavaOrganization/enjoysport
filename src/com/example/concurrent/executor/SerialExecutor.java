package com.example.concurrent.executor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>许多Executor实现都对调度任务的方式和时间强加了某种限制。以下执行程序使任务提交与第二个执行程序保持连续，这说明了一个复合执行程序。</p>
 * 
 * @author 八两俊 
 * @since
 */
public class SerialExecutor implements Executor {
	final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
	final Executor executor;
	Runnable active;
	
	public SerialExecutor(Executor executor) {
		this.executor = executor;
	}
	
	@Override
	public synchronized void execute(final Runnable runnable) {
		tasks.offer(new Runnable() {// 将指定元素插入此双端队列的末尾。
			@Override
			public void run() {
				try {
					runnable.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		if (active == null) {
			scheduleNext();
		}
	}

	protected synchronized void scheduleNext() {
		if ((active = tasks.poll()) != null) {// 获取并移除此双端队列所表示的队列的头（换句话说，此双端队列的第一个元素）；如果此双端队列为空，则返回null。
			executor.execute(active);// 在未来某个时间执行给定的命令。该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由Executor 实现决定。
			active = null;
		}
	}
}
