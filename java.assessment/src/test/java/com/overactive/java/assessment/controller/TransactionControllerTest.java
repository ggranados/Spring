package com.overactive.java.assessment.controller;

import com.overactive.java.assessment.response.TransactionResponseForRewards;
import com.overactive.java.assessment.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static com.overactive.java.assessment.service.TestData.trxApplicableForBoth;
import static org.mockito.Mockito.when;


@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @MockBean
    TransactionService service;

    @MockBean
    CommandLineRunner commandLineRunner;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getAllTransactions() throws Exception {


        ArrayList<TransactionResponseForRewards> data = new ArrayList<>();
        data.add(new TransactionResponseForRewards(trxApplicableForBoth));

        when(service.findAll()).thenReturn(data);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}