package com.quind.domain.usecase;

import com.quind.domain.model.LocationHistoryModel;
import com.quind.domain.port.repository.LocationHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationHistoryUseCaseTest {

    @Mock
    private LocationHistoryRepository locationHistoryRepository;

    private LocationHistoryUseCase locationHistoryUseCase;
    private LocationHistoryModel testLocationHistory;
    private LocalDateTime testTimestamp;

    @BeforeEach
    void setUp() {
        locationHistoryUseCase = new LocationHistoryUseCase(locationHistoryRepository);
        testTimestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        testLocationHistory = new LocationHistoryModel("Bogota", "Colombia", testTimestamp);
    }

    @Test
    void testCreateLocationHistory() {
        when(locationHistoryRepository.save(any(LocationHistoryModel.class))).thenReturn(testLocationHistory);

        LocationHistoryModel result = locationHistoryUseCase.createLocationHistory(testLocationHistory);

        assertNotNull(result);
        assertEquals("Bogota", result.getCity());
        assertEquals("Colombia", result.getCountry());
        assertEquals(testTimestamp, result.getTimestamp());
        verify(locationHistoryRepository, times(1)).save(testLocationHistory);
    }

    @Test
    void testGetLocationHistoryById() {
        when(locationHistoryRepository.findById(1L)).thenReturn(Optional.of(testLocationHistory));

        Optional<LocationHistoryModel> result = locationHistoryUseCase.getLocationHistoryById(1L);

        assertTrue(result.isPresent());
        assertEquals("Bogota", result.get().getCity());
        verify(locationHistoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetLocationHistoryByIdNotFound() {
        when(locationHistoryRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<LocationHistoryModel> result = locationHistoryUseCase.getLocationHistoryById(999L);

        assertFalse(result.isPresent());
        verify(locationHistoryRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAllLocationHistory() {
        List<LocationHistoryModel> locationHistories = Arrays.asList(testLocationHistory);
        when(locationHistoryRepository.findAll()).thenReturn(locationHistories);

        List<LocationHistoryModel> result = locationHistoryUseCase.getAllLocationHistory();

        assertEquals(1, result.size());
        assertEquals("Bogota", result.get(0).getCity());
        verify(locationHistoryRepository, times(1)).findAll();
    }

    @Test
    void testGetLocationHistoryByCity() {
        List<LocationHistoryModel> locationHistories = Arrays.asList(testLocationHistory);
        when(locationHistoryRepository.findByCity("Bogota")).thenReturn(locationHistories);

        List<LocationHistoryModel> result = locationHistoryUseCase.getLocationHistoryByCity("Bogota");

        assertEquals(1, result.size());
        assertEquals("Bogota", result.get(0).getCity());
        verify(locationHistoryRepository, times(1)).findByCity("Bogota");
    }

    @Test
    void testGetLocationHistoryByCityNoResults() {
        when(locationHistoryRepository.findByCity("UnknownCity")).thenReturn(Arrays.asList());

        List<LocationHistoryModel> result = locationHistoryUseCase.getLocationHistoryByCity("UnknownCity");

        assertTrue(result.isEmpty());
        verify(locationHistoryRepository, times(1)).findByCity("UnknownCity");
    }

    @Test
    void testGetLocationHistoryByCountry() {
        List<LocationHistoryModel> locationHistories = Arrays.asList(testLocationHistory);
        when(locationHistoryRepository.findByCountry("Colombia")).thenReturn(locationHistories);

        List<LocationHistoryModel> result = locationHistoryUseCase.getLocationHistoryByCountry("Colombia");

        assertEquals(1, result.size());
        assertEquals("Colombia", result.get(0).getCountry());
        verify(locationHistoryRepository, times(1)).findByCountry("Colombia");
    }

    @Test
    void testGetLocationHistoryByCountryNoResults() {
        when(locationHistoryRepository.findByCountry("UnknownCountry")).thenReturn(Arrays.asList());

        List<LocationHistoryModel> result = locationHistoryUseCase.getLocationHistoryByCountry("UnknownCountry");

        assertTrue(result.isEmpty());
        verify(locationHistoryRepository, times(1)).findByCountry("UnknownCountry");
    }

    @Test
    void testGetLocationHistoryByDateRange() {
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 31, 23, 59);
        List<LocationHistoryModel> locationHistories = Arrays.asList(testLocationHistory);

        when(locationHistoryRepository.findByDateRange(start, end)).thenReturn(locationHistories);

        List<LocationHistoryModel> result = locationHistoryUseCase.getLocationHistoryByDateRange(start, end);

        assertEquals(1, result.size());
        assertEquals("Bogota", result.get(0).getCity());
        verify(locationHistoryRepository, times(1)).findByDateRange(start, end);
    }

    @Test
    void testGetLocationHistoryByDateRangeNoResults() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 31, 23, 59);

        when(locationHistoryRepository.findByDateRange(start, end)).thenReturn(Arrays.asList());

        List<LocationHistoryModel> result = locationHistoryUseCase.getLocationHistoryByDateRange(start, end);

        assertTrue(result.isEmpty());
        verify(locationHistoryRepository, times(1)).findByDateRange(start, end);
    }

    @Test
    void testLocationHistoryExists() {
        when(locationHistoryRepository.existsById(1L)).thenReturn(true);

        boolean result = locationHistoryUseCase.locationHistoryExists(1L);

        assertTrue(result);
        verify(locationHistoryRepository, times(1)).existsById(1L);
    }

    @Test
    void testLocationHistoryDoesNotExist() {
        when(locationHistoryRepository.existsById(999L)).thenReturn(false);

        boolean result = locationHistoryUseCase.locationHistoryExists(999L);

        assertFalse(result);
        verify(locationHistoryRepository, times(1)).existsById(999L);
    }

    @Test
    void testUpdateLocationHistory() {
        LocationHistoryModel updatedLocationHistory = new LocationHistoryModel(
                "Madrid",
                "Spain",
                LocalDateTime.of(2024, 2, 20, 14, 45)
        );
        when(locationHistoryRepository.update(any(LocationHistoryModel.class))).thenReturn(updatedLocationHistory);

        LocationHistoryModel result = locationHistoryUseCase.updateLocationHistory(updatedLocationHistory);

        assertNotNull(result);
        assertEquals("Madrid", result.getCity());
        assertEquals("Spain", result.getCountry());
        verify(locationHistoryRepository, times(1)).update(updatedLocationHistory);
    }

    @Test
    void testDeleteLocationHistory() {
        doNothing().when(locationHistoryRepository).deleteById(1L);

        locationHistoryUseCase.deleteLocationHistory(1L);

        verify(locationHistoryRepository, times(1)).deleteById(1L);
    }
}