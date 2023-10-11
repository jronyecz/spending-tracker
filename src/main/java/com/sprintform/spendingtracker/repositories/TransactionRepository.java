package com.sprintform.spendingtracker.repositories;

import com.sprintform.spendingtracker.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
