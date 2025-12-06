package com.quind.apirest.controller;

import com.quind.apirest.controller.request.LocationHistoryRequestDTO;
import com.quind.apirest.controller.response.LocationHistoryResponseDTO;
import com.quind.apirest.controller.mapper.LocationHistoryRestMapper;
import com.quind.domain.model.LocationHistoryModel;
import com.quind.domain.usecase.LocationHistoryUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location-history")
@RequiredArgsConstructor
public class LocationHistoryController {

    private final LocationHistoryUseCase locationHistoryUseCase;
    private final LocationHistoryRestMapper locationHistoryRestMapper;

    @PostMapping
    public ResponseEntity<LocationHistoryResponseDTO> createLocationHistory(
            @Valid @RequestBody LocationHistoryRequestDTO request) {
        LocationHistoryModel model = locationHistoryRestMapper.toModel(request);
        LocationHistoryModel created = locationHistoryUseCase.createLocationHistory(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(locationHistoryRestMapper.toResponseDTO(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationHistoryResponseDTO> getLocationHistoryById(@PathVariable Long id) {
        return locationHistoryUseCase.getLocationHistoryById(id)
                .map(locationHistoryRestMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LocationHistoryResponseDTO>> getAllLocationHistory() {
        List<LocationHistoryModel> histories = locationHistoryUseCase.getAllLocationHistory();
        return ResponseEntity.ok(locationHistoryRestMapper.toResponseDTOList(histories));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationHistoryResponseDTO> updateLocationHistory(
            @PathVariable Long id,
            @Valid @RequestBody LocationHistoryRequestDTO request) {
        if (!locationHistoryUseCase.locationHistoryExists(id)) {
            return ResponseEntity.notFound().build();
        }
        LocationHistoryModel model = locationHistoryRestMapper.toModel(request);
        model.setId(id);
        LocationHistoryModel updated = locationHistoryUseCase.updateLocationHistory(model);
        return ResponseEntity.ok(locationHistoryRestMapper.toResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationHistory(@PathVariable Long id) {
        if (!locationHistoryUseCase.locationHistoryExists(id)) {
            return ResponseEntity.notFound().build();
        }
        locationHistoryUseCase.deleteLocationHistory(id);
        return ResponseEntity.noContent().build();
    }
}