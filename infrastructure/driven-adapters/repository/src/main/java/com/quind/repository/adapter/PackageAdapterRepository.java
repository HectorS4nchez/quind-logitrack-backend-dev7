package com.quind.repository.adapter;

import com.quind.domain.exception.DuplicateTrackingIdException;
import com.quind.domain.exception.PackageNotFoundException;
import com.quind.domain.exception.RepositoryOperationException;
import com.quind.domain.model.PackageModel;
import com.quind.domain.port.repository.PackageRepository;
import com.quind.repository.adapter.jpa.entity.PackageEntity;
import com.quind.repository.adapter.jpa.mapper.PackageMapper;
import com.quind.repository.adapter.jpa.repository.PackageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PackageAdapterRepository implements PackageRepository {

    private final PackageJpaRepository jpaRepository;
    private final PackageMapper packageMapper;

    @Override
    public PackageModel save(PackageModel pkg) {
        PackageEntity entity = packageMapper.toEntity(pkg);

        if (entity.getLocationHistory() != null) {
            entity.getLocationHistory().forEach(lh -> lh.setPackageEntity(entity));
        }

        PackageEntity saved = jpaRepository.save(entity);
        return packageMapper.toDomain(saved);
    }

    @Override
    public Optional<PackageModel> findById(String trackingId) {
        try {
            return jpaRepository.findById(trackingId)
                    .map(packageMapper::toDomain);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to find package by tracking ID: " + trackingId, ex);
        }
    }

    @Override
    public List<PackageModel> findAll() {
        try {
            return jpaRepository.findAll().stream()
                    .map(packageMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to retrieve all packages", ex);
        }
    }

    @Override
    public boolean existsById(String trackingId) {
        try {
            return jpaRepository.existsById(trackingId);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to check package existence for tracking ID: " + trackingId, ex);
        }
    }

    @Override
    public void deleteById(String trackingId) {
        try {
            if (!jpaRepository.existsById(trackingId)) {
                throw new PackageNotFoundException(trackingId);
            }
            jpaRepository.deleteById(trackingId);
        } catch (PackageNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to delete package with tracking ID: " + trackingId, ex);
        }
    }

    @Override
    public PackageModel update(PackageModel pkg) {
        try {
            if (!jpaRepository.existsById(pkg.getTrackingId())) {
                throw new PackageNotFoundException(pkg.getTrackingId());
            }
            PackageEntity entity = packageMapper.toEntity(pkg);
            PackageEntity updatedEntity = jpaRepository.save(entity);
            return packageMapper.toDomain(updatedEntity);
        } catch (PackageNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to update package", ex);
        }
    }
}