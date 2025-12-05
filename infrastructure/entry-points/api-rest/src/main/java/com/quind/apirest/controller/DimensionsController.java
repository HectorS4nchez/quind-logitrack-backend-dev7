package com.quind.apirest.controller;

import com.quind.domain.model.DimensionsModel;
import com.quind.domain.usecase.DimensionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dimensions")
@RequiredArgsConstructor
public class DimensionsController {

    private final DimensionsUseCase dimensionsUseCase;

    @PostMapping
    public ResponseEntity<DimensionsModel> createDimensions(@RequestBody DimensionsModel dimensions) {
        DimensionsModel created = dimensionsUseCase.createDimensions(dimensions);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DimensionsModel> getDimensionsById(@PathVariable Long id) {
        return dimensionsUseCase.getDimensionsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DimensionsModel>> getAllDimensions() {
        List<DimensionsModel> dimensions = dimensionsUseCase.getAllDimensions();
        return ResponseEntity.ok(dimensions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DimensionsModel> updateDimensions(@PathVariable Long id, @RequestBody DimensionsModel dimensions) {
        if (!dimensionsUseCase.dimensionsExists(id)) {
            return ResponseEntity.notFound().build();
        }
        DimensionsModel updated = dimensionsUseCase.updateDimensions(dimensions);
        return ResponseEntity.ok(updated);
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