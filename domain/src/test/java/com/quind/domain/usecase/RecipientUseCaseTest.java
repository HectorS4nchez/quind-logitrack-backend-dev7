package com.quind.domain.usecase;

import com.quind.domain.model.RecipientModel;
import com.quind.domain.port.repository.RecipientRepository;
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
class RecipientUseCaseTest {

    @Mock
    private RecipientRepository recipientRepository;

    private RecipientUseCase recipientUseCase;
    private RecipientModel testRecipient;

    @BeforeEach
    void setUp() {
        recipientUseCase = new RecipientUseCase(recipientRepository);
        testRecipient = new RecipientModel("John Doe", "123 Main St, Bogota");
    }

    @Test
    void testCreateRecipient() {
        when(recipientRepository.save(any(RecipientModel.class))).thenReturn(testRecipient);

        RecipientModel result = recipientUseCase.createRecipient(testRecipient);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("123 Main St, Bogota", result.getAddress());
        verify(recipientRepository, times(1)).save(testRecipient);
    }

    @Test
    void testGetRecipientById() {
        when(recipientRepository.findById(1L)).thenReturn(Optional.of(testRecipient));

        Optional<RecipientModel> result = recipientUseCase.getRecipientById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(recipientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRecipientByIdNotFound() {
        when(recipientRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<RecipientModel> result = recipientUseCase.getRecipientById(999L);

        assertFalse(result.isPresent());
        verify(recipientRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAllRecipients() {
        List<RecipientModel> recipients = Arrays.asList(testRecipient);
        when(recipientRepository.findAll()).thenReturn(recipients);

        List<RecipientModel> result = recipientUseCase.getAllRecipients();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(recipientRepository, times(1)).findAll();
    }

    @Test
    void testRecipientExists() {
        when(recipientRepository.existsById(1L)).thenReturn(true);

        boolean result = recipientUseCase.recipientExists(1L);

        assertTrue(result);
        verify(recipientRepository, times(1)).existsById(1L);
    }

    @Test
    void testRecipientDoesNotExist() {
        when(recipientRepository.existsById(999L)).thenReturn(false);

        boolean result = recipientUseCase.recipientExists(999L);

        assertFalse(result);
        verify(recipientRepository, times(1)).existsById(999L);
    }

    @Test
    void testUpdateRecipient() {
        RecipientModel updatedRecipient = new RecipientModel("Jane Doe", "456 Oak Ave");
        when(recipientRepository.update(any(RecipientModel.class))).thenReturn(updatedRecipient);

        RecipientModel result = recipientUseCase.updateRecipient(updatedRecipient);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("456 Oak Ave", result.getAddress());
        verify(recipientRepository, times(1)).update(updatedRecipient);
    }

    @Test
    void testDeleteRecipient() {
        doNothing().when(recipientRepository).deleteById(1L);

        recipientUseCase.deleteRecipient(1L);

        verify(recipientRepository, times(1)).deleteById(1L);
    }
}