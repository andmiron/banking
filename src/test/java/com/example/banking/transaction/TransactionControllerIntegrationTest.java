package com.example.banking.transaction;

import com.example.banking.TestcontainersConfiguration;
import com.example.banking.account.AccountService;
import com.example.banking.account.CreateAccountDto;
import com.example.banking.account.GetAccountDto;
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

import java.math.BigDecimal;
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
class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Test
    void createTransaction_returnsCreatedTransaction() throws Exception {
        GetAccountDto account = accountService.createAccount(
                new CreateAccountDto(77L, "EE", List.of("EUR"))
        );

        CreateTransactionDto request = new CreateTransactionDto(
                account.accountId(),
                new BigDecimal("150.00"),
                "EUR",
                "IN",
                "Deposit"
        );

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").isNumber())
                .andExpect(jsonPath("$.accountId").value(account.accountId()))
                .andExpect(jsonPath("$.amount").value(150.00))
                .andExpect(jsonPath("$.direction").value("IN"));
    }

    @Test
    void getTransactions_returnsPersistedTransactions() throws Exception {
        GetAccountDto account = accountService.createAccount(
                new CreateAccountDto(88L, "EE", List.of("EUR"))
        );

        transactionService.createTransaction(
                new CreateTransactionDto(account.accountId(), new BigDecimal("25.00"), "EUR", "IN", "First")
        );
        transactionService.createTransaction(
                new CreateTransactionDto(account.accountId(), new BigDecimal("10.00"), "EUR", "IN", "Second")
        );

        mockMvc.perform(get("/accounts/{accountId}/transactions", account.accountId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
