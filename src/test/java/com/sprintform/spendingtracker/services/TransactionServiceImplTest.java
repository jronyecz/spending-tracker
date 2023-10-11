package com.sprintform.spendingtracker.services;

import com.sprintform.spendingtracker.domain.Transaction;
import com.sprintform.spendingtracker.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        transactionService = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    void getAllTransactions() {
        List<Transaction> transactions = Arrays.asList(new Transaction(), new Transaction(), new Transaction());
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.getAll();

        assertEquals(3, result.size());
    }

    @Test
    void getTransactionById() {
        final Long ID = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(ID);
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        Transaction result = transactionService.getById(ID);

        assertEquals(ID, result.getId());
    }

    @Test
    void getTransactionByIdThrowsException_whenNotFoundItemById() {
        when(transactionRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> transactionService.getById(1L));
    }

    @Test
    void createTransaction() {
        Transaction transaction = new Transaction();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        Transaction result = transactionService.create(new Transaction());

        assertNotNull(result);
    }

    @Test
    void updateTransaction() {
        final Long ID = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(ID);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        Transaction result = transactionService.update(ID, new Transaction());

        assertEquals(ID, result.getId());
    }

    @Test
    void deleteTransaction() {
        final Long ID = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(ID);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        Transaction result = transactionService.update(ID, new Transaction());

        assertEquals(ID, result.getId());
    }
}