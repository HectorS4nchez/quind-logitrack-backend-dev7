package com.quind.domain.usecase;

import com.quind.domain.model.PackageModel;
import com.quind.domain.model.enums.PackageStatus;
import com.quind.domain.port.repository.PackageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PackageUseCase {

    private final PackageRepository packageRepository;

    public PackageUseCase(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public PackageModel createPackage(PackageModel pkg) {
        return packageRepository.save(pkg);
    }

    public Optional<PackageModel> getPackageById(String trackingId) {
        return packageRepository.findById(trackingId);
    }

    public List<PackageModel> getAllPackages() {
        return packageRepository.findAll();
    }

    public boolean packageExists(String trackingId) {
        return packageRepository.existsById(trackingId);
    }

    public PackageModel updatePackage(PackageModel pkg) {
        return packageRepository.update(pkg);
    }

    public void deletePackage(String trackingId) {
        packageRepository.deleteById(trackingId);
    }

    public PackageModel changePackageStatus(String trackingId, PackageStatus newStatus) {
        Optional<PackageModel> packageOpt = packageRepository.findById(trackingId);
        if (packageOpt.isPresent()) {
            PackageModel pkg = packageOpt.get();
            pkg.changeStatus(newStatus);
            return packageRepository.update(pkg);
        }
        throw new RuntimeException("Package not found with tracking id: " + trackingId);
    }

    public PackageModel addLocationToPackage(String trackingId, String city, String country, LocalDateTime timestamp) {
        Optional<PackageModel> packageOpt = packageRepository.findById(trackingId);
        if (packageOpt.isPresent()) {
            PackageModel pkg = packageOpt.get();
            pkg.addLocation(city, country, timestamp);
            return packageRepository.update(pkg);
        }
        throw new RuntimeException("Package not found with tracking id: " + trackingId);
    }

    public PackageModel addLocationToPackage(String trackingId, String city, String country) {
        return addLocationToPackage(trackingId, city, country, LocalDateTime.now());
    }
}