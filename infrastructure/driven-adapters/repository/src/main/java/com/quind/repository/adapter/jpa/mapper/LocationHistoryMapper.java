package com.quind.repository.adapter.jpa.mapper;

import com.quind.domain.model.LocationHistoryModel;
import com.quind.repository.adapter.jpa.entity.LocationHistoryEntity;

public class LocationHistoryMapper {

    public static LocationHistoryEntity toEntity(LocationHistoryModel locationHistoryModel) {
        if (locationHistoryModel == null) {
            return null;
        }
        return new LocationHistoryEntity(
                locationHistoryModel.getCity(),
                locationHistoryModel.getCountry(),
                locationHistoryModel.getTimestamp()
        );
    }

    public static LocationHistoryModel toDomain(LocationHistoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return new LocationHistoryModel(
                entity.getCity(),
                entity.getCountry(),
                entity.getTimestamp()
        );
    }
}