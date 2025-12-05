package com.quind.apirest.controller;

import com.quind.domain.model.PackageModel;
import com.quind.domain.model.enums.PackageStatus;
import com.quind.domain.usecase.PackageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageUseCase packageUseCase;

    @PostMapping
    public ResponseEntity<PackageModel> createPackage(@RequestBody PackageModel pkg) {
        PackageModel created = packageUseCase.createPackage(pkg);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<PackageModel> getPackageById(@PathVariable String trackingId) {
        return packageUseCase.getPackageById(trackingId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PackageModel>> getAllPackages() {
        List<PackageModel> packages = packageUseCase.getAllPackages();
        return ResponseEntity.ok(packages);
    }

    @PutMapping("/{trackingId}")
    public ResponseEntity<PackageModel> updatePackage(@PathVariable String trackingId, @RequestBody PackageModel pkg) {
        if (!packageUseCase.packageExists(trackingId)) {
            return ResponseEntity.notFound().build();
        }
        PackageModel updated = packageUseCase.updatePackage(pkg);
        return ResponseEntity.ok(updated);
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
    public ResponseEntity<PackageModel> changePackageStatus(
            @PathVariable String trackingId,
            @RequestParam PackageStatus status) {
        try {
            PackageModel updated = packageUseCase.changePackageStatus(trackingId, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{trackingId}/locations")
    public ResponseEntity<PackageModel> addLocationToPackage(
            @PathVariable String trackingId,
            @RequestParam String city,
            @RequestParam String country,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp) {
        try {
            PackageModel updated;
            if (timestamp != null) {
                updated = packageUseCase.addLocationToPackage(trackingId, city, country, timestamp);
            } else {
                updated = packageUseCase.addLocationToPackage(trackingId, city, country);
            }
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}