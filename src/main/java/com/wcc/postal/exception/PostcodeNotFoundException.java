package com.wcc.postal.exception;

public class PostcodeNotFoundException extends RuntimeException {
    public PostcodeNotFoundException(String message) {
        super(message);
    }
}
