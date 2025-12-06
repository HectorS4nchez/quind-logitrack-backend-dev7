package com.quind.apirest.controller.mapper;

import com.quind.apirest.controller.request.DimensionsRequestDTO;
import com.quind.apirest.controller.response.DimensionsResponseDTO;
import com.quind.domain.model.DimensionsModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface DimensionsRestMapper {

    DimensionsModel toModel(DimensionsRequestDTO dto);

    DimensionsResponseDTO toResponseDTO(DimensionsModel model);

    void updateModelFromDTO(DimensionsRequestDTO dto, @MappingTarget DimensionsModel model);
}
