package com.quind.domain.usecase;

import com.quind.domain.model.RecipientModel;
import com.quind.domain.port.repository.RecipientRepository;

import java.util.List;
import java.util.Optional;

public class RecipientUseCase {

    private final RecipientRepository recipientRepository;

    public RecipientUseCase(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public RecipientModel createRecipient(RecipientModel recipient) {
        return recipientRepository.save(recipient);
    }

    public Optional<RecipientModel> getRecipientById(Long id) {
        return recipientRepository.findById(id);
    }

    public List<RecipientModel> getAllRecipients() {
        return recipientRepository.findAll();
    }

    public boolean recipientExists(Long id) {
        return recipientRepository.existsById(id);
    }

    public RecipientModel updateRecipient(RecipientModel recipient) {
        return recipientRepository.update(recipient);
    }

    public void deleteRecipient(Long id) {
        recipientRepository.deleteById(id);
    }
}