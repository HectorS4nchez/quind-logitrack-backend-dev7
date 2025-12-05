package com.quind.repository.adapter;

import com.quind.domain.exception.RecipientNotFoundException;
import com.quind.domain.exception.RepositoryOperationException;
import com.quind.domain.model.RecipientModel;
import com.quind.domain.port.repository.RecipientRepository;
import com.quind.repository.adapter.jpa.entity.RecipientEntity;
import com.quind.repository.adapter.jpa.repository.RecipientJpaRepository;
import com.quind.repository.adapter.jpa.mapper.RecipientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipientAdapterRepository implements RecipientRepository {

    private final RecipientJpaRepository jpaRepository;
    private final RecipientMapper  recipientMapper;

    @Override
    public RecipientModel save(RecipientModel recipientModel) {
        try {
            RecipientEntity entity = recipientMapper.toEntity(recipientModel);
            RecipientEntity savedEntity = jpaRepository.save(entity);
            return recipientMapper.toDomain(savedEntity);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to save recipient", ex);
        }
    }

    @Override
    public Optional<RecipientModel> findById(Long id) {
        try {
            return jpaRepository.findById(id)
                    .map(recipientMapper::toDomain);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to find recipient by ID: " + id, ex);
        }
    }

    @Override
    public List<RecipientModel> findAll() {
        try {
            return jpaRepository.findAll().stream()
                    .map(recipientMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to retrieve all recipients", ex);
        }
    }

    @Override
    public List<RecipientModel> findByName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Recipient name cannot be null or empty");
            }
            return jpaRepository.findByName(name).stream()
                    .map(recipientMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to find recipients by name: " + name, ex);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            return jpaRepository.existsById(id);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to check recipient existence for ID: " + id, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            if (!jpaRepository.existsById(id)) {
                throw new RecipientNotFoundException(id);
            }
            jpaRepository.deleteById(id);
        } catch (RecipientNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to delete recipient with ID: " + id, ex);
        }
    }

    @Override
    public RecipientModel update(RecipientModel recipientModel) {
        try {
            if (recipientModel.getId() == null) {
                throw new IllegalArgumentException("Recipient ID cannot be null for update");
            }
            if (!jpaRepository.existsById(recipientModel.getId())) {
                throw new RecipientNotFoundException(recipientModel.getId());
            }
            RecipientEntity entity = recipientMapper.toEntity(recipientModel);
            RecipientEntity updatedEntity = jpaRepository.save(entity);
            return recipientMapper.toDomain(updatedEntity);
        } catch (RecipientNotFoundException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to update recipient", ex);
        }
    }
}
