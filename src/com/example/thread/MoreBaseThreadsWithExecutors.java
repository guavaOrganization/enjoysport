package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MoreBaseThreadsWithExecutors {
	public static void main(String[] args) {
		// CachedThreadPool在程序执行过程中会创建与所需数量相同的线程，然后在回收旧线程时停止创建新线程
		// ExecutorService executorService = Executors.newCachedThreadPool();
		// 创建5个核心线程(code thread)的线程池
		// ExecutorService executorService = Executors.newFixedThreadPool(5);
		// 创建1个核心线程(code thread)的线程池，SingleThreadExecutor对于希望在一个线程中连续运行的任何事物来说，都是很有用的。
		// 例如监听进入的套接字连接的任务
		// SingleThreadExecutor会序列化所有提交给它的任务,并会维护它自己(隐藏)的悬挂任务队列
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 5; i++)
			executorService.execute(new LiftOff());
		executorService.shutdown();// 防止新任务被提交给Executor
	}
}
