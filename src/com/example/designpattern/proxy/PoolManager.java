package com.example.designpattern.proxy;

import java.util.ArrayList;

public class PoolManager {
	private static class PoolItem {
		boolean inUse = false;
		Object item;

		public PoolItem(Object item) {
			this.item = item;
		}
	}

	public class ReleaseableReference {
		private PoolItem reference;
		private boolean released = false;

		public ReleaseableReference(PoolItem reference) {
			this.reference = reference;
		}
		
		public Object getReference(){
			if(released){
				throw new RuntimeException("Tried to use reference after it was released");
			}
			return reference.item;
		}
		
		public void release() {
			this.released = true;
			this.reference.inUse = false;
		}
	}
	
	
	private ArrayList items = new ArrayList();
	
	public void add(Object item) {
		items.add(new PoolItem(item));
	}
	
	public static class EmptyPoolItem{}
	
	public ReleaseableReference get() {
		for (int i = 0; i < items.size(); i++) {
			PoolItem item = (PoolItem) items.get(i);
			if (!item.inUse) {
				item.inUse = true;
				return new ReleaseableReference(item);
			}
		}
		return null;
	}
}
