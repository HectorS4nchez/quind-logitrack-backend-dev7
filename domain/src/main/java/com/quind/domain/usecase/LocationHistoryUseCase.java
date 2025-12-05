package com.quind.domain.usecase;

import com.quind.domain.model.LocationHistoryModel;
import com.quind.domain.port.repository.LocationHistoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class LocationHistoryUseCase {

    private final LocationHistoryRepository locationHistoryRepository;

    public LocationHistoryUseCase(LocationHistoryRepository locationHistoryRepository) {
        this.locationHistoryRepository = locationHistoryRepository;
    }

    public LocationHistoryModel createLocationHistory(LocationHistoryModel locationHistory) {
        return locationHistoryRepository.save(locationHistory);
    }

    public Optional<LocationHistoryModel> getLocationHistoryById(Long id) {
        return locationHistoryRepository.findById(id);
    }

    public List<LocationHistoryModel> getAllLocationHistory() {
        return locationHistoryRepository.findAll();
    }

    public List<LocationHistoryModel> getLocationHistoryByCity(String city) {
        return locationHistoryRepository.findByCity(city);
    }

    public List<LocationHistoryModel> getLocationHistoryByCountry(String country) {
        return locationHistoryRepository.findByCountry(country);
    }

    public List<LocationHistoryModel> getLocationHistoryByDateRange(LocalDateTime start, LocalDateTime end) {
        return locationHistoryRepository.findByDateRange(start, end);
    }

    public boolean locationHistoryExists(Long id) {
        return locationHistoryRepository.existsById(id);
    }

    public LocationHistoryModel updateLocationHistory(LocationHistoryModel locationHistory) {
        return locationHistoryRepository.update(locationHistory);
    }

    public void deleteLocationHistory(Long id) {
        locationHistoryRepository.deleteById(id);
    }
}