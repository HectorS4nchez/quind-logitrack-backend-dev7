package com.quind.domain.port.repository;

import com.quind.domain.model.DimensionsModel;

import java.util.List;
import java.util.Optional;

public interface DimensionsRepository {
    DimensionsModel save(DimensionsModel dimensionsModel);
    Optional<DimensionsModel> findById(Long id);
    List<DimensionsModel> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
    DimensionsModel update(DimensionsModel dimensionsModel);
}
