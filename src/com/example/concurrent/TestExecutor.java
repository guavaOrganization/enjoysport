package com.example.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.example.concurrent.executor.DirectExecutor;
import com.example.concurrent.executor.SerialExecutor;
import com.example.concurrent.executor.ThreadPerTaskExecutor;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>执行已提交的 Runnable 任务的对象。此接口提供一种将任务提交与每个任务将如何运行的机制（包括线程使用的细节、调度等）分离开来的方法。通常使用 Executor 而不是显式地创建线程。</p>
 * 
 * @author 八两俊 
 * @since
 */
public class TestExecutor {
	public void testDirectExecutor() {
		Executor executor = new DirectExecutor();
		executor.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("testDirectExecutor");
			}
		});
	}
	
	public void testThreadPerTaskExecutor() {
		Executor executor = new ThreadPerTaskExecutor();
		executor.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("testThreadPerTaskExecutor");
			}
		});
	}
	
	@Test
	public void testSerialExecutor() {
		SerialExecutor executor = new SerialExecutor(Executors.newSingleThreadExecutor());
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("runnable[1]...........");
			}
		});
		
		executor.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("runnable[2]...........");
			}
		});
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
