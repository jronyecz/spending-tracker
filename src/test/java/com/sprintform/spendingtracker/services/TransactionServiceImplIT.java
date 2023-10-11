package com.sprintform.spendingtracker.services;

import com.sprintform.spendingtracker.domain.Transaction;
import com.sprintform.spendingtracker.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TransactionServiceImplIT {

    @Autowired
    TransactionRepository transactionRepository;

    TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        transactionService = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    void getAllTransactions() {
        List<Transaction> result = transactionService.getAll();

        assertEquals(7, result.size());
        assertEquals(result.get(0).getCategory(), "housing");
    }

    @Test
    void getTransactionById() {
        final Long ID = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(ID);
        transaction.setSummary("albérlet és rezsi április");
        transaction.setCategory("housing");
        transaction.setSum(175000);
        transaction.setCurrency("HUF");
        transaction.setPaid("2022-04-20T12:56:00Z");

        Transaction result = transactionService.getById(ID);

        assertEquals(result, transaction);
    }

    @Test
    void getTransactionByIdThrowsException_whenNotFoundItemById() {
        final Long ID_NOT_EXIST = 1000L;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> transactionService.getById(ID_NOT_EXIST));
    }

    @Test
    void createNewTransaction() {
        Transaction transaction = new Transaction();
        transaction.setSummary("albérlet és rezsi április");
        transaction.setCategory("housing");
        transaction.setSum(175000);
        transaction.setCurrency("HUF");
        transaction.setPaid("2022-04-20T12:56:00Z");

        Transaction result = transactionService.create(transaction);

        assertEquals(result.getSummary(), transaction.getSummary());
        assertTrue(transactionRepository.existsById(result.getId()));
    }

    @Test
    void createTransaction_withNewId() {
        final Long ID = 3L;
        assertTrue(transactionRepository.existsById(ID));

        Transaction transaction = new Transaction();
        transaction.setId(ID);
        transaction.setSummary("albérlet és rezsi április");
        transaction.setCategory("housing");
        transaction.setSum(175000);
        transaction.setCurrency("HUF");
        transaction.setPaid("2022-04-20T12:56:00Z");

        Transaction result = transactionService.create(transaction);

        assertNotEquals(result.getId(), ID);
        assertTrue(transactionRepository.existsById(result.getId()));
    }

    @Test
    void updateTransaction() {
        final Long ID = 1L;

        Transaction transaction = new Transaction();
        transaction.setSummary("something");
        transaction.setCategory("food");
        transaction.setSum(75000);
        transaction.setCurrency("HUF");
        transaction.setPaid("2022-04-20T12:56:00Z");

        Transaction result = transactionService.update(ID, transaction);

        assertEquals(result.getId(), ID);
        assertEquals(result.getSummary(), transaction.getSummary());
        assertEquals(result.getCategory(), transaction.getCategory());
    }

    @Test
    void deleteTransaction() {
        final Long ID = 1L;
        Assertions.assertTrue(transactionRepository.existsById(ID));

        transactionService.delete(ID);

        Assertions.assertFalse(transactionRepository.existsById(ID));
    }

    @Test
    void deleteTransaction_whenTransactionNotExist() {
        final Long ID = 15L;
        Assertions.assertFalse(transactionRepository.existsById(ID));

        transactionService.delete(ID);
    }
}
