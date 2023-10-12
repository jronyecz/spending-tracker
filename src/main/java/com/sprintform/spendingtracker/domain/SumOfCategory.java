package com.sprintform.spendingtracker.domain;

import lombok.Data;

@Data
public class SumOfCategory {
    final String category;
    final Long total;
}
