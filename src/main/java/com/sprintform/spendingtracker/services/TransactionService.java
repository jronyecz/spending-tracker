package com.sprintform.spendingtracker.services;

import com.sprintform.spendingtracker.domain.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAll();

    Transaction getById(Long id);

    Transaction create(Transaction transaction);

    Transaction update(Long id, Transaction transaction);

    void delete(Long id);
}
