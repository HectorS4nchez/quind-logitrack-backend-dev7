package com.quind.apirest.controller;

import com.quind.apirest.controller.request.DimensionsRequestDTO;
import com.quind.apirest.controller.response.DimensionsResponseDTO;
import com.quind.apirest.controller.mapper.DimensionsRestMapper;
import com.quind.domain.model.DimensionsModel;
import com.quind.domain.usecase.DimensionsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/dimensions")
@RequiredArgsConstructor
public class DimensionsController {

    private final DimensionsUseCase dimensionsUseCase;
    private final DimensionsRestMapper dimensionsRestMapper;

    @PostMapping
    public ResponseEntity<DimensionsResponseDTO> createDimensions(@Valid @RequestBody DimensionsRequestDTO request) {
        DimensionsModel model = dimensionsRestMapper.toModel(request);
        DimensionsModel created = dimensionsUseCase.createDimensions(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(dimensionsRestMapper.toResponseDTO(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DimensionsResponseDTO> getDimensionsById(@PathVariable Long id) {
        return dimensionsUseCase.getDimensionsById(id)
                .map(dimensionsRestMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DimensionsResponseDTO>> getAllDimensions() {
        List<DimensionsResponseDTO> dimensions = dimensionsUseCase.getAllDimensions().stream()
                .map(dimensionsRestMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dimensions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DimensionsResponseDTO> updateDimensions(
            @PathVariable Long id,
            @Valid @RequestBody DimensionsRequestDTO request) {
        if (!dimensionsUseCase.dimensionsExists(id)) {
            return ResponseEntity.notFound().build();
        }
        DimensionsModel model = dimensionsRestMapper.toModel(request);
        model.setId(id);
        DimensionsModel updated = dimensionsUseCase.updateDimensions(model);
        return ResponseEntity.ok(dimensionsRestMapper.toResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDimensions(@PathVariable Long id) {
        if (!dimensionsUseCase.dimensionsExists(id)) {
            return ResponseEntity.notFound().build();
        }
        dimensionsUseCase.deleteDimensions(id);
        return ResponseEntity.noContent().build();
    }
}