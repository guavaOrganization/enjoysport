package com.example.thread;

public class Pair {// Not thread-safe
	private int x, y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Pair() {
		this(0, 0);
	}

	public int getX() {
		return x;
	}

	public void incrementX() {
		x++;
	}

	public int getY() {
		return y;
	}

	public void incrementY() {
		y++;
	}

	@Override
	public String toString() {
		return "x:" + x + ",y:" + y;
	}

	public class PairValuesNotEqualException extends RuntimeException {
		private static final long serialVersionUID = 356966514225663383L;

		public PairValuesNotEqualException() {
			super("Pair values not equal：" + Pair.this);
		}
	}

	public void checkState() {
		if (x != y) {
			throw new PairValuesNotEqualException();
		}
	}
}
