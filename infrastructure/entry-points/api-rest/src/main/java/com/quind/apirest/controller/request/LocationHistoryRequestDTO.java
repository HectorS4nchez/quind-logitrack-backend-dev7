package com.quind.apirest.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class LocationHistoryRequestDTO {

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    public LocationHistoryRequestDTO() {
    }

    public LocationHistoryRequestDTO(String city, String country, LocalDateTime timestamp) {
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
