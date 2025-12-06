package com.quind.apirest.controller.mapper;


import com.quind.apirest.controller.request.RecipientRequestDTO;
import com.quind.apirest.controller.response.RecipientResponseDTO;
import com.quind.domain.model.RecipientModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RecipientRestMapper {

    RecipientModel toModel(RecipientRequestDTO dto);

    RecipientResponseDTO toResponseDTO(RecipientModel model);

    void updateModelFromDTO(RecipientRequestDTO dto, @MappingTarget RecipientModel model);
}
