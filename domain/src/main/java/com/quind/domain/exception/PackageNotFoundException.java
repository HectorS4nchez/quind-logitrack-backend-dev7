package com.quind.domain.exception;

public class PackageNotFoundException extends RuntimeException {
    private final String trackingId;

    public PackageNotFoundException(String trackingId) {
        super(String.format("Package not found with tracking ID: %s", trackingId));
        this.trackingId = trackingId;
    }

    public String getTrackingId() {
        return trackingId;
    }
}
