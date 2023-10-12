package com.sprintform.spendingtracker.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprintform.spendingtracker.domain.SumOfCategory;
import com.sprintform.spendingtracker.repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AggregationServiceImpl implements AggregationService {
    final private TransactionRepository transactionRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    public AggregationServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String totalByCategories() {
        String previousMonthStart = LocalDate.now().minusMonths(1).withDayOfMonth(1).toString();
        String currentMonthStart = LocalDate.now().withDayOfMonth(1).toString();

        List<SumOfCategory> sums = transactionRepository.countTotalAmountsByCategory(previousMonthStart, currentMonthStart);
        Map<String, Long> data = sums.stream().collect(Collectors.toMap(SumOfCategory::getCategory, SumOfCategory::getTotal));

        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.warn("Error {} during JSON processing data - {}", e.getMessage(), data);
            throw new RuntimeException(e);
        }
    }
}
