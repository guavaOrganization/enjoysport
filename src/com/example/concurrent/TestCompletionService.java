package com.example.concurrent;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 将生产新的异步任务与使用已完成任务的结果分离开来的服务。生产者submit执行的任务。
 * 使用者take已完成的任务，并按照完成这些任务的顺序处理它们的结果。
 * 例如，CompletionService可以用来管理异步IO，执行读操作的任务作为程序或系统的一部分提交，然后，当完成读操作时，
 * 会在程序的不同部分执行其他操作，执行操作的顺序可能与所请求的顺序不同。
 * </p>
 * 
 * <p>
 * 通常，CompletionService依赖于一个单独的Executor来实际执行任务，在这种情况下，CompletionService只管理一个内部完成队列。
 * ExecutorCompletionService类提供了此方法的一个实现。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class TestCompletionService {
	@Test
	public void testCompletionService() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		ExecutorCompletionService<Integer> service = new ExecutorCompletionService<Integer>(executorService);
		
		for (int i = 0; i < 10; i++) {
			service.submit(getTask());
		}
		
		int sum = 0;  
		for (int i = 0; i < 10; i++) {
			/**
			 * <p>
			 * 获取并移除表示下一个已完成任务的 Future，如果目前不存在这样的任务，则等待。
			 * </p>
			 */
			// Future<Integer> future = service.take();
			/**
			 * <p>
			 * 获取并移除表示下一个已完成任务的 Future，如果不存在这样的任务，则返回 null。
			 * </p>
			 */
			// Future<Integer> future = service.poll();
			
			Future<Integer> future = service.poll(1, TimeUnit.SECONDS);
			if (null != future) {
				System.out.println(future.get());
				sum += future.get();
			}
		}
		System.out.println("总数为：" + sum);
		executorService.shutdown();
	}
	
	public Callable<Integer> getTask() {
		final Random random = new Random();
		Callable<Integer> task = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				int i = random.nextInt(10);
				TimeUnit.SECONDS.sleep(i);
				int j = random.nextInt(10);
				int sum = i * j;
				System.out.print(sum + "\t");
				return sum;
			}
		};
		return task;
	}
}
