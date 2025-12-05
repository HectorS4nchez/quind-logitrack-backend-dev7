package com.quind.domain.model;

import com.quind.domain.model.enums.PackageStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PackageModel {

    private String trackingId;
    private RecipientModel recipientModel;
    private DimensionsModel dimensionsModel;
    private double weight;
    private PackageStatus status;
    private List<LocationHistoryModel> locationHistoryModel;

    public PackageModel() {
    }

    public PackageModel(String trackingId, RecipientModel recipientModel, DimensionsModel dimensionsModel, double weight) {
        this.trackingId = trackingId;
        this.recipientModel = recipientModel;
        this.dimensionsModel = dimensionsModel;
        this.weight = weight;
        this.status = PackageStatus.PENDING;
        this.locationHistoryModel = new ArrayList<>();
    }

    public void addLocation(String city, String country, LocalDateTime timestamp) {
        LocationHistoryModel newLocation = new LocationHistoryModel(city, country, timestamp);
        this.locationHistoryModel.add(newLocation);
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

    public RecipientModel getRecipient() {
        return recipientModel;
    }

    public void setRecipient(RecipientModel recipientModel) {
        this.recipientModel = recipientModel;
    }

    public DimensionsModel getDimensions() {
        return dimensionsModel;
    }

    public void setDimensions(DimensionsModel dimensionsModel) {
        this.dimensionsModel = dimensionsModel;
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

    public List<LocationHistoryModel> getLocationHistory() {
        return locationHistoryModel;
    }

    public void setLocationHistory(List<LocationHistoryModel> locationHistoryModel) {
        this.locationHistoryModel = locationHistoryModel;
    }
}