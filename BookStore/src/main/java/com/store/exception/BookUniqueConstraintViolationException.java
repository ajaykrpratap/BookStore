package com.store.exception;

public class BookUniqueConstraintViolationException extends RuntimeException {

	private static final long serialVersionUID = -5846543690941349765L;

	public BookUniqueConstraintViolationException(String exception) {
		super(exception);
	}
}
