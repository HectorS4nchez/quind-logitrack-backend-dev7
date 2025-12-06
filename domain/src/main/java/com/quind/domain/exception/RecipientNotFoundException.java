package com.quind.domain.exception;

public class RecipientNotFoundException extends RuntimeException {
    private final Long recipientId;

    public RecipientNotFoundException(Long recipientId) {
        super(String.format("Recipient not found with ID: %d", recipientId));
        this.recipientId = recipientId;
    }

    public Long getRecipientId() {
        return recipientId;
    }
}


