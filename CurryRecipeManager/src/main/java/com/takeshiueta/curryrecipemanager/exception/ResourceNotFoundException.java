package com.takeshiueta.curryrecipemanager.exception;

/* 独自例外発生クラス**/
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String message) {
		super(message);
	}
}