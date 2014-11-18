package com.example.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * 
 * <p>Executors支持以下各种方法：</p>
 * <span>创建并返回设置有常用配置字符串的ExecutorService</span>
 * <span>创建并返回设置有常用配置字符串的ScheduledExecutorService的方法</span>
 * <span>创建并返回“包装的”ExecutorService方法，它通过是特定于实现的方法不可访问来禁用重新配置</span>
 * <span>创建并返回ThreadFactory的方法，它可将新创建的线程设置为已知的状态</span>
 * <span>创建并返回"非闭包"形式的Callable的方法，这样可将其用于需要Callable的执行方法中</span>
 *  
 * @author 八两俊 
 * @since
 */
public class TestExecutors {
	/**
	 * <p>创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。</p>
	 * @since
	 * @throws
	 */
	public void testNewFixedThreadPool() {
		ExecutorService executorService = Executors.newFixedThreadPool(5);// 相当于创建了5个核心线程，如果正在执行的任务达到了5个，再加入任务会被挂在在队列中等待
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("ExecutorService's Thread Running...");
			}
		});
	}
	
	/**
	 * <p>创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程，在需要时使用提供的 ThreadFactory创建新线程。</p>
	 * @since
	 * @throws
	 */
	public void testNewFixedThreadPoolWithThreadFactory() {
		ExecutorService executorService = Executors.newFixedThreadPool(5, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable paramRunnable) {// 我是来自ExecutorService.execute传入的Runnable
				Thread thread = new Thread(paramRunnable);
				return thread;
			}
		});
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("猜猜什么结果...");
			}
		});
	}
	
	/**
	 * <p>
	 * 创建一个使用单个 worker线程的Executor，以无界队列方式来运行该线程。（注意，如果因为在关闭前的执行期间出现失败而终止了此单个线程，那么如果需要，一个新线程将代替它执行后续的任务）。
	 * 可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的。与其他等效的newFixedThreadPool(1) 不同，可保证无需重新配置此方法所返回的执行程序即可使用其他的线程。
	 * </p>
	 * 
	 * @since
	 * @throws
	 */
	public void testNewSingleThreadExecutor() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();// 相当于创建了一个核心线程，如果当前线程处于执行状态，后加入的任务会被挂起在队列中
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("我这在被Executors.newSingleThreadExecutor()创建的单一线程执行...");
			}
		});
	}
	
	/**
	 * <p>
	 * 创建一个使用单个 worker线程的 Executor，以无界队列方式来运行该线程，并在需要时使用提供的 ThreadFactory创建新线程。
	 * 与其他等效的 newFixedThreadPool(1,threadFactory)不同，可保证无需重新配置此方法所返回的执行程序即可使用其他的线程。
	 * </p>
	 * 
	 * @since
	 * @throws
	 */
	public void testNewSingleThreadExecutorWithThreadFactory() {
		ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable paramRunnable) {
				return new Thread(paramRunnable);
			}
		});
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("testNewSingleThreadExecutorWithThreadFactory");
			}
		});
	}
	
	/**
	 * <p>
	 * 创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。对于执行很多短期异步任务的程序而言，这些线程池通常可提高程序性能。
	 * 调用execute将重用以前构造的线程（如果线程可用）。如果现有线程没有可用的，则创建一个新线程并添加到池中。
	 * 终止并从缓存中移除那些已有60秒钟未被使用的线程。因此，长时间保持空闲的线程池不会使用任何资源。
	 * 注意，可以使用 ThreadPoolExecutor构造方法创建具有类似属性但细节不同（例如超时参数）的线程池。
	 * </p>
	 * 
	 * @since
	 * @throws
	 */
	public void testNewCachedThreadPool() {
		ExecutorService executorService = Executors.newCachedThreadPool();// 核心线程数为0，最大线程数2147483647。来一个任务就创建个线程
		System.out.println("++++++++++++++++++++++++++++++");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("zzzzzzzzzzzzz");
			}
		});
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("aaaaaaaaaaa");
			}
		});
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("dddddddddd");
			}
		});
		try {
			Thread.sleep(3000);// 控制主线程结束之前执行Runnable
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>
	 * 创建一个单线程执行程序，它可安排在给定延迟后运行命令或者定期地执行。（注意，如果因为在关闭前的执行期间出现失败而终止了此单个线程，那么如果需要，一个新线程会代替它执行后续的任务）。
	 * 可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的。与其他等效的newScheduledThreadPool(1)不同，可保证无需重新配置此方法所返回的执行程序即可使用其他的线程。
	 * </p>
	 * 
	 * @since
	 * @throws
	 */
	public void testNewSingleThreadScheduledExecutor() {
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("testNewSingleThreadScheduledExecutor");
			}
		}, 3, TimeUnit.SECONDS);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。 </p>
	 * @since
	 * @throws
	 */
	public void testNewScheduledThreadPool() {
		// corePoolSize - 池中所保存的线程数，即使线程是空闲的也包括在内。 
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
		executorService.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("hi");
			}
		}, 2, TimeUnit.SECONDS);

		executorService.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("boys");
			}
		}, 3, TimeUnit.SECONDS);
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>
	 * 返回一个将所有已定义的 ExecutorService方法委托给指定执行程序的对象，但是使用强制转换可能无法访问其他方法。这提供了一种可安全地“冻结”配置并且不允许调整给定具体实现的方法。
	 * </p>
	 * 
	 * @since
	 * @throws
	 */
	public void testUnconfigurableExecutorService() {
		// 将一个ExecutorService委派给Executors.DelegatedExecutorService
		ExecutorService executorService = Executors.unconfigurableExecutorService(Executors.newSingleThreadExecutor());
	}

	/**
	 * <p>
	 * 返回一个将所有已定义的 ExecutorService方法委托给指定执行程序的对象，但是使用强制转换可能无法访问其他方法。这提供了一种可安全地“冻结”配置并且不允许调整给定具体实现的方法。
	 * </p>
	 * 
	 * @since
	 * @throws
	 */
	public void testUnconfigurableScheduledExecutorService() {
		ScheduledExecutorService executorService = Executors.unconfigurableScheduledExecutorService(Executors.newScheduledThreadPool(2));
	}

	/**
	 * <p>
	 * 返回用于创建新线程的默认线程工厂。此工厂创建同一ThreadGroup中 Executor使用的所有新线程。
	 * 如果有SecurityManager，则它使用 System.getSecurityManager()组来调用此defaultThreadFactory方法，其他情况则使用线程组。
	 * 每个新线程都作为非守护程序而创建，并且具有设置为Thread.NORM_PRIORITY中较小者的优先级以及线程组中允许的最大优先级。
	 * 新线程具有可通过 pool-N-thread-M 的Thread.getName()来访问的名称，其中  N 是此工厂的序列号，M 是此工厂所创建线程的序列号。
	 * </p>
	 * 
	 * @since
	 * @throws
	 */
	public void testDefaultThreadFactory() {
		ThreadFactory factory = Executors.defaultThreadFactory();
	}
	
	@Test
	public void testCallable() throws Exception{
		String s = "今天是个好日子";
		Callable<String> callable = Executors.callable(new Runnable() {
			@Override
			public void run() {
				System.out.println("zzzzzzzzzzzzzzzz");
			}
		}, s);
		s = callable.call();
		System.out.println("s is " + s);
	}
}
