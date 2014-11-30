package com.example.concurrent.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStatck<E> {
	static class Node<E> {
		final E item;
		Node<E> next;

		public Node(E item) {
			this.item = item;
		}
	}

	AtomicReference<ConcurrentStatck.Node<E>> head = new AtomicReference<ConcurrentStatck.Node<E>>();

	public void push(E item) {
		Node<E> newHead = new Node<E>(item);
		Node<E> oldHead;
		do {
			oldHead = head.get();
			newHead.next = oldHead;
		} while (!head.compareAndSet(oldHead, newHead));
	}

	public E pop() {
		Node<E> oldHead;// 旧头
		Node<E> newHead;// 心头
		do {
			oldHead = head.get();// 获取旧头
			if (null == oldHead)
				return null;
			newHead = oldHead.next;// 将下一个节点作为一个心头
		} while (!head.compareAndSet(oldHead, newHead));
		return oldHead.item;
	}
}
