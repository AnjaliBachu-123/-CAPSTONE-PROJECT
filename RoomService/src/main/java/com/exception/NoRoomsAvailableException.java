package com.exception;

public class NoRoomsAvailableException extends RuntimeException {
	 private static final long serialVersionUID = 1L;
    public NoRoomsAvailableException(String message) {
        super(message);
    }
}
