package com.example.concurrent.juc.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class TestAtomicReferenceFieldUpdater {
	public static void main(String[] args) {
		AtomicReferenceFieldUpdater<AtomicUser, String> fieldUpdater = AtomicReferenceFieldUpdater
				.newUpdater(AtomicUser.class, String.class, "hobby");
		AtomicUser user = new AtomicUser(0, "八两俊");
		fieldUpdater.set(user, "八两俊");
		System.out.println(user.hobby);
	}
}
