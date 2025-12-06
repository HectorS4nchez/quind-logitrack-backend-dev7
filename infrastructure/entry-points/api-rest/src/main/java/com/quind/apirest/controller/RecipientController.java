package com.quind.apirest.controller;

import com.quind.apirest.controller.request.RecipientRequestDTO;
import com.quind.apirest.controller.response.RecipientResponseDTO;
import com.quind.apirest.controller.mapper.RecipientRestMapper;
import com.quind.domain.model.RecipientModel;
import com.quind.domain.usecase.RecipientUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipients")
@RequiredArgsConstructor
public class RecipientController {

    private final RecipientUseCase recipientUseCase;
    private final RecipientRestMapper recipientDTOMapper;

    @PostMapping
    public ResponseEntity<RecipientResponseDTO> createRecipient(@Valid @RequestBody RecipientRequestDTO request) {
        RecipientModel model = recipientDTOMapper.toModel(request);
        RecipientModel created = recipientUseCase.createRecipient(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipientDTOMapper.toResponseDTO(created));
    }


    @GetMapping("/{id}")
    public ResponseEntity<RecipientResponseDTO> getRecipientById(@PathVariable Long id) {
        return recipientUseCase.getRecipientById(id)
                .map(recipientDTOMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RecipientResponseDTO>> getAllRecipients() {
        List<RecipientResponseDTO> recipients = recipientUseCase.getAllRecipients().stream()
                .map(recipientDTOMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recipients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipientResponseDTO> updateRecipient(
            @PathVariable Long id,
            @Valid @RequestBody RecipientRequestDTO request) {
        if (!recipientUseCase.recipientExists(id)) {
            return ResponseEntity.notFound().build();
        }
        RecipientModel model = recipientDTOMapper.toModel(request);
        model.setId(id);
        RecipientModel updated = recipientUseCase.updateRecipient(model);
        return ResponseEntity.ok(recipientDTOMapper.toResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        if (!recipientUseCase.recipientExists(id)) {
            return ResponseEntity.notFound().build();
        }
        recipientUseCase.deleteRecipient(id);
        return ResponseEntity.noContent().build();
    }
}