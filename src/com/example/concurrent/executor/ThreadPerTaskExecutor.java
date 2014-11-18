package com.example.concurrent.executor;

import java.util.concurrent.Executor;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>任务是在某个不是调用者线程的线程中执行的。以下执行程序将为每个任务生成一个新线程。 </p>
 * 
 * @author 八两俊 
 * @since
 */
public class ThreadPerTaskExecutor implements Executor {
	@Override
	public void execute(Runnable r) {
		new Thread(r).start();
	}
}
