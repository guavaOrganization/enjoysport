package com.guava.codeautogenerator.core.exception;

@SuppressWarnings("serial")
public class CodeAutoGeneratorException extends RuntimeException {
	public CodeAutoGeneratorException(String msg) {
		super(msg);
	}

	public CodeAutoGeneratorException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
