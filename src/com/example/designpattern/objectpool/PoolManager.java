package com.example.designpattern.objectpool;

import java.util.ArrayList;

public class PoolManager {// 连接池管理类
	private static class PoolItem {
		boolean inUse = false;
		Object item;

		public PoolItem(Object item) {
			this.item = item;
		}
	}

	private ArrayList items = new ArrayList();

	public void add(Object item) {
		items.add(new PoolItem(item));
	}

	static class EmptyPoolException extends Exception {
	}

	public Object get() throws EmptyPoolException {
		for (int i = 0; i < items.size(); i++) {
			PoolItem poolItem = (PoolItem) items.get(i);
			if (!poolItem.inUse) {
				poolItem.inUse = true;
				return poolItem;
			}
		}
		throw new EmptyPoolException();
	}

	public void release(Object item) {
		for (int i = 0; i < items.size(); i++) {
			PoolItem poolItem = (PoolItem) items.get(i);
			if (poolItem.item == item) {
				poolItem.inUse = false;
				return;
			}
		}
		throw new RuntimeException(item + " Not Found ");
	}
}
