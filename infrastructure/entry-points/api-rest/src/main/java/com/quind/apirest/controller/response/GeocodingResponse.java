package com.quind.apirest.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeocodingResponse {

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lon")
    private String longitude;

    @JsonProperty("display_name")
    private String displayName;

    public GeocodingResponse() {
    }

    public GeocodingResponse(String latitude, String longitude, String displayName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "GeocodingResponse{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}