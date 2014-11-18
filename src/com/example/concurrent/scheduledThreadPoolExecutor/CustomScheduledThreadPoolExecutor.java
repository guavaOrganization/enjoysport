package com.example.concurrent.scheduledThreadPoolExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class CustomScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

	public CustomScheduledThreadPoolExecutor(int paramInt) {
		super(paramInt);
	}

	@Override
	protected <V> RunnableScheduledFuture<V> decorateTask(
			Callable<V> paramCallable,
			RunnableScheduledFuture<V> paramRunnableScheduledFuture) {
		return super.decorateTask(paramCallable, paramRunnableScheduledFuture);
	}
	
	@Override
	protected <V> RunnableScheduledFuture<V> decorateTask(Runnable paramRunnable, RunnableScheduledFuture<V> paramRunnableScheduledFuture) {
		return super.decorateTask(paramRunnable, paramRunnableScheduledFuture);
	}
}
