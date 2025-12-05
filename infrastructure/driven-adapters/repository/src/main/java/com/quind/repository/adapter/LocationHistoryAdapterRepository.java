package com.quind.repository.adapter;

import com.quind.domain.exception.LocationHistoryNotFoundException;
import com.quind.domain.exception.RepositoryOperationException;
import com.quind.domain.model.LocationHistoryModel;
import com.quind.domain.port.repository.LocationHistoryRepository;
import com.quind.repository.adapter.jpa.entity.LocationHistoryEntity;
import com.quind.repository.adapter.jpa.repository.LocationHistoryJpaRepository;
import com.quind.repository.adapter.jpa.mapper.LocationHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LocationHistoryAdapterRepository implements LocationHistoryRepository {

    private final LocationHistoryJpaRepository jpaRepository;
    private final LocationHistoryMapper locationHistoryMapper;

    @Override
    public LocationHistoryModel save(LocationHistoryModel locationHistoryModel) {
        try {
            LocationHistoryEntity entity = locationHistoryMapper.toEntity(locationHistoryModel);
            LocationHistoryEntity savedEntity = jpaRepository.save(entity);
            return locationHistoryMapper.toDomain(savedEntity);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to save location history", ex);
        }
    }

    @Override
    public Optional<LocationHistoryModel> findById(Long id) {
        try {
            return jpaRepository.findById(id)
                    .map(locationHistoryMapper::toDomain);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to find location history by ID: " + id, ex);
        }
    }

    @Override
    public List<LocationHistoryModel> findAll() {
        try {
            return jpaRepository.findAll().stream()
                    .map(locationHistoryMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to retrieve all location histories", ex);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            return jpaRepository.existsById(id);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to check location history existence for ID: " + id, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            if (!jpaRepository.existsById(id)) {
                throw new LocationHistoryNotFoundException(id);
            }
            jpaRepository.deleteById(id);
        } catch (LocationHistoryNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to delete location history with ID: " + id, ex);
        }
    }

    @Override
    public LocationHistoryModel update(LocationHistoryModel locationHistoryModel) {
        try {
            if (locationHistoryModel.getId() == null) {
                throw new IllegalArgumentException("Location history ID cannot be null for update");
            }
            if (!jpaRepository.existsById(locationHistoryModel.getId())) {
                throw new LocationHistoryNotFoundException(locationHistoryModel.getId());
            }
            LocationHistoryEntity entity = locationHistoryMapper.toEntity(locationHistoryModel);
            LocationHistoryEntity updatedEntity = jpaRepository.save(entity);
            return locationHistoryMapper.toDomain(updatedEntity);
        } catch (LocationHistoryNotFoundException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to update location history", ex);
        }
    }
}
