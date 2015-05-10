package com.example.concurrent.juc.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class TestAtomicMarkableReference {
	public static void main(String[] args) {
		AtomicUser atomicUser = new AtomicUser(0, "陈俊");
		AtomicMarkableReference<AtomicUser> reference = new AtomicMarkableReference<AtomicUser>(atomicUser, true);
		
		System.out.println(reference.getReference());
		System.out.println(reference.isMarked());
		
		reference.set(new AtomicUser(1, "吴丽"), false);
		
		System.out.println(reference.getReference());
		
	}
}
