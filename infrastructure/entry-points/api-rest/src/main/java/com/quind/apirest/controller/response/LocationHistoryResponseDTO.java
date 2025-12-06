package com.quind.apirest.controller.response;

import java.time.LocalDateTime;

/**
 * DTO para respuestas de historial de ubicación. [web:21]
 */
public class LocationHistoryResponseDTO {

    private Long id;
    private String city;
    private String country;
    private LocalDateTime timestamp;

    public LocationHistoryResponseDTO() {
    }

    public LocationHistoryResponseDTO(Long id, String city, String country, LocalDateTime timestamp) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
