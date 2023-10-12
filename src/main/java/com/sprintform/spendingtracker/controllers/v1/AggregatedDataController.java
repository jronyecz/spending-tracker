package com.sprintform.spendingtracker.controllers.v1;

import com.sprintform.spendingtracker.services.AggregationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AggregatedDataController.BASE_URL)
public class AggregatedDataController {

    public static final String BASE_URL = "/api/v1/totalsByCategory";

    private final AggregationService aggregationService;

    public AggregatedDataController(AggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String getTotalsTransactionByCategoryInLastMonth() {
        return aggregationService.totalByCategories();
    }
}
