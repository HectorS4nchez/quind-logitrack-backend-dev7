package com.quind.repository.adapter.jpa.mapper;

import com.quind.domain.model.LocationHistoryModel;
import com.quind.domain.model.PackageModel;
import com.quind.repository.adapter.jpa.entity.LocationHistoryEntity;
import com.quind.repository.adapter.jpa.entity.PackageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {RecipientMapper.class, DimensionsMapper.class, LocationHistoryMapper.class}
)
public interface PackageMapper {

    @Mapping(target = "locationHistory", qualifiedByName = "mapLocationHistoryToEntity")
    PackageEntity toEntity(PackageModel pkg);

    PackageModel toDomain(PackageEntity entity);

    @Named("mapLocationHistoryToEntity")
    default List<LocationHistoryEntity> mapLocationHistoryToEntity(
            List<LocationHistoryModel> locationHistory) {
        if (locationHistory == null) {
            return null;
        }
        return locationHistory.stream()
                .map(this::mapSingleLocationHistory)
                .collect(java.util.stream.Collectors.toList());
    }

    default com.quind.repository.adapter.jpa.entity.LocationHistoryEntity mapSingleLocationHistory(
            com.quind.domain.model.LocationHistoryModel model) {
        if (model == null) {
            return null;
        }
        com.quind.repository.adapter.jpa.entity.LocationHistoryEntity entity =
                new com.quind.repository.adapter.jpa.entity.LocationHistoryEntity(
                        model.getCity(),
                        model.getCountry(),
                        model.getTimestamp()
                );
        return entity;
    }
}
