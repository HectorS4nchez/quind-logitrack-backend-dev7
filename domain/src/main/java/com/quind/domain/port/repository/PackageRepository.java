package com.quind.domain.port.repository;

import com.quind.domain.model.PackageModel;

import java.util.List;
import java.util.Optional;

public interface PackageRepository {

    PackageModel save(PackageModel pkg);
    Optional<PackageModel> findById(String trackingId);
    List<PackageModel> findAll();
    boolean existsById(String trackingId);
    void deleteById(String trackingId);
    PackageModel update(PackageModel pkg);
}