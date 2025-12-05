package com.quind.repository.adapter.jpa.mapper;

import com.quind.domain.model.DimensionsModel;
import com.quind.repository.adapter.jpa.entity.DimensionsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DimensionsMapper {

    DimensionsEntity toEntity(DimensionsModel dimensionsModel);

    DimensionsModel toDomain(DimensionsEntity entity);
}
