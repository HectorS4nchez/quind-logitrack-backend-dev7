package com.quind.repository.adapter;

import com.quind.domain.exception.DuplicateTrackingIdException;
import com.quind.domain.exception.PackageNotFoundException;
import com.quind.domain.exception.RepositoryOperationException;
import com.quind.domain.model.PackageModel;
import com.quind.domain.port.repository.PackageRepository;
import com.quind.repository.adapter.jpa.entity.PackageEntity;
import com.quind.repository.adapter.jpa.mapper.PackageMapper;
import com.quind.repository.adapter.jpa.repository.PackageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de repositorio para paquetes que delega
 * operaciones de persistencia a JPA y mapea entre dominio y entidad. [web:11]
 * Gestiona la relación bidireccional con el historial de ubicaciones. [web:11]
 *
 * @author Hector Andres Sanchez
 */
@Component
@RequiredArgsConstructor
public class PackageAdapterRepository implements PackageRepository {

    private final PackageJpaRepository jpaRepository;
    private final PackageMapper packageMapper;

    /**
     * Persiste un paquete estableciendo la relación bidireccional
     * con su historial de ubicaciones. [web:11]
     *
     * @param pkg Modelo de paquete a persistir [web:11]
     * @return Modelo persistido con relaciones establecidas [web:11]
     * @throws RepositoryOperationException si falla la operación [web:15]
     */
    @Override
    public PackageModel save(PackageModel pkg) {
        PackageEntity entity = packageMapper.toEntity(pkg);

        if (entity.getLocationHistory() != null) {
            entity.getLocationHistory().forEach(lh -> lh.setPackageEntity(entity));
        }

        PackageEntity saved = jpaRepository.save(entity);
        return packageMapper.toDomain(saved);
    }

    /**
     * Busca un paquete por su tracking ID. [web:11]
     *
     * @param trackingId Identificador único de seguimiento del paquete [web:11]
     * @return Optional con el modelo si existe [web:11]
     * @throws RepositoryOperationException si falla la búsqueda [web:15]
     */
    @Override
    public Optional<PackageModel> findById(String trackingId) {
        try {
            return jpaRepository.findById(trackingId)
                    .map(packageMapper::toDomain);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to find package by tracking ID: " + trackingId, ex);
        }
    }

    /**
     * Obtiene todos los paquetes. [web:11]
     *
     * @return Lista de todos los paquetes [web:11]
     * @throws RepositoryOperationException si falla la consulta [web:15]
     */
    @Override
    public List<PackageModel> findAll() {
        try {
            return jpaRepository.findAll().stream()
                    .map(packageMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to retrieve all packages", ex);
        }
    }

    /**
     * Verifica si existe un paquete por tracking ID. [web:11]
     *
     * @param trackingId Identificador de seguimiento a verificar [web:11]
     * @return true si existe, false en caso contrario [web:11]
     * @throws RepositoryOperationException si falla la verificación [web:15]
     */
    @Override
    public boolean existsById(String trackingId) {
        try {
            return jpaRepository.existsById(trackingId);
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to check package existence for tracking ID: " + trackingId, ex);
        }
    }

    /**
     * Elimina un paquete por tracking ID. [web:11]
     *
     * @param trackingId Identificador del paquete a eliminar [web:11]
     * @throws PackageNotFoundException si no existe [web:15]
     * @throws RepositoryOperationException si falla la eliminación [web:15]
     */
    @Override
    public void deleteById(String trackingId) {
        try {
            if (!jpaRepository.existsById(trackingId)) {
                throw new PackageNotFoundException(trackingId);
            }
            jpaRepository.deleteById(trackingId);
        } catch (PackageNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to delete package with tracking ID: " + trackingId, ex);
        }
    }

    /**
     * Actualiza un paquete existente. [web:11]
     *
     * @param pkg Modelo con datos actualizados (tracking ID requerido) [web:11]
     * @return Modelo actualizado [web:11]
     * @throws PackageNotFoundException si no existe [web:15]
     * @throws RepositoryOperationException si falla la actualización [web:15]
     */
    @Override
    public PackageModel update(PackageModel pkg) {
        try {
            if (!jpaRepository.existsById(pkg.getTrackingId())) {
                throw new PackageNotFoundException(pkg.getTrackingId());
            }
            PackageEntity entity = packageMapper.toEntity(pkg);
            PackageEntity updatedEntity = jpaRepository.save(entity);
            return packageMapper.toDomain(updatedEntity);
        } catch (PackageNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RepositoryOperationException("Failed to update package", ex);
        }
    }
}
