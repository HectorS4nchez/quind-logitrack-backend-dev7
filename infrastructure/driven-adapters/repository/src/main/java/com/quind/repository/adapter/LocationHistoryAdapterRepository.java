package com.quind.repository.adapter;

import com.quind.domain.model.LocationHistoryModel;
import com.quind.domain.port.repository.LocationHistoryRepository;
import com.quind.repository.adapter.jpa.entity.LocationHistoryEntity;
import com.quind.repository.adapter.jpa.repository.LocationHistoryJpaRepository;
import com.quind.repository.adapter.jpa.mapper.LocationHistoryMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LocationHistoryAdapterRepository implements LocationHistoryRepository {

    private final LocationHistoryJpaRepository jpaRepository;

    public LocationHistoryAdapterRepository(LocationHistoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public LocationHistoryModel save(LocationHistoryModel locationHistoryModel) {
        LocationHistoryEntity entity = LocationHistoryMapper.toEntity(locationHistoryModel);
        LocationHistoryEntity savedEntity = jpaRepository.save(entity);
        return LocationHistoryMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<LocationHistoryModel> findById(Long id) {
        return jpaRepository.findById(id)
                .map(LocationHistoryMapper::toDomain);
    }

    @Override
    public List<LocationHistoryModel> findAll() {
        return jpaRepository.findAll().stream()
                .map(LocationHistoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationHistoryModel> findByCity(String city) {
        return jpaRepository.findByCity(city).stream()
                .map(LocationHistoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationHistoryModel> findByCountry(String country) {
        return jpaRepository.findByCountry(country).stream()
                .map(LocationHistoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationHistoryModel> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByDateRange(start, end).stream()
                .map(LocationHistoryMapper::toDomain)
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
    public LocationHistoryModel update(LocationHistoryModel locationHistoryModel) {
        LocationHistoryEntity entity = LocationHistoryMapper.toEntity(locationHistoryModel);
        LocationHistoryEntity updatedEntity = jpaRepository.save(entity);
        return LocationHistoryMapper.toDomain(updatedEntity);
    }
}