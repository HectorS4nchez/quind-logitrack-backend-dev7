package com.quind.apirest.controller.mapper;


import com.quind.apirest.controller.request.LocationHistoryRequestDTO;
import com.quind.apirest.controller.response.LocationHistoryResponseDTO;
import com.quind.domain.model.LocationHistoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationHistoryRestMapper {

    LocationHistoryModel toModel(LocationHistoryRequestDTO dto);

    LocationHistoryResponseDTO toResponseDTO(LocationHistoryModel model);

    List<LocationHistoryResponseDTO> toResponseDTOList(List<LocationHistoryModel> models);

    void updateModelFromDTO(LocationHistoryRequestDTO dto, @MappingTarget LocationHistoryModel model);
}
