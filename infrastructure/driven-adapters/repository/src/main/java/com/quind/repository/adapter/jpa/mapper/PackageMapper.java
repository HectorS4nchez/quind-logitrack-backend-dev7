package com.quind.repository.adapter.jpa.mapper;

import com.quind.domain.model.PackageModel;
import com.quind.repository.adapter.jpa.entity.PackageEntity;

import java.util.stream.Collectors;

public class PackageMapper {

    public static PackageEntity toEntity(PackageModel pkg) {
        if (pkg == null) {
            return null;
        }

        PackageEntity entity = new PackageEntity();
        entity.setTrackingId(pkg.getTrackingId());
        entity.setRecipient(RecipientMapper.toEntity(pkg.getRecipient()));
        entity.setDimensions(DimensionsMapper.toEntity(pkg.getDimensions()));
        entity.setWeight(pkg.getWeight());
        entity.setStatus(pkg.getStatus());

        if (pkg.getLocationHistory() != null) {
            entity.setLocationHistory(
                    pkg.getLocationHistory().stream()
                            .map(LocationHistoryMapper::toEntity)
                            .peek(lh -> lh.setPackageEntity(entity))
                            .collect(Collectors.toList())
            );
        }

        return entity;
    }

    public static PackageModel toDomain(PackageEntity entity) {
        if (entity == null) {
            return null;
        }

        PackageModel pkg = new PackageModel();
        pkg.setTrackingId(entity.getTrackingId());
        pkg.setRecipient(RecipientMapper.toDomain(entity.getRecipient()));
        pkg.setDimensions(DimensionsMapper.toDomain(entity.getDimensions()));
        pkg.setWeight(entity.getWeight());
        pkg.setStatus(entity.getStatus());

        if (entity.getLocationHistory() != null) {
            pkg.setLocationHistory(
                    entity.getLocationHistory().stream()
                            .map(LocationHistoryMapper::toDomain)
                            .collect(Collectors.toList())
            );
        }

        return pkg;
    }
}