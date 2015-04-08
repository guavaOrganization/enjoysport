package com.example.cglib;

public class BookFacadeTest {
	public static void main(String[] args) {
		BookFacadeProxy proxy = new BookFacadeProxy();
		BookFacadeImpl impl = (BookFacadeImpl)proxy.getInstance(new BookFacadeImpl());
		impl.addBook();
	}
}
