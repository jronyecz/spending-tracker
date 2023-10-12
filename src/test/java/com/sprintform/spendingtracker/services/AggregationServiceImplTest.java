package com.sprintform.spendingtracker.services;

import com.sprintform.spendingtracker.domain.SumOfCategory;
import com.sprintform.spendingtracker.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AggregationServiceImplTest {

    AggregationService aggregationService;

    @Mock
    TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        aggregationService = new AggregationServiceImpl(transactionRepository);
    }

    @Test
    void totalByCategories() {
        List<SumOfCategory> sums = Arrays.asList(new SumOfCategory("cat1", 12345L), new SumOfCategory("cat2", 54321L));
        when(transactionRepository.countTotalAmountsByCategory(anyString(), anyString())).thenReturn(sums);

        String result = aggregationService.totalByCategories();

        assertEquals(result, "{\"cat2\":54321,\"cat1\":12345}");
    }

}