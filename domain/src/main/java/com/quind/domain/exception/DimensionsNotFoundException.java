package com.quind.domain.exception;

public class DimensionsNotFoundException extends RuntimeException {
    private final Long dimensionsId;

    public DimensionsNotFoundException(Long dimensionsId) {
        super(String.format("Dimensions not found with ID: %d", dimensionsId));
        this.dimensionsId = dimensionsId;
    }

    public Long getDimensionsId() {
        return dimensionsId;
    }
}
