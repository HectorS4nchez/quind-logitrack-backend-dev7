package com.quind.domain.model;

import com.quind.domain.model.enums.PackageStatus;
import com.quind.domain.validations.PackageStateValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackageModel {

    private String trackingId;
    private RecipientModel recipient;
    private DimensionsModel dimensions;
    private double weight;
    private PackageStatus status;
    private List<LocationHistoryModel> locationHistory;

    private PackageModel(Builder builder) {
        this.trackingId = builder.trackingId;
        this.recipient = builder.recipient;
        this.dimensions = builder.dimensions;
        this.weight = builder.weight;
        this.status = builder.status;
        this.locationHistory = new ArrayList<>(builder.locationHistory);
    }

    public PackageModel() {
        this.locationHistory = new ArrayList<>();
        this.status = PackageStatus.CREATED;
    }

    public PackageModel(String trackingId, RecipientModel recipient, DimensionsModel dimensions, double weight) {
        validateTrackingId(trackingId);
        validateRecipient(recipient);
        validateDimensions(dimensions);
        validateWeight(weight);

        this.trackingId = trackingId;
        this.recipient = recipient;
        this.dimensions = dimensions;
        this.weight = weight;
        this.status = PackageStatus.CREATED;
        this.locationHistory = new ArrayList<>();
    }

    public void addLocation(String city, String country, LocalDateTime timestamp) {
        validateCity(city);
        validateCountry(country);
        validateTimestamp(timestamp);

        LocationHistoryModel newLocation = new LocationHistoryModel(city, country, timestamp);
        this.locationHistory.add(newLocation);
    }

    public void addLocation(String city, String country) {
        addLocation(city, country, LocalDateTime.now());
    }

    public void changeStatus(PackageStatus newStatus) {
        PackageStateValidator.validateTransition(this.status, newStatus);
        this.status = newStatus;
    }

    public List<LocationHistoryModel> getLocationHistory() {
        return Collections.unmodifiableList(locationHistory);
    }

    private void validateTrackingId(String trackingId) {
        if (trackingId == null || trackingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Tracking ID cannot be null or empty");
        }
    }

    private void validateRecipient(RecipientModel recipient) {
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient cannot be null");
        }
    }

    private void validateDimensions(DimensionsModel dimensions) {
        if (dimensions == null) {
            throw new IllegalArgumentException("Dimensions cannot be null");
        }
    }

    private void validateWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
    }

    private void validateCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
    }

    private void validateCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
    }

    private void validateTimestamp(LocalDateTime timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
    }

    // Getters and Setters
    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        validateTrackingId(trackingId);
        this.trackingId = trackingId;
    }

    public RecipientModel getRecipient() {
        return recipient;
    }

    public void setRecipient(RecipientModel recipient) {
        validateRecipient(recipient);
        this.recipient = recipient;
    }

    public DimensionsModel getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionsModel dimensions) {
        validateDimensions(dimensions);
        this.dimensions = dimensions;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        validateWeight(weight);
        this.weight = weight;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public void setLocationHistory(List<LocationHistoryModel> locationHistory) {
        this.locationHistory = locationHistory;
    }

    // Builder Pattern
    public static class Builder {
        private String trackingId;
        private RecipientModel recipient;
        private DimensionsModel dimensions;
        private double weight;
        private PackageStatus status = PackageStatus.CREATED;
        private List<LocationHistoryModel> locationHistory = new ArrayList<>();

        public Builder trackingId(String trackingId) {
            this.trackingId = trackingId;
            return this;
        }

        public Builder recipient(RecipientModel recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder dimensions(DimensionsModel dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public Builder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder status(PackageStatus status) {
            this.status = status;
            return this;
        }

        public Builder locationHistory(List<LocationHistoryModel> locationHistory) {
            this.locationHistory = new ArrayList<>(locationHistory);
            return this;
        }

        public Builder addLocation(LocationHistoryModel location) {
            this.locationHistory.add(location);
            return this;
        }

        public PackageModel build() {
            if (trackingId == null || trackingId.trim().isEmpty()) {
                throw new IllegalArgumentException("Tracking ID cannot be null or empty");
            }
            if (recipient == null) {
                throw new IllegalArgumentException("Recipient cannot be null");
            }
            if (dimensions == null) {
                throw new IllegalArgumentException("Dimensions cannot be null");
            }
            if (weight <= 0) {
                throw new IllegalArgumentException("Weight must be greater than zero");
            }

            return new PackageModel(this);
        }
    }
}