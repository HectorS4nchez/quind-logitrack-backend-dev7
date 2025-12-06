package com.quind.domain.usecase;

import com.quind.domain.model.*;
import com.quind.domain.model.enums.PackageStatus;
import com.quind.domain.port.repository.PackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackageUseCaseTest {

    @Mock
    private PackageRepository packageRepository;

    private PackageUseCase packageUseCase;
    private PackageModel testPackage;

    @BeforeEach
    void setUp() {
        packageUseCase = new PackageUseCase(packageRepository);

        RecipientModel recipient = new RecipientModel("John Doe", "123 Main St");
        DimensionsModel dimensions = new DimensionsModel(10, 20, 30);
        testPackage = new PackageModel("LT-123456", recipient, dimensions, 5.5);
    }

    @Test
    void testCreatePackage() {
        when(packageRepository.save(any(PackageModel.class))).thenReturn(testPackage);

        PackageModel result = packageUseCase.createPackage(testPackage);

        assertNotNull(result);
        assertEquals("LT-123456", result.getTrackingId());
        verify(packageRepository, times(1)).save(testPackage);
    }

    @Test
    void testGetPackageById() {
        when(packageRepository.findById("LT-123456")).thenReturn(Optional.of(testPackage));

        Optional<PackageModel> result = packageUseCase.getPackageById("LT-123456");

        assertTrue(result.isPresent());
        assertEquals("LT-123456", result.get().getTrackingId());
        verify(packageRepository, times(1)).findById("LT-123456");
    }

    @Test
    void testGetPackageByIdNotFound() {
        when(packageRepository.findById("INVALID")).thenReturn(Optional.empty());

        Optional<PackageModel> result = packageUseCase.getPackageById("INVALID");

        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllPackages() {
        List<PackageModel> packages = Arrays.asList(testPackage);
        when(packageRepository.findAll()).thenReturn(packages);

        List<PackageModel> result = packageUseCase.getAllPackages();

        assertEquals(1, result.size());
        verify(packageRepository, times(1)).findAll();
    }

    @Test
    void testUpdatePackage() {
        when(packageRepository.update(any(PackageModel.class))).thenReturn(testPackage);

        PackageModel result = packageUseCase.updatePackage(testPackage);

        assertNotNull(result);
        verify(packageRepository, times(1)).update(testPackage);
    }

    @Test
    void testDeletePackage() {
        doNothing().when(packageRepository).deleteById("LT-123456");

        packageUseCase.deletePackage("LT-123456");

        verify(packageRepository, times(1)).deleteById("LT-123456");
    }

    @Test
    void testChangePackageStatus() {
        when(packageRepository.findById("LT-123456")).thenReturn(Optional.of(testPackage));
        when(packageRepository.update(any(PackageModel.class))).thenReturn(testPackage);

        PackageModel result = packageUseCase.changePackageStatus("LT-123456", PackageStatus.IN_TRANSIT);

        assertEquals(PackageStatus.IN_TRANSIT, result.getStatus());
        verify(packageRepository, times(1)).findById("LT-123456");
        verify(packageRepository, times(1)).update(any(PackageModel.class));
    }

    @Test
    void testChangePackageStatusThrowsExceptionWhenNotFound() {
        when(packageRepository.findById("INVALID")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                packageUseCase.changePackageStatus("INVALID", PackageStatus.IN_TRANSIT)
        );
    }

    @Test
    void testAddLocationToPackage() {
        when(packageRepository.findById("LT-123456")).thenReturn(Optional.of(testPackage));
        when(packageRepository.update(any(PackageModel.class))).thenReturn(testPackage);

        LocalDateTime timestamp = LocalDateTime.now();
        PackageModel result = packageUseCase.addLocationToPackage("LT-123456", "Bogota", "Colombia", timestamp);

        assertEquals(1, result.getLocationHistory().size());
        assertEquals("Bogota", result.getLocationHistory().get(0).getCity());
        verify(packageRepository, times(1)).update(any(PackageModel.class));
    }

    @Test
    void testAddLocationToPackageWithoutTimestamp() {
        when(packageRepository.findById("LT-123456")).thenReturn(Optional.of(testPackage));
        when(packageRepository.update(any(PackageModel.class))).thenReturn(testPackage);

        PackageModel result = packageUseCase.addLocationToPackage("LT-123456", "Madrid", "Spain");

        assertEquals(1, result.getLocationHistory().size());
        assertEquals("Madrid", result.getLocationHistory().get(0).getCity());
    }

    @Test
    void testAddLocationToPackageThrowsExceptionWhenNotFound() {
        when(packageRepository.findById("INVALID")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                packageUseCase.addLocationToPackage("INVALID", "Bogota", "Colombia")
        );
    }

    @Test
    void testPackageExists() {
        when(packageRepository.existsById("LT-123456")).thenReturn(true);

        boolean result = packageUseCase.packageExists("LT-123456");

        assertTrue(result);
    }
}