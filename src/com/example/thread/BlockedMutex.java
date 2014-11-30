package com.example.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockedMutex {
	private Lock lock = new ReentrantLock();

	public BlockedMutex() {
		lock.lock();
	}

	public void f() {
		try {
			/**
			 * <p>如果当前线程未被中断，则获取锁。</p>
			 * <p>如果锁可用，则获取锁，并立即返回。</p>
			 * <p>如果锁不可用，出于线程调度目的，将禁用当前线程，并且在发生以下两种情况之一以前该线程将一直处于休眠状态：
			 * 	<span>锁由当前线程获得</span>
			 * 	<span>其他某个线程中断当前线程，并且支持对锁获取的中断。</span>
			 * </p>
			 * 
			 * <p>如果当前线程：
			 *  <span>在进入此方法时已经设置了该线程的中断状态；或者</span>
			 *	<span>在获取锁时被中断，并且支持对锁获取的中断，</span>
			 * 	则将抛出 InterruptedException，并清除当前线程的已中断状态。
			 * </p>
			 * 
			 * <p>实现注意事项
			 *  在某些实现中可能无法中断锁获取，即使可能，该操作的开销也很大。程序员应该知道可能会发生这种情况。在这种情况下，该实现应该对此进行记录。
			 *  相对于普通方法返回而言，实现可能更喜欢响应某个中断。
			 *  Lock 实现可能可以检测锁的错误用法，例如，某个调用可能导致死锁，在特定的环境中可能抛出（未经检查的）异常。该 Lock 实现必须对环境和异常类型进行记录。
			 *  </p>
			 *  抛出：InterruptedException - 如果在获取锁时，当前线程被中断（并且支持对锁获取的中断）。
			 */
			lock.lockInterruptibly();
			System.out.println("lock acquired in f()");
		} catch (InterruptedException e) {
			System.out.println("Interrupted from lock acquisition in f()");
		}
	}
}
