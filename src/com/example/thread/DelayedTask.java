package com.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedTask implements Runnable, Delayed {
	private static int counter = 0;
	private final int id = counter++;
	private final int delta;
	private final long trigger;

	protected static List<DelayedTask> sequence = new ArrayList<DelayedTask>();

	public DelayedTask(int delayInMilliseconds) {
		delta = delayInMilliseconds;
		trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
		sequence.add(this);
	}

	@Override
	public void run() {
		System.out.println(this + "  ");
	}
	
	@Override
	public String toString() {
		return String.format("[%1$-4d]", delta) + " Task " + id;
	}
	
	public String summary(){
		return "(" + id + ":" + delta + ")";
	}

	@Override
	public int compareTo(Delayed delayed) {
		DelayedTask that = (DelayedTask) delayed;
		if (trigger < that.trigger)
			return -1;
		if (trigger > that.trigger)
			return 1;
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {// 可以用来告知延迟到期有多长时间,或者延迟在多长时间之前到期
		return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);// System.nanoTime()产生的时间是以纳秒为单位的
	}
}
