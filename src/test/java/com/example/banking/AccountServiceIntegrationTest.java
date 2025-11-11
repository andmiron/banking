package com.example.banking;

import com.example.banking.account.AccountService;
import com.example.banking.account.CreateAccountDto;
import com.example.banking.account.GetAccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        System.out.println(request);

        GetAccountDto created = accountService.createAccount(request);

        assertThat(created.accountId()).isNotNull();
        assertThat(created.customerId()).isEqualTo(request.customerId());
        assertThat(created.balances()).hasSize(2);

        GetAccountDto fetched = accountService.getAccount(created.accountId());
        assertThat(fetched.balances()).hasSameSizeAs(created.balances());
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
