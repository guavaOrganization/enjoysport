package com.example.thread;

public class Blocked implements Runnable {
	BlockedMutex blockedMutex = new BlockedMutex();

	@Override
	public void run() {
		System.out.println("Waiting for f() in BlockedMutex");
		blockedMutex.f();// 在未获得锁之前，一直会在这里被阻塞
		System.out.println("Broken out of blocked call");
	}
}
