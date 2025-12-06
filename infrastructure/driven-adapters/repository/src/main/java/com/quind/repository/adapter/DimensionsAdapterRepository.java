package com.quind.repository.adapter;

import com.quind.domain.exception.DimensionsNotFoundException;
import com.quind.domain.exception.RepositoryOperationException;
import com.quind.domain.model.DimensionsModel;
import com.quind.domain.port.repository.DimensionsRepository;
import com.quind.repository.adapter.jpa.entity.DimensionsEntity;
import com.quind.repository.adapter.jpa.repository.DimensionsJpaRepository;
import com.quind.repository.adapter.jpa.mapper.DimensionsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de repositorio para dimensiones que delega
 * operaciones de persistencia a JPA y mapea entre dominio y entidad. [web:11]
 *
 * @author Hector Andres Sanchez
 */
@Component
@RequiredArgsConstructor
public class DimensionsAdapterRepository implements DimensionsRepository {

    private final DimensionsJpaRepository jpaRepository;
    private final DimensionsMapper dimensionsMapper;

    /**
     * Persiste unas dimensiones. [web:11]
     *
     * @param dimensionsModel Modelo a persistir [web:11]
     * @return Modelo persistido con ID generado [web:11]
     * @throws RepositoryOperationException si falla la operación [web:15]
     */
    @Override
    public DimensionsModel save(DimensionsModel dimensionsModel) {
        try {
            DimensionsEntity entity = dimensionsMapper.toEntity(dimensionsModel);
            DimensionsEntity savedEntity = jpaRepository.save(entity);
            return dimensionsMapper.toDomain(savedEntity);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to save dimensions", ex);
        }
    }

    /**
     * Busca unas dimensiones por ID. [web:11]
     *
     * @param id Identificador de las dimensiones [web:11]
     * @return Optional con el modelo si existe [web:11]
     * @throws RepositoryOperationException si falla la búsqueda [web:15]
     */
    @Override
    public Optional<DimensionsModel> findById(Long id) {
        try {
            return jpaRepository.findById(id)
                    .map(dimensionsMapper::toDomain);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to find dimensions by ID: " + id, ex);
        }
    }

    /**
     * Obtiene todas las dimensiones. [web:11]
     *
     * @return Lista de todas las dimensiones [web:11]
     * @throws RepositoryOperationException si falla la consulta [web:15]
     */
    @Override
    public List<DimensionsModel> findAll() {
        try {
            return jpaRepository.findAll().stream()
                    .map(dimensionsMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to retrieve all dimensions", ex);
        }
    }

    /**
     * Verifica si existen unas dimensiones por ID. [web:11]
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
            throw new RepositoryOperationException("Failed to check dimensions existence for ID: " + id, ex);
        }
    }

    /**
     * Elimina unas dimensiones por ID. [web:11]
     *
     * @param id Identificador de las dimensiones a eliminar [web:11]
     * @throws DimensionsNotFoundException si no existe [web:15]
     * @throws RepositoryOperationException si falla la eliminación [web:15]
     */
    @Override
    public void deleteById(Long id) {
        try {
            if (!jpaRepository.existsById(id)) {
                throw new DimensionsNotFoundException(id);
            }
            jpaRepository.deleteById(id);
        } catch (DimensionsNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to delete dimensions with ID: " + id, ex);
        }
    }

    /**
     * Actualiza unas dimensiones existentes. [web:11]
     *
     * @param dimensionsModel Modelo con datos actualizados (ID requerido) [web:11]
     * @return Modelo actualizado [web:11]
     * @throws DimensionsNotFoundException si no existe o ID es null [web:15]
     * @throws RepositoryOperationException si falla la actualización [web:15]
     */
    @Override
    public DimensionsModel update(DimensionsModel dimensionsModel) {
        try {
            if (dimensionsModel.getId() == null || !jpaRepository.existsById(dimensionsModel.getId())) {
                throw new DimensionsNotFoundException(dimensionsModel.getId());
            }
            DimensionsEntity entity = dimensionsMapper.toEntity(dimensionsModel);
            DimensionsEntity updatedEntity = jpaRepository.save(entity);
            return dimensionsMapper.toDomain(updatedEntity);
        } catch (DimensionsNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to update dimensions", ex);
        }
    }
}
