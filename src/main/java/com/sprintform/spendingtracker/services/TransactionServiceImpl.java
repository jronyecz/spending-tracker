package com.sprintform.spendingtracker.services;

import com.sprintform.spendingtracker.domain.Transaction;
import com.sprintform.spendingtracker.repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getById(Long id) {
        return transactionRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Transaction create(Transaction transaction) {
        return save(null, transaction);
    }

    @Override
    public Transaction update(Long id, Transaction transaction) {
        return save(id, transaction);
    }

    private Transaction save(Long id, Transaction transaction) {
        transaction.setId(id);
        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
