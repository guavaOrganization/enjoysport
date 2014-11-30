package com.example.thread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskWithResult implements Callable<String> {
	private int id;

	public TaskWithResult(int id) {
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		return "result of TaskWithResult " + id;
	}
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		ArrayList<Future<String>> list = new ArrayList<Future<String>>();
		for(int i = 0 ; i < 10;i++){
			list.add(executorService.submit(new TaskWithResult(i)));
		}
		
		for (int i = 0; i < list.size(); i++) {
			try {// Future的get方法会阻塞直到任务完成
				System.out.println(list.get(i).get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} finally {
				executorService.shutdown();
			}
		}
	}
}
