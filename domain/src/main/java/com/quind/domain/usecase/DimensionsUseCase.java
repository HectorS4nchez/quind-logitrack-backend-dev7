package com.quind.domain.usecase;

import com.quind.domain.model.DimensionsModel;
import com.quind.domain.port.repository.DimensionsRepository;

import java.util.List;
import java.util.Optional;

public class DimensionsUseCase {

    private final DimensionsRepository dimensionsRepository;

    public DimensionsUseCase(DimensionsRepository dimensionsRepository) {
        this.dimensionsRepository = dimensionsRepository;
    }

    public DimensionsModel createDimensions(DimensionsModel dimensions) {
        return dimensionsRepository.save(dimensions);
    }

    public Optional<DimensionsModel> getDimensionsById(Long id) {
        return dimensionsRepository.findById(id);
    }

    public List<DimensionsModel> getAllDimensions() {
        return dimensionsRepository.findAll();
    }

    public boolean dimensionsExists(Long id) {
        return dimensionsRepository.existsById(id);
    }

    public DimensionsModel updateDimensions(DimensionsModel dimensions) {
        return dimensionsRepository.update(dimensions);
    }

    public void deleteDimensions(Long id) {
        dimensionsRepository.deleteById(id);
    }
}