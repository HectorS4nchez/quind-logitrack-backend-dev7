package com.quind.repository.adapter.jpa.mapper;

import com.quind.domain.model.LocationHistoryModel;
import com.quind.repository.adapter.jpa.entity.LocationHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationHistoryMapper {

    LocationHistoryEntity toEntity(LocationHistoryModel locationHistoryModel);

    LocationHistoryModel toDomain(LocationHistoryEntity entity);
}
