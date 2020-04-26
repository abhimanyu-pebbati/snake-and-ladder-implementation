package com.snl.application.snakenladder.exceptionhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BoardLoopException extends AbstractException {
	public BoardLoopException() {
		super();
	}

	public BoardLoopException(String message, Throwable cause) {
		super(message, cause);
	}

	public BoardLoopException(String message) {
		super(message);
	}

	public BoardLoopException(Throwable cause) {
		super(cause);
	}
}
