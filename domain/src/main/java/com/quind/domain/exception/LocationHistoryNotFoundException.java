package com.quind.domain.exception;

public class LocationHistoryNotFoundException extends RuntimeException {
    private final Long locationHistoryId;

    public LocationHistoryNotFoundException(Long locationHistoryId) {
        super(String.format("Location history not found with ID: %d", locationHistoryId));
        this.locationHistoryId = locationHistoryId;
    }

    public Long getLocationHistoryId() {
        return locationHistoryId;
    }
}
