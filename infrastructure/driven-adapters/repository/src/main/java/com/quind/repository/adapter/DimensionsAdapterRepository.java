package com.quind.repository.adapter;

import com.quind.domain.exception.DimensionsNotFoundException;
import com.quind.domain.exception.RepositoryOperationException;
import com.quind.domain.model.DimensionsModel;
import com.quind.domain.port.repository.DimensionsRepository;
import com.quind.repository.adapter.jpa.entity.DimensionsEntity;
import com.quind.repository.adapter.jpa.repository.DimensionsJpaRepository;
import com.quind.repository.adapter.jpa.mapper.DimensionsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DimensionsAdapterRepository implements DimensionsRepository {

    private final DimensionsJpaRepository jpaRepository;
    private final DimensionsMapper dimensionsMapper;

    @Override
    public DimensionsModel save(DimensionsModel dimensionsModel) {
        try {
            DimensionsEntity entity = dimensionsMapper.toEntity(dimensionsModel);
            DimensionsEntity savedEntity = jpaRepository.save(entity);
            return dimensionsMapper.toDomain(savedEntity);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to save dimensions", ex);
        }
    }

    @Override
    public Optional<DimensionsModel> findById(Long id) {
        try {
            return jpaRepository.findById(id)
                    .map(dimensionsMapper::toDomain);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to find dimensions by ID: " + id, ex);
        }
    }

    @Override
    public List<DimensionsModel> findAll() {
        try {
            return jpaRepository.findAll().stream()
                    .map(dimensionsMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to retrieve all dimensions", ex);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            return jpaRepository.existsById(id);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to check dimensions existence for ID: " + id, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            if (!jpaRepository.existsById(id)) {
                throw new DimensionsNotFoundException(id);
            }
            jpaRepository.deleteById(id);
        } catch (DimensionsNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to delete dimensions with ID: " + id, ex);
        }
    }

    @Override
    public DimensionsModel update(DimensionsModel dimensionsModel) {
        try {
            if (dimensionsModel.getId() == null || !jpaRepository.existsById(dimensionsModel.getId())) {
                throw new DimensionsNotFoundException(dimensionsModel.getId());
            }
            DimensionsEntity entity = dimensionsMapper.toEntity(dimensionsModel);
            DimensionsEntity updatedEntity = jpaRepository.save(entity);
            return dimensionsMapper.toDomain(updatedEntity);
        } catch (DimensionsNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to update dimensions", ex);
        }
    }
}