package com.example.banking;

import com.example.banking.account.AccountMapper;
import com.example.banking.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
class BankingApplicationTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Test
    void contextLoads() {
        assertNotNull(accountService);
        assertNotNull(accountMapper);
        Object injectedMapper = ReflectionTestUtils.getField(accountService, "accountMapper");
        assertSame(accountMapper, injectedMapper);
    }
}
