package com.quind.domain.exception;

public class DuplicateTrackingIdException extends RuntimeException {
    private final String trackingId;

    public DuplicateTrackingIdException(String trackingId) {
        super(String.format("Package already exists with tracking ID: %s", trackingId));
        this.trackingId = trackingId;
    }

    public String getTrackingId() {
        return trackingId;
    }
}
