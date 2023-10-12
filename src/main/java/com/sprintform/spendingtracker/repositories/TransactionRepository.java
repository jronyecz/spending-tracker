package com.sprintform.spendingtracker.repositories;

import com.sprintform.spendingtracker.domain.SumOfCategory;
import com.sprintform.spendingtracker.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT new com.sprintform.spendingtracker.domain.SumOfCategory(t.category, SUM(t.sum)) "
            + "FROM Transaction AS t GROUP BY t.category ORDER BY SUM(t.sum) DESC")
    List<SumOfCategory> countTotalAmountsByCategory();

}
