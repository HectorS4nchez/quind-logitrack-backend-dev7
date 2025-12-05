package com.quind.domain.model;

import com.quind.domain.constants.PackageStatus;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Package {


    private String trackingId;
    private Recipient recipient;
    private Dimensions dimensions;
    private double weight;
    private PackageStatus status;
    private List<LocationHistory> locationHistory;

    public Package() {
    }

    public Package(String trackingId, Recipient recipient, Dimensions dimensions, double weight) {
        this.trackingId = trackingId;
        this.recipient = recipient;
        this.dimensions = dimensions;
        this.weight = weight;
        this.status = PackageStatus.PENDING;
        this.locationHistory = new ArrayList<>();
    }

    // Métodos de comportamiento
    public void addLocation(String city, String country, LocalDateTime timestamp) {
        LocationHistory newLocation = new LocationHistory(city, country, timestamp);
        this.locationHistory.add(newLocation);
    }

    public void addLocation(String city, String country) {
        addLocation(city, country, LocalDateTime.now());
    }

    public void changeStatus(PackageStatus newStatus) {
        this.status = newStatus;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
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

    public List<LocationHistory> getLocationHistory() {
        return locationHistory;
    }

    public void setLocationHistory(List<LocationHistory> locationHistory) {
        this.locationHistory = locationHistory;
    }
}