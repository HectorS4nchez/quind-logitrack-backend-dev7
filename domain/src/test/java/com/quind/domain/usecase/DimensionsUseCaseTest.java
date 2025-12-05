package com.quind.domain.usecase;

import com.quind.domain.model.DimensionsModel;
import com.quind.domain.port.repository.DimensionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DimensionsUseCaseTest {

    @Mock
    private DimensionsRepository dimensionsRepository;

    private DimensionsUseCase dimensionsUseCase;
    private DimensionsModel testDimensions;

    @BeforeEach
    void setUp() {
        dimensionsUseCase = new DimensionsUseCase(dimensionsRepository);
        testDimensions = new DimensionsModel(10.0, 20.0, 30.0);
    }

    @Test
    void testCreateDimensions() {
        when(dimensionsRepository.save(any(DimensionsModel.class))).thenReturn(testDimensions);

        DimensionsModel result = dimensionsUseCase.createDimensions(testDimensions);

        assertNotNull(result);
        assertEquals(10.0, result.getHeight());
        assertEquals(20.0, result.getWidth());
        assertEquals(30.0, result.getDepth());
        verify(dimensionsRepository, times(1)).save(testDimensions);
    }

    @Test
    void testGetDimensionsById() {
        when(dimensionsRepository.findById(1L)).thenReturn(Optional.of(testDimensions));

        Optional<DimensionsModel> result = dimensionsUseCase.getDimensionsById(1L);

        assertTrue(result.isPresent());
        assertEquals(10.0, result.get().getHeight());
        verify(dimensionsRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDimensionsByIdNotFound() {
        when(dimensionsRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<DimensionsModel> result = dimensionsUseCase.getDimensionsById(999L);

        assertFalse(result.isPresent());
        verify(dimensionsRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAllDimensions() {
        List<DimensionsModel> dimensionsList = Arrays.asList(testDimensions);
        when(dimensionsRepository.findAll()).thenReturn(dimensionsList);

        List<DimensionsModel> result = dimensionsUseCase.getAllDimensions();

        assertEquals(1, result.size());
        assertEquals(10.0, result.get(0).getHeight());
        verify(dimensionsRepository, times(1)).findAll();
    }

    @Test
    void testDimensionsExists() {
        when(dimensionsRepository.existsById(1L)).thenReturn(true);

        boolean result = dimensionsUseCase.dimensionsExists(1L);

        assertTrue(result);
        verify(dimensionsRepository, times(1)).existsById(1L);
    }

    @Test
    void testDimensionsDoesNotExist() {
        when(dimensionsRepository.existsById(999L)).thenReturn(false);

        boolean result = dimensionsUseCase.dimensionsExists(999L);

        assertFalse(result);
        verify(dimensionsRepository, times(1)).existsById(999L);
    }

    @Test
    void testUpdateDimensions() {
        DimensionsModel updatedDimensions = new DimensionsModel(15.0, 25.0, 35.0);
        when(dimensionsRepository.update(any(DimensionsModel.class))).thenReturn(updatedDimensions);

        DimensionsModel result = dimensionsUseCase.updateDimensions(updatedDimensions);

        assertNotNull(result);
        assertEquals(15.0, result.getHeight());
        assertEquals(25.0, result.getWidth());
        assertEquals(35.0, result.getDepth());
        verify(dimensionsRepository, times(1)).update(updatedDimensions);
    }

    @Test
    void testDeleteDimensions() {
        doNothing().when(dimensionsRepository).deleteById(1L);

        dimensionsUseCase.deleteDimensions(1L);

        verify(dimensionsRepository, times(1)).deleteById(1L);
    }
}