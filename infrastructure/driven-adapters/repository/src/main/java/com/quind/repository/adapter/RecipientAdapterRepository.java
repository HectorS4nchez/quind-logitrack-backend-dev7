package com.quind.repository.adapter;

import com.quind.domain.model.RecipientModel;
import com.quind.domain.port.repository.RecipientRepository;
import com.quind.repository.adapter.jpa.entity.RecipientEntity;
import com.quind.repository.adapter.jpa.repository.RecipientJpaRepository;
import com.quind.repository.adapter.jpa.mapper.RecipientMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RecipientAdapterRepository implements RecipientRepository {

    private final RecipientJpaRepository jpaRepository;

    public RecipientAdapterRepository(RecipientJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public RecipientModel save(RecipientModel recipientModel) {
        RecipientEntity entity = RecipientMapper.toEntity(recipientModel);
        RecipientEntity savedEntity = jpaRepository.save(entity);
        return RecipientMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RecipientModel> findById(Long id) {
        return jpaRepository.findById(id)
                .map(RecipientMapper::toDomain);
    }

    @Override
    public List<RecipientModel> findAll() {
        return jpaRepository.findAll().stream()
                .map(RecipientMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipientModel> findByName(String name) {
        return jpaRepository.findByName(name).stream()
                .map(RecipientMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public RecipientModel update(RecipientModel recipientModel) {
        RecipientEntity entity = RecipientMapper.toEntity(recipientModel);
        RecipientEntity updatedEntity = jpaRepository.save(entity);
        return RecipientMapper.toDomain(updatedEntity);
    }
}