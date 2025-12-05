package com.quind.repository.adapter;

import com.quind.domain.model.PackageModel;
import com.quind.domain.port.repository.PackageRepository;
import com.quind.repository.adapter.jpa.entity.PackageEntity;
import com.quind.repository.adapter.jpa.mapper.PackageMapper;
import com.quind.repository.adapter.jpa.repository.PackageJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PackageAdapterRepository implements PackageRepository {

    private final PackageJpaRepository jpaRepository;
    private final PackageMapper packageMapper;

    public PackageAdapterRepository(PackageJpaRepository jpaRepository, PackageMapper packageMapper) {
        this.jpaRepository = jpaRepository;
        this.packageMapper = packageMapper;
    }

    @Override
    public PackageModel save(PackageModel pkg) {
        PackageEntity entity = packageMapper.toEntity(pkg);
        PackageEntity savedEntity = jpaRepository.save(entity);
        return packageMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<PackageModel> findById(String trackingId) {
        return jpaRepository.findById(trackingId)
                .map(PackageMapper::toDomain);
    }

    @Override
    public List<PackageModel> findAll() {
        return jpaRepository.findAll().stream()
                .map(PackageMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String trackingId) {
        return jpaRepository.existsById(trackingId);
    }

    @Override
    public void deleteById(String trackingId) {
        jpaRepository.deleteById(trackingId);
    }

    @Override
    public PackageModel update(PackageModel pkg) {
        PackageEntity entity = PackageMapper.toEntity(pkg);
        PackageEntity updatedEntity = jpaRepository.save(entity);
        return PackageMapper.toDomain(updatedEntity);
    }
}