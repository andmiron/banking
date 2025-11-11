package com.example.banking.account;

import com.example.banking.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
class AccountMapperIntegrationTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    void insertAndFindAccount() {
        Account account = new Account(null, 1L, AccountStatus.ACTIVE, "EE", Instant.now());

        accountMapper.insert(account);

        Optional<Account> savedAccountOptional = accountMapper.findByCustomerId(account.getCustomerId());

        assertThat(savedAccountOptional).isPresent();
        Account savedAccount = savedAccountOptional.get();

        assertThat(savedAccount.getId()).isNotNull();
        assertThat(savedAccount.getCustomerId()).isEqualTo(account.getCustomerId());
        assertThat(savedAccount.getAccountStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(savedAccount.getCountry()).isEqualTo(account.getCountry());
        assertThat(savedAccount.getCreatedAt()).isNotNull();
    }
}
