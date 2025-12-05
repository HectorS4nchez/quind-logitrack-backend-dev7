package com.quind.apirest.controller;

import com.quind.domain.model.RecipientModel;
import com.quind.domain.usecase.RecipientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipients")
@RequiredArgsConstructor
public class RecipientController {

    private final RecipientUseCase recipientUseCase;

    @PostMapping
    public ResponseEntity<RecipientModel> createRecipient(@RequestBody RecipientModel recipient) {
        RecipientModel created = recipientUseCase.createRecipient(recipient);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipientModel> getRecipientById(@PathVariable Long id) {
        return recipientUseCase.getRecipientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RecipientModel>> getAllRecipients() {
        List<RecipientModel> recipients = recipientUseCase.getAllRecipients();
        return ResponseEntity.ok(recipients);
    }


    @PutMapping("/{id}")
    public ResponseEntity<RecipientModel> updateRecipient(@PathVariable Long id, @RequestBody RecipientModel recipient) {
        if (!recipientUseCase.recipientExists(id)) {
            return ResponseEntity.notFound().build();
        }
        RecipientModel updated = recipientUseCase.updateRecipient(recipient);
        return ResponseEntity.ok(updated);
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