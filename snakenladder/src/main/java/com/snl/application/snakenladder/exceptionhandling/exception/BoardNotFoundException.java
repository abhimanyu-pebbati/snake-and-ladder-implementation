package com.snl.application.snakenladder.exceptionhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BoardNotFoundException extends EntityNotFoundException {
	public BoardNotFoundException() {
		super();
	}

	public BoardNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public BoardNotFoundException(String message) {
		super(message);
	}

	public BoardNotFoundException(Throwable cause) {
		super(cause);
	}
}
