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

/**
 * Adaptador de repositorio para destinatarios que delega
 * operaciones de persistencia a JPA y mapea entre dominio y entidad. [web:11]
 *
 * @author Hector Andres Sanchez
 */
@Component
@RequiredArgsConstructor
public class RecipientAdapterRepository implements RecipientRepository {

    private final RecipientJpaRepository jpaRepository;
    private final RecipientMapper recipientMapper;

    /**
     * Persiste un destinatario. [web:11]
     *
     * @param recipientModel Modelo a persistir [web:11]
     * @return Modelo persistido con ID generado [web:11]
     * @throws RepositoryOperationException si falla la operación [web:15]
     */
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

    /**
     * Busca un destinatario por ID. [web:11]
     *
     * @param id Identificador del destinatario [web:11]
     * @return Optional con el modelo si existe [web:11]
     * @throws RepositoryOperationException si falla la búsqueda [web:15]
     */
    @Override
    public Optional<RecipientModel> findById(Long id) {
        try {
            return jpaRepository.findById(id)
                    .map(recipientMapper::toDomain);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to find recipient by ID: " + id, ex);
        }
    }

    /**
     * Obtiene todos los destinatarios. [web:11]
     *
     * @return Lista de todos los destinatarios [web:11]
     * @throws RepositoryOperationException si falla la consulta [web:15]
     */
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

    /**
     * Verifica si existe un destinatario por ID. [web:11]
     *
     * @param id Identificador a verificar [web:11]
     * @return true si existe, false en caso contrario [web:11]
     * @throws RepositoryOperationException si falla la verificación [web:15]
     */
    @Override
    public boolean existsById(Long id) {
        try {
            return jpaRepository.existsById(id);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to check recipient existence for ID: " + id, ex);
        }
    }

    /**
     * Elimina un destinatario por ID. [web:11]
     *
     * @param id Identificador del destinatario a eliminar [web:11]
     * @throws RecipientNotFoundException si no existe [web:15]
     * @throws RepositoryOperationException si falla la eliminación [web:15]
     */
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

    /**
     * Actualiza un destinatario existente. [web:11]
     *
     * @param recipientModel Modelo con datos actualizados (ID requerido) [web:11]
     * @return Modelo actualizado [web:11]
     * @throws IllegalArgumentException si ID es null [web:15]
     * @throws RecipientNotFoundException si no existe [web:15]
     * @throws RepositoryOperationException si falla la actualización [web:15]
     */
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

