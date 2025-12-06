package com.quind.apirest.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PackageRequestDTO {

    @NotBlank(message = "Tracking ID is required")
    private String trackingId;

    @NotNull(message = "Recipient is required")
    @Valid
    private RecipientRequestDTO recipient;

    @NotNull(message = "Dimensions are required")
    @Valid
    private DimensionsRequestDTO dimensions;

    @Positive(message = "Weight must be greater than zero")
    private double weight;

    public PackageRequestDTO() {
    }

    public PackageRequestDTO(String trackingId, RecipientRequestDTO recipient,
                             DimensionsRequestDTO dimensions, double weight) {
        this.trackingId = trackingId;
        this.recipient = recipient;
        this.dimensions = dimensions;
        this.weight = weight;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public RecipientRequestDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(RecipientRequestDTO recipient) {
        this.recipient = recipient;
    }

    public DimensionsRequestDTO getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionsRequestDTO dimensions) {
        this.dimensions = dimensions;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
