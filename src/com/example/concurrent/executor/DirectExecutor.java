package com.example.concurrent.executor;

import java.util.concurrent.Executor;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>Executor接口并没有严格地要求执行是异步的。在最简单的情况下，执行程序可以在调用者的线程中立即运行已提交的任务</p>
 * 
 * @author 八两俊 
 * @since
 */
public class DirectExecutor implements Executor {
	@Override
	public void execute(Runnable r) {
		r.run();
	}
}
