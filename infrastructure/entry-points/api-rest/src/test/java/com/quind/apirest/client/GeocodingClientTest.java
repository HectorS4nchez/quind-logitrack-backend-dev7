package com.quind.apirest.client;

import com.quind.apirest.controller.client.GeocodingClient;
import com.quind.apirest.controller.response.GeocodingResponse;
import com.quind.domain.exception.GeocodingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeocodingClientTest {

    @Mock
    private RestTemplate restTemplate;

    private GeocodingClient geocodingClient;

    @BeforeEach
    void setUp() {
        geocodingClient = new GeocodingClient(restTemplate);
    }

    @Test
    void testGetCoordinatesReturnsValidResponse() {
        GeocodingResponse[] responses = new GeocodingResponse[]{
                new GeocodingResponse("4.7110", "-74.0721", "Bogota, Colombia")
        };

        when(restTemplate.getForObject(anyString(), any())).thenReturn(responses);

        GeocodingResponse result = geocodingClient.getCoordinates("Bogota", "Colombia");

        assertNotNull(result);
        assertEquals("4.7110", result.getLatitude());
        assertEquals("-74.0721", result.getLongitude());
        assertEquals("Bogota, Colombia", result.getDisplayName());
        verify(restTemplate, times(1)).getForObject(anyString(), any());
    }

    @Test
    void testGetCoordinatesThrowsExceptionWhenNoResults() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(new GeocodingResponse[0]);

        GeocodingException exception = assertThrows(GeocodingException.class, () ->
                geocodingClient.getCoordinates("InvalidCity", "InvalidCountry")
        );

        assertTrue(exception.getMessage().contains("No results found"));
    }

    @Test
    void testGetCoordinatesHandles404Error() {
        when(restTemplate.getForObject(anyString(), any()))
                .thenThrow(HttpClientErrorException.create(
                        HttpStatus.NOT_FOUND,
                        "Not Found",
                        null,
                        null,
                        null
                ));

        GeocodingException exception = assertThrows(GeocodingException.class, () ->
                geocodingClient.getCoordinates("Unknown", "Unknown")
        );

        assertTrue(exception.getMessage().contains("Location not found"));
    }

    @Test
    void testGetCoordinatesHandles4xxError() {
        when(restTemplate.getForObject(anyString(), any()))
                .thenThrow(HttpClientErrorException.create(
                        HttpStatus.BAD_REQUEST,
                        "Bad Request",
                        null,
                        null,
                        null
                ));

        GeocodingException exception = assertThrows(GeocodingException.class, () ->
                geocodingClient.getCoordinates("Test", "Test")
        );

        assertTrue(exception.getMessage().contains("Client error"));
    }

    @Test
    void testGetCoordinatesRetriesOnServerError() {
        GeocodingResponse[] responses = new GeocodingResponse[]{
                new GeocodingResponse("4.7110", "-74.0721", "Bogota, Colombia")
        };

        when(restTemplate.getForObject(anyString(), any()))
                .thenThrow(HttpServerErrorException.create(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Server Error",
                        null,
                        null,
                        null
                ))
                .thenReturn(responses);

        GeocodingResponse result = geocodingClient.getCoordinates("Bogota", "Colombia");

        assertNotNull(result);
        assertEquals("4.7110", result.getLatitude());
        verify(restTemplate, times(2)).getForObject(anyString(), any());
    }

    @Test
    void testGetCoordinatesFailsAfterMaxRetries() {
        when(restTemplate.getForObject(anyString(), any()))
                .thenThrow(HttpServerErrorException.create(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Server Error",
                        null,
                        null,
                        null
                ));

        GeocodingException exception = assertThrows(GeocodingException.class, () ->
                geocodingClient.getCoordinates("Bogota", "Colombia")
        );

        assertTrue(exception.getMessage().contains("Failed after 3 attempts"));
        verify(restTemplate, times(3)).getForObject(anyString(), any());
    }

    @Test
    void testGetCoordinatesHandlesTimeout() {
        when(restTemplate.getForObject(anyString(), any()))
                .thenThrow(new ResourceAccessException("Timeout"));

        GeocodingException exception = assertThrows(GeocodingException.class, () ->
                geocodingClient.getCoordinates("Bogota", "Colombia")
        );

        assertTrue(exception.getMessage().contains("Failed after 3 attempts"));
        verify(restTemplate, times(3)).getForObject(anyString(), any());
    }

    @Test
    void testGetCoordinatesWithNullCityThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                geocodingClient.getCoordinates(null, "Colombia")
        );

        assertTrue(exception.getMessage().contains("City cannot be null or empty"));
    }

    @Test
    void testGetCoordinatesWithEmptyCityThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                geocodingClient.getCoordinates("", "Colombia")
        );

        assertTrue(exception.getMessage().contains("City cannot be null or empty"));
    }

    @Test
    void testGetCoordinatesWithNullCountryThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                geocodingClient.getCoordinates("Bogota", null)
        );

        assertTrue(exception.getMessage().contains("Country cannot be null or empty"));
    }

    @Test
    void testGetCoordinatesWithEmptyCountryThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                geocodingClient.getCoordinates("Bogota", "")
        );

        assertTrue(exception.getMessage().contains("Country cannot be null or empty"));
    }

    @Test
    void testValidateLocationReturnsTrue() {
        GeocodingResponse[] responses = new GeocodingResponse[]{
                new GeocodingResponse("4.7110", "-74.0721", "Bogota, Colombia")
        };

        when(restTemplate.getForObject(anyString(), any())).thenReturn(responses);

        boolean result = geocodingClient.validateLocation("Bogota", "Colombia");

        assertTrue(result);
    }

    @Test
    void testValidateLocationReturnsFalse() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(new GeocodingResponse[0]);

        boolean result = geocodingClient.validateLocation("InvalidCity", "InvalidCountry");

        assertFalse(result);
    }

    @Test
    void testGetLocationDisplayName() {
        GeocodingResponse[] responses = new GeocodingResponse[]{
                new GeocodingResponse("4.7110", "-74.0721", "Bogota, Bogota D.C., Colombia")
        };

        when(restTemplate.getForObject(anyString(), any())).thenReturn(responses);

        String displayName = geocodingClient.getLocationDisplayName("Bogota", "Colombia");

        assertEquals("Bogota, Bogota D.C., Colombia", displayName);
    }

    @Test
    void testGetCoordinatesTrimsWhitespace() {
        GeocodingResponse[] responses = new GeocodingResponse[]{
                new GeocodingResponse("4.7110", "-74.0721", "Bogota, Colombia")
        };

        when(restTemplate.getForObject(anyString(), any())).thenReturn(responses);

        GeocodingResponse result = geocodingClient.getCoordinates("  Bogota  ", "  Colombia  ");

        assertNotNull(result);
        verify(restTemplate, times(1)).getForObject(anyString(), any());
    }
}