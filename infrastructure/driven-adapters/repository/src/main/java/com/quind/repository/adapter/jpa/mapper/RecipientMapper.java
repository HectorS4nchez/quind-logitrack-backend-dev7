package com.quind.repository.adapter.jpa.mapper;

import com.quind.domain.model.RecipientModel;
import com.quind.repository.adapter.jpa.entity.RecipientEntity;

public class RecipientMapper {

    public static RecipientEntity toEntity(RecipientModel recipientModel) {
        if (recipientModel == null) {
            return null;
        }
        return new RecipientEntity(
                recipientModel.getName(),
                recipientModel.getAddress()
        );
    }

    public static RecipientModel toDomain(RecipientEntity entity) {
        if (entity == null) {
            return null;
        }
        return new RecipientModel(
                entity.getName(),
                entity.getAddress()
        );
    }
}