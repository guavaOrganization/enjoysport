package com.example.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

public class BookFacadeTest {
	public static void main(String[] args) {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\cglib\\"); 
		BookFacadeProxy proxy = new BookFacadeProxy();
		BookFacadeImpl impl = (BookFacadeImpl)proxy.getInstance(new BookFacadeImpl());
		impl.addBook();
	}
}
