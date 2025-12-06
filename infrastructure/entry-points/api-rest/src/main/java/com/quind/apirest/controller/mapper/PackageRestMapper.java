package com.quind.apirest.controller.mapper;

import com.quind.apirest.controller.request.PackageRequestDTO;
import com.quind.apirest.controller.response.PackageResponseDTO;
import com.quind.domain.model.PackageModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(
        componentModel = "spring",
        uses = {RecipientRestMapper.class, DimensionsRestMapper.class, LocationHistoryRestMapper.class}
)
public interface PackageRestMapper {


    @Mapping(target = "status", ignore = true)
    @Mapping(target = "locationHistory", ignore = true)
    PackageModel toModel(PackageRequestDTO dto);

    PackageResponseDTO toResponseDTO(PackageModel model);

    List<PackageResponseDTO> toResponseDTOList(List<PackageModel> models);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "locationHistory", ignore = true)
    void updateModelFromDTO(PackageRequestDTO dto, @MappingTarget PackageModel model);
}
