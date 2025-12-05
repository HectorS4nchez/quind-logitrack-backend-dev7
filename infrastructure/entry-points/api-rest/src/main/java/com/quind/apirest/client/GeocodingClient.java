package com.quind.apirest.client;

import com.quind.apirest.controller.response.GeocodingResponse;
import com.quind.domain.exception.GeocodingException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingClient {

    private final RestTemplate restTemplate;
    private static final String GEOCODING_API_URL = "https://nominatim.openstreetmap.org/search";
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    public GeocodingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtiene las coordenadas geográficas de una ciudad y país
     * @param city Ciudad a buscar
     * @param country País a buscar
     * @return GeocodingResponse con las coordenadas
     * @throws GeocodingException si ocurre un error
     */
    public GeocodingResponse getCoordinates(String city, String country) {
        validateInput(city, country);

        String url = buildUrl(city, country);
        int attempt = 0;
        Exception lastException = null;

        while (attempt < MAX_RETRIES) {
            try {
                GeocodingResponse[] responses = restTemplate.getForObject(url, GeocodingResponse[].class);

                if (responses != null && responses.length > 0) {
                    return responses[0];
                } else {
                    throw new GeocodingException("No results found for city: " + city + ", country: " + country);
                }

            } catch (HttpClientErrorException e) {
                return handleClientError(e, city, country);

            } catch (HttpServerErrorException e) {
                lastException = e;
                attempt++;
                if (attempt < MAX_RETRIES) {
                    waitBeforeRetry(attempt);
                }

            } catch (ResourceAccessException e) {
                lastException = e;
                attempt++;
                if (attempt < MAX_RETRIES) {
                    waitBeforeRetry(attempt);
                }

            } catch (Exception e) {
                throw new GeocodingException("Unexpected error: " + e.getMessage(), e);
            }
        }

        throw new GeocodingException(
                "Failed after " + MAX_RETRIES + " attempts: " +
                        (lastException != null ? lastException.getMessage() : "Unknown error")
        );
    }

    /**
     * Valida si una ubicación existe en el servicio de geocodificación
     * @param city Ciudad a validar
     * @param country País a validar
     * @return true si la ubicación existe, false en caso contrario
     */
    public boolean validateLocation(String city, String country) {
        try {
            getCoordinates(city, country);
            return true;
        } catch (GeocodingException e) {
            return false;
        }
    }

    /**
     * Obtiene información detallada de una ubicación
     * @param city Ciudad a buscar
     * @param country País a buscar
     * @return String con el nombre completo de la ubicación
     */
    public String getLocationDisplayName(String city, String country) {
        GeocodingResponse response = getCoordinates(city, country);
        return response.getDisplayName();
    }

    private void validateInput(String city, String country) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
    }

    private String buildUrl(String city, String country) {
        return String.format("%s?city=%s&country=%s&format=json&limit=1",
                GEOCODING_API_URL,
                city.trim(),
                country.trim());
    }

    private GeocodingResponse handleClientError(HttpClientErrorException e, String city, String country) {
        if (e.getStatusCode().value() == 404) {
            throw new GeocodingException("Location not found: " + city + ", " + country);
        } else if (e.getStatusCode().is4xxClientError()) {
            throw new GeocodingException("Client error: " + e.getMessage());
        }
        throw new GeocodingException("HTTP error: " + e.getMessage(), e);
    }

    private void waitBeforeRetry(int attempt) {
        try {
            Thread.sleep(RETRY_DELAY_MS * attempt); // Exponential backoff
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new GeocodingException("Request interrupted");
        }
    }
}