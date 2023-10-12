package com.sprintform.spendingtracker.controllers.v1;

import com.sprintform.spendingtracker.services.AggregationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AggregatedDataControllerTest {

    @Mock
    AggregationService aggregationService;

    @InjectMocks
    AggregatedDataController aggregatedDataController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(aggregatedDataController).build();
    }

    @Test
    void getTotalTransactionByCategory() throws Exception {
        when(aggregationService.totalByCategories()).thenReturn("{\"housing\":175000,\"clothing\":12000,\"travel\":5050,\"food\":4150}");

        mockMvc.perform(get(AggregatedDataController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.housing", equalTo(175000)))
                .andExpect(jsonPath("$.clothing", equalTo(12000)))
                .andExpect(jsonPath("$.travel", equalTo(5050)))
                .andExpect(jsonPath("$.food", equalTo(4150)));
    }
}