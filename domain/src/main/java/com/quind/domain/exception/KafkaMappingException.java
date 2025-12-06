package com.quind.domain.exception;

public class KafkaMappingException extends RuntimeException {

    public KafkaMappingException(String message) {
        super(message);
    }

    public KafkaMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}