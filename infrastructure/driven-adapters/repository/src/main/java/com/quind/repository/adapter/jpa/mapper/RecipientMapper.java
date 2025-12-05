package com.quind.repository.adapter.jpa.mapper;

import com.quind.domain.model.RecipientModel;
import com.quind.repository.adapter.jpa.entity.RecipientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipientMapper {

    RecipientEntity toEntity(RecipientModel recipientModel);

    RecipientModel toDomain(RecipientEntity entity);
}
