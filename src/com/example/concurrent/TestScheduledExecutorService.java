package com.example.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.example.concurrent.executorservice.BeeperControl;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 所有超级接口：
 * Executor, ExecutorService
 * 所有已知实现类：
 * ScheduledThreadPoolExecutor
 * </p>
 *
 * <p>一个 ExecutorService，可安排在给定的延迟后运行或定期执行的命令。</p>
 * 
 * <p>
 * schedule方法使用各种延迟创建任务，并返回一个可用于取消或检查执行的任务对象。
 * scheduleAtFixedRate和 scheduleWithFixedDelay方法创建并执行某些在取消前一直定期运行的任务。
 * </p>
 * 
 * <p>
 * 用Executor.execute(java.lang.Runnable)和 ExecutorService的 submit方法所提交的命令，通过所请求的 0延迟进行安排。
 * schedule方法中允许出现 0和负数延迟（但不是周期），并将这些视为一种立即执行的请求。
 * </p>
 * 
 * <p>
 * 所有的 schedule方法都接受相对延迟和周期作为参数，而不是绝对的时间或日期。将以Date所表示的绝对时间转换成要求的形式很容易。
 * 例如，要安排在某个以后的Date运行，可以使用：schedule(task,date.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS)。
 * 但是要注意，由于网络时间同步协议、时钟漂移或其他因素的存在，因此相对延迟的期满日期不必与启用任务的当前Date相符。 
 * Executors类为此包中所提供的ScheduledExecutorService实现提供了便捷的工厂方法。
 * </p>
 * @author 八两俊 
 * @since
 */
public class TestScheduledExecutorService {
	public void testBeeperControl() {
		new BeeperControl().beepForAnHour();
		try {
			TimeUnit.HOURS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void testSchedule(){
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
		ScheduledFuture<String> future = executorService.schedule(
		new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "callable";
			}
		}, 10, TimeUnit.SECONDS);
		
		/**
		 * <p>
		 * 一个延迟的、结果可接受的操作，可将其取消。通常已安排的 future 是用 ScheduledExecutorService 安排任务的结果
		 * </p>
		 */
		ScheduledFuture<?> scheduledFuture = executorService.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("runnable");
			}
		}, 10, TimeUnit.SECONDS);
		
		try {
			System.out.println(future.get());
			System.out.println(scheduledFuture.get());
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void testScheduleAtFixedRate() {
		final long current = System.currentTimeMillis();
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		/**
		 * <p>
		 * 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；也就是将在initialDelay后开始执行，然后在 initialDelay+period后执行，
		 * 接着在 initialDelay + 2 * period 后执行，依此类推。如果任务的任何一个执行遇到异常，则后续执行都会被取消。否则，只能通过执行程序的取消或终止方法来终止该任务。
		 * 如果此任务的任何一个执行要花费比其周期更长的时间，则将推迟后续执行，但不会同时执行。
		 * </p>
		 */
		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(11);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis() - current + " scheduleAtFixedRate");
			}
		}, 10, 10, TimeUnit.SECONDS);
		
		try {
			TimeUnit.SECONDS.sleep(60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testScheduleWithFixedDelay(){
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
		final long current = System.currentTimeMillis();
		/**
		 * <p>
		 * 创建并执行一个在给定初始延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。如果任务的任一执行遇到异常，就会取消后续执行。
		 * 否则，只能通过执行程序的取消或终止方法来终止该任务。
		 * </p>
		 */
		executorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(11);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis() - current + " scheduleWithFixedDelay");
			}
		}, 10, 10, TimeUnit.SECONDS);
		try {
			TimeUnit.SECONDS.sleep(60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
