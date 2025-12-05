package com.quind.repository.adapter;

import com.quind.domain.exception.LocationHistoryNotFoundException;
import com.quind.domain.exception.RepositoryOperationException;
import com.quind.domain.model.LocationHistoryModel;
import com.quind.domain.port.repository.LocationHistoryRepository;
import com.quind.repository.adapter.jpa.entity.LocationHistoryEntity;
import com.quind.repository.adapter.jpa.repository.LocationHistoryJpaRepository;
import com.quind.repository.adapter.jpa.mapper.LocationHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de repositorio para historial de ubicación que delega
 * operaciones de persistencia a JPA y mapea entre dominio y entidad. [web:11]
 *
 * @author Hector Andres Sanchez
 */
@Component
@RequiredArgsConstructor
public class LocationHistoryAdapterRepository implements LocationHistoryRepository {

    private final LocationHistoryJpaRepository jpaRepository;
    private final LocationHistoryMapper locationHistoryMapper;

    /**
     * Persiste un historial de ubicación. [web:11]
     *
     * @param locationHistoryModel Modelo a persistir [web:11]
     * @return Modelo persistido con ID generado [web:11]
     * @throws RepositoryOperationException si falla la operación [web:15]
     */
    @Override
    public LocationHistoryModel save(LocationHistoryModel locationHistoryModel) {
        try {
            LocationHistoryEntity entity = locationHistoryMapper.toEntity(locationHistoryModel);
            LocationHistoryEntity savedEntity = jpaRepository.save(entity);
            return locationHistoryMapper.toDomain(savedEntity);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to save location history", ex);
        }
    }

    /**
     * Busca un historial por ID. [web:11]
     *
     * @param id Identificador del historial [web:11]
     * @return Optional con el modelo si existe [web:11]
     * @throws RepositoryOperationException si falla la búsqueda [web:15]
     */
    @Override
    public Optional<LocationHistoryModel> findById(Long id) {
        try {
            return jpaRepository.findById(id)
                    .map(locationHistoryMapper::toDomain);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to find location history by ID: " + id, ex);
        }
    }

    /**
     * Obtiene todos los historiales. [web:11]
     *
     * @return Lista de todos los historiales [web:11]
     * @throws RepositoryOperationException si falla la consulta [web:15]
     */
    @Override
    public List<LocationHistoryModel> findAll() {
        try {
            return jpaRepository.findAll().stream()
                    .map(locationHistoryMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to retrieve all location histories", ex);
        }
    }

    /**
     * Verifica si existe un historial por ID. [web:11]
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
            throw new RepositoryOperationException("Failed to check location history existence for ID: " + id, ex);
        }
    }

    /**
     * Elimina un historial por ID. [web:11]
     *
     * @param id Identificador del historial a eliminar [web:11]
     * @throws LocationHistoryNotFoundException si no existe [web:15]
     * @throws RepositoryOperationException si falla la eliminación [web:15]
     */
    @Override
    public void deleteById(Long id) {
        try {
            if (!jpaRepository.existsById(id)) {
                throw new LocationHistoryNotFoundException(id);
            }
            jpaRepository.deleteById(id);
        } catch (LocationHistoryNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to delete location history with ID: " + id, ex);
        }
    }

    /**
     * Actualiza un historial existente. [web:11]
     *
     * @param locationHistoryModel Modelo con datos actualizados (ID requerido) [web:11]
     * @return Modelo actualizado [web:11]
     * @throws IllegalArgumentException si ID es null [web:15]
     * @throws LocationHistoryNotFoundException si no existe [web:15]
     * @throws RepositoryOperationException si falla la actualización [web:15]
     */
    @Override
    public LocationHistoryModel update(LocationHistoryModel locationHistoryModel) {
        try {
            if (locationHistoryModel.getId() == null) {
                throw new IllegalArgumentException("Location history ID cannot be null for update");
            }
            if (!jpaRepository.existsById(locationHistoryModel.getId())) {
                throw new LocationHistoryNotFoundException(locationHistoryModel.getId());
            }
            LocationHistoryEntity entity = locationHistoryMapper.toEntity(locationHistoryModel);
            LocationHistoryEntity updatedEntity = jpaRepository.save(entity);
            return locationHistoryMapper.toDomain(updatedEntity);
        } catch (LocationHistoryNotFoundException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to update location history", ex);
        }
    }
}
