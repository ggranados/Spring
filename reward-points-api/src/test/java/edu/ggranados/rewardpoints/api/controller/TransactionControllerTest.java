package edu.ggranados.rewardpoints.api.controller;

import edu.ggranados.rewardpoints.api.response.TransactionResponseForRewards;
import edu.ggranados.rewardpoints.api.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static edu.ggranados.rewardpoints.api.service.TestData.trxApplicableForBoth;
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