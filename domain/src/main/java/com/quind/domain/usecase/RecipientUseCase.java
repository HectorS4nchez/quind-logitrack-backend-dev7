package com.quind.domain.usecase;

import com.quind.domain.port.repository.RecipientRepository;

public class RecipientUseCase {

    private final RecipientRepository recipientRepository;

    public RecipientUseCase(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }
}
