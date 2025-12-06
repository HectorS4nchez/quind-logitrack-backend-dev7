package com.quind.apirest.controller;

import com.quind.apirest.controller.client.GeocodingClient;
import com.quind.apirest.controller.request.PackageRequestDTO;
import com.quind.apirest.controller.response.PackageResponseDTO;
import com.quind.apirest.controller.mapper.PackageRestMapper;
import com.quind.domain.model.PackageModel;
import com.quind.domain.model.enums.PackageStatus;
import com.quind.domain.usecase.PackageUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageUseCase packageUseCase;
    private final PackageRestMapper packageDTOMapper;
    private final GeocodingClient geocodingClient;

    @PostMapping
    public ResponseEntity<PackageResponseDTO> createPackage(@Valid @RequestBody PackageRequestDTO request) {
        PackageModel model = packageDTOMapper.toModel(request);
        PackageModel created = packageUseCase.createPackage(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(packageDTOMapper.toResponseDTO(created));
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<PackageResponseDTO> getPackageById(@PathVariable String trackingId) {
        return packageUseCase.getPackageById(trackingId)
                .map(packageDTOMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PackageResponseDTO>> getAllPackages() {
        List<PackageModel> packages = packageUseCase.getAllPackages();
        return ResponseEntity.ok(packageDTOMapper.toResponseDTOList(packages));
    }

    @PutMapping("/{trackingId}")
    public ResponseEntity<PackageResponseDTO> updatePackage(
            @PathVariable String trackingId,
            @Valid @RequestBody PackageRequestDTO request) {
        if (!packageUseCase.packageExists(trackingId)) {
            return ResponseEntity.notFound().build();
        }
        PackageModel model = packageDTOMapper.toModel(request);
        model.setTrackingId(trackingId);
        PackageModel updated = packageUseCase.updatePackage(model);
        return ResponseEntity.ok(packageDTOMapper.toResponseDTO(updated));
    }

    @DeleteMapping("/{trackingId}")
    public ResponseEntity<Void> deletePackage(@PathVariable String trackingId) {
        if (!packageUseCase.packageExists(trackingId)) {
            return ResponseEntity.notFound().build();
        }
        packageUseCase.deletePackage(trackingId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{trackingId}/status")
    public ResponseEntity<PackageResponseDTO> changePackageStatus(
            @PathVariable String trackingId,
            @RequestParam PackageStatus status) {
        try {
            PackageModel updated = packageUseCase.changePackageStatus(trackingId, status);
            return ResponseEntity.ok(packageDTOMapper.toResponseDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{trackingId}/locations")
    public ResponseEntity<?> addLocationToPackage(
            @PathVariable String trackingId,
            @RequestParam String city,
            @RequestParam String country,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp) {

        if (!geocodingClient.validateLocation(city, country)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid location: " + city + ", " + country);
            error.put("message", "The location could not be found in the geocoding service");
            return ResponseEntity.badRequest().body(error);
        }

        try {
            PackageModel updated;
            if (timestamp != null) {
                updated = packageUseCase.addLocationToPackage(trackingId, city, country, timestamp);
            } else {
                updated = packageUseCase.addLocationToPackage(trackingId, city, country);
            }
            return ResponseEntity.ok(packageDTOMapper.toResponseDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}