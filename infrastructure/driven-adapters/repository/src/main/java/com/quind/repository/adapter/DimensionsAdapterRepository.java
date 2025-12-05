package com.quind.repository.adapter;

import com.quind.domain.model.DimensionsModel;
import com.quind.domain.port.repository.DimensionsRepository;
import com.quind.repository.adapter.jpa.entity.DimensionsEntity;
import com.quind.repository.adapter.jpa.repository.DimensionsJpaRepository;
import com.quind.repository.adapter.jpa.mapper.DimensionsMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DimensionsAdapterRepository implements DimensionsRepository {

    private final DimensionsJpaRepository jpaRepository;

    public DimensionsAdapterRepository(DimensionsJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public DimensionsModel save(DimensionsModel dimensionsModel) {
        DimensionsEntity entity = DimensionsMapper.toEntity(dimensionsModel);
        DimensionsEntity savedEntity = jpaRepository.save(entity);
        return DimensionsMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<DimensionsModel> findById(Long id) {
        return jpaRepository.findById(id)
                .map(DimensionsMapper::toDomain);
    }

    @Override
    public List<DimensionsModel> findAll() {
        return jpaRepository.findAll().stream()
                .map(DimensionsMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public DimensionsModel update(DimensionsModel dimensionsModel) {
        DimensionsEntity entity = DimensionsMapper.toEntity(dimensionsModel);
        DimensionsEntity updatedEntity = jpaRepository.save(entity);
        return DimensionsMapper.toDomain(updatedEntity);
    }
}