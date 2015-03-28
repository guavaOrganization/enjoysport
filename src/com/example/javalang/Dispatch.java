package com.example.javalang;

public class Dispatch {
	static class QQ {
	}

	static class _360 {
	}

	public static class Father {
		public void hardChoice(_360 arg) {
			System.out.println("father choice 360");
		}

		public void hardChoice(QQ arg) {
			System.out.println("father choice QQ");
		}
	}

	public static class Son extends Father {
		public void hardChoice(_360 arg) {
			System.out.println("son choice 360");
		}

		public void hardChoice(QQ arg) {
			System.out.println("son choice QQ");
		}
	}

	public static void main(String[] args) {
		Father father = new Father();
		Father son = new Son();

		father.hardChoice(new _360());
		son.hardChoice(new QQ());
	}
}
