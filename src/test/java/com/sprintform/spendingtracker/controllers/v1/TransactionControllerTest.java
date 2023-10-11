package com.sprintform.spendingtracker.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprintform.spendingtracker.domain.Transaction;
import com.sprintform.spendingtracker.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    TransactionService transactionService;

    @InjectMocks
    TransactionController transactionController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void getAllTransactions() throws Exception {
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionService.getAll()).thenReturn(transactions);

        mockMvc.perform(get(TransactionController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", hasSize(2)));
    }

    @Test
    void getTransaction() throws Exception {
        final Long ID = 1L;
        String category = "housing";
        Transaction transaction = new Transaction();
        transaction.setId(ID);
        transaction.setCategory(category);

        when(transactionService.getById(ID)).thenReturn(transaction);

        mockMvc.perform(get(TransactionController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category", equalTo(category)));
    }

    @Test
    void createTransaction() throws Exception {
        String category = "housing";
        Transaction transaction = new Transaction();
        transaction.setCategory(category);

        Transaction newtransaction = new Transaction();
        newtransaction.setCategory(category);
        newtransaction.setId(1L);

        when(transactionService.create(transaction)).thenReturn(newtransaction);

        mockMvc.perform(post(TransactionController.BASE_URL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(transaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    void updateTransaction() throws Exception {
        String category = "housing";
        Transaction transaction = new Transaction();
        transaction.setCategory(category);

        final Long ID = 1L;
        Transaction newtransaction = new Transaction();
        newtransaction.setCategory(category);
        newtransaction.setId(ID);

        when(transactionService.update(ID, transaction)).thenReturn(newtransaction);

        mockMvc.perform(put(TransactionController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.category", equalTo(category)));
    }

    @Test
    void deleteTransaction() throws Exception {
        final Long ID = 1L;

        transactionService.delete(ID);

        mockMvc.perform(delete(TransactionController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}