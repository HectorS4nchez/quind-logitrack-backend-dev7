package com.quind.domain.port.repository;

import com.quind.domain.model.LocationHistoryModel;

import java.util.List;
import java.util.Optional;

public interface LocationHistoryRepository {
    LocationHistoryModel save(LocationHistoryModel locationHistoryModel);
    Optional<LocationHistoryModel> findById(Long id);
    List<LocationHistoryModel> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
    LocationHistoryModel update(LocationHistoryModel locationHistoryModel);
}
