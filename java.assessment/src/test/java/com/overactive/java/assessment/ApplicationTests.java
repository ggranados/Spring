package com.overactive.java.assessment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Given Transactions When RequestTransactions Then ApplicableResponseTransactions")
	void givenTransaction_WhenRequestTransactions_ThenApplicableResponseTransactions() throws Exception {
		mockMvc.perform(get("/api/v1/transactions"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data[0].id").isNotEmpty())
				.andExpect(jsonPath("$.data[0].id").isNumber())
				.andExpect(jsonPath("$.data[0].amount").isNotEmpty())
				.andExpect(jsonPath("$.data[0].amount").isNumber())
				.andExpect(jsonPath("$.data[0].date").isNotEmpty())
				.andExpect(jsonPath("$.data[0].applicable", is(true)))
				.andExpect(jsonPath("$.meta").exists())
				.andExpect(jsonPath("$.meta.responseTime").isNotEmpty())
				.andExpect(jsonPath("$.meta.apiVersion", is("v1")))
				.andExpect(jsonPath("$.meta.responseCode", is("200 OK")))
				.andExpect(jsonPath("$.meta.errorMessage").isEmpty())
		;
	}

	@Test
	@DisplayName("Given ExistingClientId When RequestRewardsByClient Then ResponseClientRewards")
	void givenExistingClientId_WhenRequestRewardsByClient_ThenResponseClientRewards() throws Exception {
		mockMvc.perform(get("/api/v1/rewards/clients/CLI001"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data[0].points").exists())
				.andExpect(jsonPath("$.data[0].points").isNumber())
				.andExpect(jsonPath("$.data[0].points").isNotEmpty())
				.andExpect(jsonPath("$.data[0].clientId", is("CLI001")))
				.andExpect(jsonPath("$.data[1]").doesNotExist())
				.andExpect(jsonPath("$.meta").exists())
				.andExpect(jsonPath("$.meta.responseTime").isNotEmpty())
				.andExpect(jsonPath("$.meta.apiVersion", is("v1")))
				.andExpect(jsonPath("$.meta.responseCode", is("200 OK")))
				.andExpect(jsonPath("$.meta.errorMessage").isEmpty());

	}

	@Test
	@DisplayName("Given NoClientId When RequestRewardsByClient Then NotFound")
	void givenNoClientId_WhenRequestRewardsByClient_ThenNotFound() throws Exception {
		mockMvc.perform(get("/api/v1/rewards/clients/ /"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.meta").exists())
				.andExpect(jsonPath("$.meta.responseTime").isNotEmpty())
				.andExpect(jsonPath("$.meta.apiVersion", is("v1")))
				.andExpect(jsonPath("$.meta.responseCode", is("404")))
				.andExpect(jsonPath("$.meta.errorMessage", is("Rewards points for client:   not found")));
	}

	@Test
	@DisplayName("Given ExistingClientId When RequestRewardsByClientMonthly Then ResponseClientRewards")
	void givenExistingClientId_WhenRequestRewardsByClientMonthly_ThenResponseClientRewards() throws Exception {
		mockMvc.perform(get("/api/v1/rewards/clients/CLI001/monthly"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data[0].points").exists())
				.andExpect(jsonPath("$.data[0].points").isNumber())
				.andExpect(jsonPath("$.data[0].points").isNotEmpty())
				.andExpect(jsonPath("$.data[0].month").exists())
				.andExpect(jsonPath("$.data[0].month").isString())
				.andExpect(jsonPath("$.data[0].month").isNotEmpty())
				.andExpect(jsonPath("$.meta").exists())
				.andExpect(jsonPath("$.meta.responseTime").isNotEmpty())
				.andExpect(jsonPath("$.meta.apiVersion", is("v1")))
				.andExpect(jsonPath("$.meta.responseCode", is("200 OK")))
				.andExpect(jsonPath("$.meta.errorMessage").isEmpty());

	}

	@Test
	@DisplayName("Given ExistingClientId When RequestRewardsByClientTotal Then ResponseClientRewards")
	void givenExistingClientId_WhenRequestRewardsByClientTotal_ThenResponseClientRewards() throws Exception {
		mockMvc.perform(get("/api/v1/rewards/clients/CLI001/total"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data").isNotEmpty())
				.andExpect(jsonPath("$.data[0].points").exists())
				.andExpect(jsonPath("$.data[0].points").isNumber())
				.andExpect(jsonPath("$.data[0].points").isNotEmpty())
				.andExpect(jsonPath("$.data[0].clientId", is("CLI001")))
				.andExpect(jsonPath("$.meta").exists())
				.andExpect(jsonPath("$.meta.responseTime").isNotEmpty())
				.andExpect(jsonPath("$.meta.apiVersion", is("v1")))
				.andExpect(jsonPath("$.meta.responseCode", is("200 OK")))
				.andExpect(jsonPath("$.meta.errorMessage").isEmpty());

	}


	@Test
	@DisplayName("Given NoClientId When RequestRewardsByClientMonthly Then NotFound")
	void givenNoClientId_WhenRequestRewardsByClientMonthly_NotFound() throws Exception {
		mockMvc.perform(get("/api/v1/rewards/clients/ /monthly"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.meta").exists())
				.andExpect(jsonPath("$.meta.responseTime").isNotEmpty())
				.andExpect(jsonPath("$.meta.apiVersion", is("v1")))
				.andExpect(jsonPath("$.meta.responseCode", is("404")))
				.andExpect(jsonPath("$.meta.errorMessage", is("Rewards points for client: Optional[ ] not found")));
				}

}
