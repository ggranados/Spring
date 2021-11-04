package com.overactive.java.assessment.service;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.repository.TransactionRepository;
import com.overactive.java.assessment.response.TransactionResponseForRewards;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static com.overactive.java.assessment.service.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    TransactionServiceImpl service;

    @Mock
    TransactionRepository repository;

    @Test
    @DisplayName("Service should return a not empty list of TransactionResponseForRewards when findAll")
    void findAll() {

        List<Transaction> data = new ArrayList<>();
        data.add(trxApplicableForBoth);

        when(repository.findAll()).thenReturn(data);

        ArrayList<TransactionResponseForRewards> result = service.findAll();

        verify(repository, times(1)).findAll();

        assertAll(
                ()-> assertNotNull(result, ()-> "Resulting list should not be null"),
                ()-> assertFalse(result.isEmpty(), ()-> "Resulting list should not be empty"),
                ()-> assertTrue(result instanceof ArrayList,()-> "Expected ArrayList result type"),
                ()-> assertTrue(result.get(0) instanceof TransactionResponseForRewards,()-> "Expected elements of TransactionResponseForRewards in result list")
        );
    }

    @Test
    @DisplayName("Service should return a not empty list of applicable TransactionResponseForRewards when findTransactionByClientIdAndApplicable")
    void findAllApplicableTransactionsByClient() {
        List<Transaction> data = new ArrayList<>();
        data.add(trxApplicableForBoth);

        when(repository.findTransactionByClientIdAndApplicable("CLI001", true)).thenReturn(data);

        ArrayList<TransactionResponseForRewards> result = service.findAllApplicableTransactionsByClient("CLI001");

        verify(repository, times(1)).findTransactionByClientIdAndApplicable("CLI001", true);

        assertAll(
                ()-> assertNotNull(result, ()-> "Resulting list should not be null"),
                ()-> assertFalse(result.isEmpty(), ()-> "Resulting list should not be empty"),
                ()-> assertTrue(result instanceof ArrayList,()-> "Expected ArrayList result type"),
                ()-> assertTrue(result.get(0) instanceof TransactionResponseForRewards,()-> "Expected elements of TransactionResponseForRewards in result list"),
                ()-> assertTrue(result.get(0).getApplicable(),()-> "Expected applicable in result list")
        );
    }

    @Test
    @DisplayName("Service should return not empty size 1 array list of TransactionResponseForRewards when save")
    void saveTransaction() {
        when(repository.save(trxApplicableForBoth)).thenReturn(trxApplicableForBoth);

        ArrayList<TransactionResponseForRewards> result = service.saveTransaction(trxApplicableForBoth);

        verify(repository, times(1)).save(any(Transaction.class));

        assertAll(
                ()-> assertNotNull(result, ()-> "Resulting list should not be null"),
                ()-> assertEquals(1,result.size(),  ()-> "Resulting list should not be empty"),
                ()-> assertTrue(result instanceof ArrayList,()-> "Expected ArrayList result type"),
                ()-> assertTrue(result.get(0) instanceof TransactionResponseForRewards,()-> "Expected elements of TransactionResponseForRewards in result list"),
                ()-> assertEquals(result.get(0).getId(), trxApplicableForBoth.getId(), ()-> "Expected same id saved element")
        );
    }

    @Test
    @DisplayName("Service should return not empty size 1 array list of TransactionResponseForRewards when findTransaction")
    void findTransaction() {

        when(repository.findById(1L)).thenReturn(Optional.of(trxApplicableForBoth));

        ArrayList<TransactionResponseForRewards> result = service.findTransaction(1L);

        verify(repository, times(1)).findById(1L);

        assertAll(
                ()-> assertNotNull(result, ()-> "Resulting list should not be null"),
                ()-> assertEquals(1,result.size(),  ()-> "Resulting list should not be empty"),
                ()-> assertTrue(result instanceof ArrayList,()-> "Expected ArrayList result type"),
                ()-> assertTrue(result.get(0) instanceof TransactionResponseForRewards,()-> "Expected elements of TransactionResponseForRewards in result list")
        );
    }

    @Test
    @DisplayName("Service should return an ResponseStatusException when findTransaction of not found transaction")
    void findNotFoundTransaction() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, ()-> service.findTransaction(1L), "Expected response status exception");

        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Service should return an empty array list when removeTransaction")
    void removeTransaction() {
        when(repository.findById(1L)).thenReturn(Optional.of(trxApplicableForBoth));

        ArrayList<TransactionResponseForRewards> result = service.removeTransaction(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(trxApplicableForBoth);

        assertAll(
                ()-> assertNotNull(result, ()-> "Resulting list should not be null"),
                ()-> assertTrue(result.isEmpty(), ()-> "Resulting list should  be empty")
        );
    }

    @Test
    @DisplayName("Service should return an ResponseStatusException when removeTransaction of not found transaction")
    void removeNotFoundTransaction() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, ()->service.removeTransaction(1L), "Expected response status exception");

        verify(repository, times(1)).findById(1L);
        verify(repository, times(0)).delete(any());
    }

    @Test
    @DisplayName("Service should return not empty size 1 array list of TransactionResponseForRewards when editTransaction")
    void editTransaction() {

        when(repository.findById(1L)).thenReturn(Optional.of(trxApplicableForBoth));

        ArrayList<TransactionResponseForRewards> result = service.editTransaction(trxApplicableForBoth);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(trxApplicableForBoth);

        assertAll(
                ()-> assertNotNull(result, ()-> "Resulting list should not be null"),
                ()-> assertEquals(1, result.size(),  ()-> "Resulting list should not be empty"),
                ()-> assertTrue(result instanceof ArrayList,()-> "Expected ArrayList result type"),
                ()-> assertTrue(result.get(0) instanceof TransactionResponseForRewards,()-> "Expected elements of TransactionResponseForRewards in result list")
        );
    }

    @Test
    @DisplayName("Service should return an ResponseStatusException when editTransaction of not found transaction")
    void editNotFoundTransaction() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, ()->service.editTransaction(trxApplicableForBoth), "Expected response status exception");

        verify(repository, times(1)).findById(1L);
        verify(repository, times(0)).save(any());
    }
}