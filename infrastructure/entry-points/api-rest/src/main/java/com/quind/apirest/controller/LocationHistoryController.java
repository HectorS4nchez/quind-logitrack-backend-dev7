package com.quind.apirest.controller;

import com.quind.domain.model.LocationHistoryModel;
import com.quind.domain.usecase.LocationHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/location-history")
@RequiredArgsConstructor
public class LocationHistoryController {

    private final LocationHistoryUseCase locationHistoryUseCase;

    @PostMapping
    public ResponseEntity<LocationHistoryModel> createLocationHistory(@RequestBody LocationHistoryModel locationHistory) {
        LocationHistoryModel created = locationHistoryUseCase.createLocationHistory(locationHistory);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationHistoryModel> getLocationHistoryById(@PathVariable Long id) {
        return locationHistoryUseCase.getLocationHistoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LocationHistoryModel>> getAllLocationHistory() {
        List<LocationHistoryModel> locationHistories = locationHistoryUseCase.getAllLocationHistory();
        return ResponseEntity.ok(locationHistories);
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<LocationHistoryModel>> getLocationHistoryByCity(@RequestParam String city) {
        List<LocationHistoryModel> locationHistories = locationHistoryUseCase.getLocationHistoryByCity(city);
        return ResponseEntity.ok(locationHistories);
    }

    @GetMapping("/by-country")
    public ResponseEntity<List<LocationHistoryModel>> getLocationHistoryByCountry(@RequestParam String country) {
        List<LocationHistoryModel> locationHistories = locationHistoryUseCase.getLocationHistoryByCountry(country);
        return ResponseEntity.ok(locationHistories);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<LocationHistoryModel>> getLocationHistoryByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<LocationHistoryModel> locationHistories = locationHistoryUseCase.getLocationHistoryByDateRange(start, end);
        return ResponseEntity.ok(locationHistories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationHistoryModel> updateLocationHistory(@PathVariable Long id, @RequestBody LocationHistoryModel locationHistory) {
        if (!locationHistoryUseCase.locationHistoryExists(id)) {
            return ResponseEntity.notFound().build();
        }
        LocationHistoryModel updated = locationHistoryUseCase.updateLocationHistory(locationHistory);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationHistory(@PathVariable Long id) {
        if (!locationHistoryUseCase.locationHistoryExists(id)) {
            return ResponseEntity.notFound().build();
        }
        locationHistoryUseCase.deleteLocationHistory(id);
        return ResponseEntity.noContent().build();
    }
}