package com.quind.repository.adapter.jpa.mapper;

import com.quind.domain.model.DimensionsModel;
import com.quind.repository.adapter.jpa.entity.DimensionsEntity;

public class DimensionsMapper {

    public static DimensionsEntity toEntity(DimensionsModel dimensionsModel) {
        if (dimensionsModel == null) {
            return null;
        }
        return new DimensionsEntity(
                dimensionsModel.getHeight(),
                dimensionsModel.getWidth(),
                dimensionsModel.getDepth()
        );
    }

    public static DimensionsModel toDomain(DimensionsEntity entity) {
        if (entity == null) {
            return null;
        }
        return new DimensionsModel(
                entity.getHeight(),
                entity.getWidth(),
                entity.getDepth()
        );
    }
}