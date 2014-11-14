package com.guava.codeautogenerator.exception;

@SuppressWarnings("serial")
public class CodeAutoGeneratorException extends RuntimeException {
	public CodeAutoGeneratorException(String msg) {
		super(msg);
	}

	public CodeAutoGeneratorException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
