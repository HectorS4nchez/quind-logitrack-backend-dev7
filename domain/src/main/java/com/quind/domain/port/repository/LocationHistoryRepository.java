package com.quind.domain.port.repository;

import com.quind.domain.model.LocationHistoryModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LocationHistoryRepository {
    LocationHistoryModel save(LocationHistoryModel locationHistoryModel);
    Optional<LocationHistoryModel> findById(Long id);
    List<LocationHistoryModel> findAll();
    List<LocationHistoryModel> findByCity(String city);
    List<LocationHistoryModel> findByCountry(String country);
    List<LocationHistoryModel> findByDateRange(LocalDateTime start, LocalDateTime end);
    boolean existsById(Long id);
    void deleteById(Long id);
    LocationHistoryModel update(LocationHistoryModel locationHistoryModel);
}
