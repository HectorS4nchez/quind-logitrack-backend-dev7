package com.quind.domain.model;

import java.time.LocalDateTime;

public class LocationHistoryModel {
    private String city;
    private String country;
    private LocalDateTime timestamp;

    public LocationHistoryModel() {
    }

    public LocationHistoryModel(String city, String country, LocalDateTime timestamp) {
        this.city = city;
        this.country = country;
        this.timestamp = timestamp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
