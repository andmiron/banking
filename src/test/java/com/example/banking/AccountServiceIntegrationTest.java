package com.example.banking;

import com.example.banking.account.AccountService;
import com.example.banking.account.CreateAccountDto;
import com.example.banking.account.GetAccountDto;
import com.example.banking.balance.Balance;
import com.example.banking.balance.BalanceDto;
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

//    @Test
//    void createAccount_invalidCurrency_throws() {
//        CreateAccountDto invalid = new CreateAccountDto(
//                UUID.randomUUID(),
//                "EE",
//                List.of("EUR", "XYZ")
//        );
//
//        assertThatThrownBy(() -> accountService.createAccount(invalid))
//                .isInstanceOf(InvalidCurrencyException.class)
//                .hasMessageContaining("XYZ");
//    }
//
//    @Test
//    void getAccount_missing_throwsNotFound() {
//        assertThatThrownBy(() -> accountService.getAccount(Long.MAX_VALUE))
//                .isInstanceOf(AccountNotFoundException.class);
//    }
}
