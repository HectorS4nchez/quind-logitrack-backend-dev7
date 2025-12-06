package com.quind.apirest.controller.response;

import com.quind.domain.model.enums.PackageStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para respuestas de paquetes. [web:21]
 */
public class PackageResponseDTO {

    private String trackingId;
    private RecipientResponseDTO recipient;
    private DimensionsResponseDTO dimensions;
    private double weight;
    private PackageStatus status;
    private List<LocationHistoryResponseDTO> locationHistory;

    public PackageResponseDTO() {
        this.locationHistory = new ArrayList<>();
    }

    public PackageResponseDTO(String trackingId, RecipientResponseDTO recipient,
                              DimensionsResponseDTO dimensions, double weight,
                              PackageStatus status, List<LocationHistoryResponseDTO> locationHistory) {
        this.trackingId = trackingId;
        this.recipient = recipient;
        this.dimensions = dimensions;
        this.weight = weight;
        this.status = status;
        this.locationHistory = locationHistory != null ? locationHistory : new ArrayList<>();
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public RecipientResponseDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(RecipientResponseDTO recipient) {
        this.recipient = recipient;
    }

    public DimensionsResponseDTO getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionsResponseDTO dimensions) {
        this.dimensions = dimensions;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public List<LocationHistoryResponseDTO> getLocationHistory() {
        return locationHistory;
    }

    public void setLocationHistory(List<LocationHistoryResponseDTO> locationHistory) {
        this.locationHistory = locationHistory;
    }
}
