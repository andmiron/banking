package com.example.banking.account;

import com.example.banking.TestcontainersConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@Transactional
class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @Test
    void createAccount_returnsCreatedAccount() throws Exception {
        CreateAccountDto request = new CreateAccountDto(
                3L,
                "EE",
                List.of("EUR", "USD")
        );

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").isNumber())
                .andExpect(jsonPath("$.customerId").value(request.customerId()))
                .andExpect(jsonPath("$.balances", hasSize(2)));
    }

    @Test
    void getAccount_returnsPersistedAccount() throws Exception {
        GetAccountDto created = accountService.createAccount(
                new CreateAccountDto(55L, "EE", List.of("EUR"))
        );

        mockMvc.perform(get("/accounts/{accountId}", created.accountId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(created.accountId()))
                .andExpect(jsonPath("$.customerId").value(created.customerId()))
                .andExpect(jsonPath("$.balances", hasSize(1)));
    }
}
