package com.example.banking;

import com.example.banking.account.AccountService;
import com.example.banking.account.CreateAccountDto;
import com.example.banking.account.GetAccountDto;
import com.example.banking.balance.BalanceDto;
import com.example.banking.common.exception.AccountNotFoundException;
import com.example.banking.common.exception.InvalidCurrencyException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@Transactional
class AccountServiceIntegrationTest {

    @Autowired
    AccountService accountService;

    @Test
    void createAccount_persistsBalancesAndReturnsDto() {
        CreateAccountDto request = new CreateAccountDto(
                1L,
                "EE",
                List.of("eur", "USD")
        );

        GetAccountDto createdAccount = accountService.createAccount(request);

        assertNotNull(createdAccount.accountId());
        assertEquals(createdAccount.customerId(), request.customerId());

        List<BalanceDto> fetchedBalances = accountService.getAccount(createdAccount.accountId()).balances();
        assertEquals(fetchedBalances, createdAccount.balances());
    }

    @Test
    void createAccount_withInvalidCurrency_throwsInvalidCurrencyException() {
        CreateAccountDto request = new CreateAccountDto(
                1L,
                "EE",
                List.of("XYZ")
        );

        InvalidCurrencyException exception = assertThrows(
                InvalidCurrencyException.class,
                () -> accountService.createAccount(request)
        );

        assertTrue(exception.getMessage().contains("XYZ"));
    }



    @Test
    void createAccount_withMixedValidAndInvalidCurrencies_throwsInvalidCurrencyException() {
        CreateAccountDto request = new CreateAccountDto(
                1L,
                "EE",
                List.of("EUR", "INVALID", "USD")
        );

        InvalidCurrencyException exception = assertThrows(
                InvalidCurrencyException.class,
                () -> accountService.createAccount(request)
        );

        assertTrue(exception.getMessage().contains("INVALID"));
    }

    @Test
    void getAccount_withValidAccount_returnsDto() {
        Long customerId = 1L;
        String country = "EE";
        List<String> currencies = List.of("EUR", "USD", "GBP");
        
        CreateAccountDto createRequest = new CreateAccountDto(
                customerId,
                country,
                currencies
        );
        
        GetAccountDto createdAccount = accountService.createAccount(createRequest);
        Long accountId = createdAccount.accountId();

        GetAccountDto retrievedAccount = accountService.getAccount(accountId);

        assertNotNull(retrievedAccount);
        assertEquals(accountId, retrievedAccount.accountId());
        assertEquals(customerId, retrievedAccount.customerId());
        assertNotNull(retrievedAccount.balances());
        assertEquals(3, retrievedAccount.balances().size());

        List<String> retrievedCurrencyCodes = retrievedAccount.balances().stream()
                .map(BalanceDto::currency)
                .map(Enum::name)
                .toList();
        
        assertTrue(retrievedCurrencyCodes.contains("EUR"));
        assertTrue(retrievedCurrencyCodes.contains("USD"));
        assertTrue(retrievedCurrencyCodes.contains("GBP"));

        retrievedAccount.balances().forEach(balance -> 
                assertEquals(0, balance.availableAmount().compareTo(java.math.BigDecimal.ZERO))
        );
    }

    @Test
    void getAccount_withInvalidAccount_throwsInvalidAccountException() {

        Long nonExistentAccountId = 999999L;
        
        // Act & Assert: Verify exception is thrown
        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> accountService.getAccount(nonExistentAccountId)
        );
        
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(String.valueOf(nonExistentAccountId)));
    }
}
