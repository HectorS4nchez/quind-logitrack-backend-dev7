package com.quind.domain.port.repository;

import com.quind.domain.model.RecipientModel;

import java.util.List;
import java.util.Optional;

public interface RecipientRepository {
    RecipientModel save(RecipientModel recipientModel);
    Optional<RecipientModel> findById(Long id);
    List<RecipientModel> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
    RecipientModel update(RecipientModel recipientModel);
}
