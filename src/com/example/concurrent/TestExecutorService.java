package com.example.concurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.example.concurrent.callable.HelloCallable;
import com.example.concurrent.executorservice.NetworkService;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>ExecutorService 提供了管理终止的方法，以及可为跟踪一个或多个异步任务执行状况而生成 Future的方法。 </p>
 * 
 * <p>
 * 可以关闭 ExecutorService，这将导致其拒绝新任务。提供两个方法来关闭 ExecutorService。
 * shutdown()方法在终止前允许执行以前提交的任务，而 shutdownNow()方法阻止等待任务启动并试图停止当前正在执行的任务。
 * 在终止时，执行程序没有任务在执行，也没有任务在等待执行，并且无法提交新任务。应该关闭未使用的 ExecutorService以允许回收其资源。 
 * </p>
 * 
 * <p>
 * 通过创建并返回一个可用于取消执行或等待完成的 Future，方法 submit扩展了基本方法 Executor.execute(java.lang.Runnable)。
 * 方法 invokeAny和 invokeAll是批量执行的最常用形式，它们执行任务 collection，然后等待至少一个，
 * 或全部任务完成（可使用 ExecutorCompletionService类来编写这些方法的自定义变体）。 
 * </p>
 * 
 * <p>
 * Executors类提供了用于此包中所提供的执行程序服务的工厂方法。 
 * </p>
 * @author 八两俊 
 * @since
 */
public class TestExecutorService {
	public void testNetworkService() {
		try {
			NetworkService service = new NetworkService(20, 5);
			service.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdownAndAwaitTermination() {
		ExecutorService service = Executors.newFixedThreadPool(2);
	
		service.execute(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("zzzzzzzzzzz");
			}
		});
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("pppppppppppppp");
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("qqqqqqqqqqqqq");
			}
		});
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("lllllllllllllll");
			}
		});
		
		/**
		 * <p>启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。</p>
		 */
		service.shutdown();
		
		try {
			service.execute(new Runnable() {// 不允许在加入新的任务
				@Override
				public void run() {
					System.out.println("aaaaaaaaaaaaa");
				}
			});
		} catch (Exception e) {
			System.out.println("ExecutorService 已经被关闭了,不能再加入新的Runnable了");
		}
		
		try {
			/**
			 * <p>
			 * 这个方法有两个参数，一个是timeout即超时时间，另一个是unit即时间单位。
			 * 这个方法会使线程等待timeout时长，当超过timeout时间后，会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false。
			 * </p>
			 */
			if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
				/**
				 * <p>如果此执行程序已关闭，则返回true。</p>
				 */
				System.out.println("isShutdown>>>>>>>" + service.isShutdown());
				/**
				 * <p>如果关闭后所有任务都已完成，则返回true。注意，除非首先调用 shutdown或shutdownNow，否则 isTerminated永不为 true。</p>
				 */
				System.out.println("isTerminated>>>>>>>>>" + service.isTerminated());
				/**
				 * <p>试图停止所有正在执行的活动任务，暂停处理正在等待的任务，并返回等待执行的任务列表。</p>
				 * 
				 * <p>无法保证能够停止正在处理的活动执行任务，但是会尽力尝试。例如，通过Thread.interrupt()来取消典型的实现，所以任何任务无法响应中断都可能永远无法终止。</p>
				 */
				List<Runnable> list = service.shutdownNow();
				System.out.println("isTerminated===========" + service.isTerminated());
				System.out.println(null == list ? 0 : list.size());
				if (!service.awaitTermination(5, TimeUnit.SECONDS)){
					System.err.println("Pool did not terminate");
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			service.shutdownNow();
			Thread.currentThread().interrupt();
		}
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void testSubmit() {
		ExecutorService service = Executors.newFixedThreadPool(2);
		
		/**
		 * <p>提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future。该 Future 的 get 方法在成功 完成时将会返回 null。</p>
		 */
		Future<String> submitRunnableResult = (Future<String>) service.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("ExecutorService....sumbit(Runnable)");
			}
		});
		try {
			System.out.println(submitRunnableResult.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		/**
		 * <p>提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future。该 Future 的 get 方法在成功完成时将会返回给定的结果。</p>
		 */
		Future<String> submitRunnableHasResult = service.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("ExecutorService....sumbit(Runnable,result)");
			}
		},"hello");
		try {
			System.out.println(submitRunnableHasResult.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		/**
		 * <p>
		 * 提交一个有返回值的任务用于执行，返回一个表示任务的未决结果的 Future。该 Future的 get方法在成功完成时将会返回该任务的结果。
		 * 如果想立即阻塞任务的等待，则可以使用result=exec.submit(aCallable).get();形式的构造。
		 * 注：Executors类包括了一组方法，可以转换某些其他常见的类似于闭包的对象，例如，将 PrivilegedAction 转换为 Callable形式，这样就可以提交它们了。
		 * </p>
		 */
		Future<String> submitCallableResult = service.submit(new HelloCallable());
		try {
			System.out.println(submitCallableResult.get());// 阻塞了
			System.out.println("The End");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void testInvokeAll() {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		List<Callable<String>> list = new ArrayList<Callable<String>>();
		list.add(new HelloCallable());
		list.add(new HelloCallable());
		list.add(new HelloCallable());
		try {
			long current = System.currentTimeMillis();
			/**
			 * <p>
			 * 执行给定的任务，当所有任务完成时，返回保持任务状态和结果的Future列表。
			 * 返回列表的所有元素的 Future.isDone()为 true。注意，可以正常地或通过抛出异常来终止已完成任务。
			 * 如果正在进行此操作时修改了给定的 collection，则此方法的结果是不确定的。
			 * </p>
			 */
			// List<Future<String>> results = executorService.invokeAll(list);// 会导致调用线程阻塞
			/**
			 * <p>
			 * 执行给定的任务，当所有任务完成或超时期满时（无论哪个首先发生），返回保持任务状态和结果的 Future 列表。
			 * 返回列表的所有元素的 Future.isDone()为 true。一旦返回后，即取消尚未完成的任务。注意，可以正常地或通过抛出异常来终止已完成任务。
			 * 如果此操作正在进行时修改了给定的 collection，则此方法的结果是不确定的。
			 * </p>
			 */
			List<Future<String>> results = executorService.invokeAll(list, 10, TimeUnit.SECONDS);
			for (Future<String> future : results) {
				try {
					System.out.println(future.get(5, TimeUnit.SECONDS));
				} catch (ExecutionException | TimeoutException e) {
					e.printStackTrace();
				}
			}
			System.out.println(System.currentTimeMillis() - current);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvokeAny() {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		List<Callable<String>> list = new ArrayList<Callable<String>>();
		list.add(new HelloCallable());
		list.add(new HelloCallable());
		list.add(new HelloCallable());
		try {
			/**
			 * <p>
			 * 执行给定的任务，如果某个任务已成功完成（也就是未抛出异常），则返回其结果。
			 * 一旦正常或异常返回后，则取消尚未完成的任务。
			 * 如果此操作正在进行时修改了给定的 collection，则此方法的结果是不确定的。
			 * </p>
			 */
			String result = executorService.invokeAny(list);
			System.out.println("result is " + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
