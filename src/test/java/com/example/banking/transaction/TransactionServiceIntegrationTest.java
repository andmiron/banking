package com.example.banking.transaction;

import com.example.banking.TestcontainersConfiguration;
import com.example.banking.account.AccountService;
import com.example.banking.account.CreateAccountDto;
import com.example.banking.account.GetAccountDto;
import com.example.banking.balance.BalanceCurrencyCode;
import com.example.banking.common.exception.AccountNotFoundException;
import com.example.banking.common.exception.DescriptionMissingException;
import com.example.banking.common.exception.InsufficientFundsException;
import com.example.banking.common.exception.InvalidAmountException;
import com.example.banking.common.exception.InvalidCurrencyException;
import com.example.banking.common.exception.InvalidDirectionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@Transactional
class TransactionServiceIntegrationTest {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    @Test
    void createTransaction_withInDirection_updatesBalance() {
        String transactionAmount = "100.50";
        String transactionCurrency = "EUR";
        String transactionDirection = "IN";
        String transactionDescription = "Deposit";

        GetAccountDto account = createAccountWithBalance(transactionCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto request = new CreateTransactionDto(
                accountId,
                new BigDecimal(transactionAmount),
                transactionCurrency,
                transactionDirection,
                transactionDescription
        );

        GetTransactionDto transaction = transactionService.createTransaction(request);

        assertNotNull(transaction.transactionId());
        assertEquals(accountId, transaction.accountId());
        assertEquals(new BigDecimal(transactionAmount), transaction.amount());
        assertEquals(BalanceCurrencyCode.valueOf(transactionCurrency), transaction.currency());
        assertEquals(Direction.valueOf(transactionDirection), transaction.direction());
        assertEquals(transactionDescription, transaction.description());
        assertEquals(new BigDecimal(transactionAmount), transaction.balanceAfter());

        List<GetTransactionDto> transactions = transactionService.getTransactions(accountId);
        assertEquals(1, transactions.size());
        assertEquals(transaction.transactionId(), transactions.get(0).transactionId());
    }

    @Test
    void createTransaction_withOutDirection_updatesBalance() {
        String accountCurrency = "EUR";
        String depositAmount = "200.00";
        String depositDirection = "IN";
        String depositDescription = "Initial deposit";
        String withdrawalAmount = "50.25";
        String withdrawalDirection = "OUT";
        String withdrawalDescription = "Withdrawal";
        String expectedBalanceAfter = "149.75";

        GetAccountDto account = createAccountWithBalance(accountCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto deposit = new CreateTransactionDto(
                accountId,
                new BigDecimal(depositAmount),
                accountCurrency,
                depositDirection,
                depositDescription
        );
        transactionService.createTransaction(deposit);

        CreateTransactionDto withdrawal = new CreateTransactionDto(
                accountId,
                new BigDecimal(withdrawalAmount),
                accountCurrency,
                withdrawalDirection,
                withdrawalDescription
        );

        GetTransactionDto transaction = transactionService.createTransaction(withdrawal);

        assertEquals(new BigDecimal(withdrawalAmount), transaction.amount());
        assertEquals(Direction.OUT, transaction.direction());
        assertEquals(new BigDecimal(expectedBalanceAfter), transaction.balanceAfter());
    }

    @Test
    void createTransaction_withInvalidAccount_throwsAccountNotFoundException() {
        Long invalidAccountId = 999999L;
        String transactionAmount = "100.00";
        String transactionCurrency = "EUR";
        String transactionDirection = "IN";
        String transactionDescription = "Test";

        CreateTransactionDto request = new CreateTransactionDto(
                invalidAccountId,
                new BigDecimal(transactionAmount),
                transactionCurrency,
                transactionDirection,
                transactionDescription
        );

        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.createTransaction(request)
        );

        assertTrue(exception.getMessage().contains(String.valueOf(invalidAccountId)));
    }

    @Test
    void createTransaction_withInvalidCurrency_throwsInvalidCurrencyException() {
        String accountCurrency = "EUR";
        String transactionAmount = "100.00";
        String invalidCurrency = "XYZ";
        String transactionDirection = "IN";
        String transactionDescription = "Test";

        GetAccountDto account = createAccountWithBalance(accountCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto request = new CreateTransactionDto(
                accountId,
                new BigDecimal(transactionAmount),
                invalidCurrency,
                transactionDirection,
                transactionDescription
        );

        InvalidCurrencyException exception = assertThrows(
                InvalidCurrencyException.class,
                () -> transactionService.createTransaction(request)
        );

        assertTrue(exception.getMessage().contains(invalidCurrency));
    }

    @Test
    void createTransaction_withCurrencyNotInAccount_throwsInvalidCurrencyException() {
        String accountCurrency = "EUR";
        String transactionAmount = "100.00";
        String missingCurrency = "USD";
        String transactionDirection = "IN";
        String transactionDescription = "Test";

        GetAccountDto account = createAccountWithBalance(accountCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto request = new CreateTransactionDto(
                accountId,
                new BigDecimal(transactionAmount),
                missingCurrency,
                transactionDirection,
                transactionDescription
        );

        InvalidCurrencyException exception = assertThrows(
                InvalidCurrencyException.class,
                () -> transactionService.createTransaction(request)
        );

        assertTrue(exception.getMessage().contains(missingCurrency));
    }

    @Test
    void createTransaction_withInvalidDirection_throwsInvalidDirectionException() {
        String accountCurrency = "EUR";
        String transactionAmount = "100.00";
        String transactionCurrency = "EUR";
        String invalidDirection = "INVALID";
        String transactionDescription = "Test";

        GetAccountDto account = createAccountWithBalance(accountCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto request = new CreateTransactionDto(
                accountId,
                new BigDecimal(transactionAmount),
                transactionCurrency,
                invalidDirection,
                transactionDescription
        );

        InvalidDirectionException exception = assertThrows(
                InvalidDirectionException.class,
                () -> transactionService.createTransaction(request)
        );

        assertTrue(exception.getMessage().contains(invalidDirection));
    }

    @Test
    void createTransaction_withMissingDescription_throwsDescriptionMissingException() {
        String accountCurrency = "EUR";
        String transactionAmount = "100.00";
        String transactionCurrency = "EUR";
        String transactionDirection = "IN";
        String emptyDescription = "";

        GetAccountDto account = createAccountWithBalance(accountCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto request = new CreateTransactionDto(
                accountId,
                new BigDecimal(transactionAmount),
                transactionCurrency,
                transactionDirection,
                emptyDescription
        );

        DescriptionMissingException exception = assertThrows(
                DescriptionMissingException.class,
                () -> transactionService.createTransaction(request)
        );

        assertNotNull(exception);
    }

    @Test
    void createTransaction_withNullAmount_throwsInvalidAmountException() {
        String accountCurrency = "EUR";
        BigDecimal nullAmount = null;
        String transactionCurrency = "EUR";
        String transactionDirection = "IN";
        String transactionDescription = "Test";

        GetAccountDto account = createAccountWithBalance(accountCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto request = new CreateTransactionDto(
                accountId,
                nullAmount,
                transactionCurrency,
                transactionDirection,
                transactionDescription
        );

        InvalidAmountException exception = assertThrows(
                InvalidAmountException.class,
                () -> transactionService.createTransaction(request)
        );

        assertNotNull(exception);
    }

    @Test
    void createTransaction_withZeroAmount_throwsInvalidAmountException() {
        String accountCurrency = "EUR";
        BigDecimal zeroAmount = BigDecimal.ZERO;
        String transactionCurrency = "EUR";
        String transactionDirection = "IN";
        String transactionDescription = "Test";

        GetAccountDto account = createAccountWithBalance(accountCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto request = new CreateTransactionDto(
                accountId,
                zeroAmount,
                transactionCurrency,
                transactionDirection,
                transactionDescription
        );

        InvalidAmountException exception = assertThrows(
                InvalidAmountException.class,
                () -> transactionService.createTransaction(request)
        );

        assertNotNull(exception);
    }

    @Test
    void createTransaction_withNegativeAmount_throwsInvalidAmountException() {
        String accountCurrency = "EUR";
        String negativeAmount = "-10.00";
        String transactionCurrency = "EUR";
        String transactionDirection = "IN";
        String transactionDescription = "Test";

        GetAccountDto account = createAccountWithBalance(accountCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto request = new CreateTransactionDto(
                accountId,
                new BigDecimal(negativeAmount),
                transactionCurrency,
                transactionDirection,
                transactionDescription
        );

        InvalidAmountException exception = assertThrows(
                InvalidAmountException.class,
                () -> transactionService.createTransaction(request)
        );

        assertNotNull(exception);
    }

    @Test
    void createTransaction_withInsufficientFunds_throwsInsufficientFundsException() {
        String accountCurrency = "EUR";
        String withdrawalAmount = "100.00";
        String transactionCurrency = "EUR";
        String transactionDirection = "OUT";
        String transactionDescription = "Withdrawal";

        GetAccountDto account = createAccountWithBalance(accountCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto request = new CreateTransactionDto(
                accountId,
                new BigDecimal(withdrawalAmount),
                transactionCurrency,
                transactionDirection,
                transactionDescription
        );

        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> transactionService.createTransaction(request)
        );

        assertNotNull(exception);
    }

    @Test
    void getTransactions_withValidAccount_returnsTransactions() {
        String accountCurrency = "EUR";
        String firstTransactionAmount = "100.00";
        String firstTransactionDirection = "IN";
        String firstTransactionDescription = "First transaction";
        String secondTransactionAmount = "50.00";
        String secondTransactionDirection = "IN";
        String secondTransactionDescription = "Second transaction";
        int expectedTransactionCount = 2;

        GetAccountDto account = createAccountWithBalance(accountCurrency);
        Long accountId = account.accountId();

        CreateTransactionDto tx1 = new CreateTransactionDto(
                accountId,
                new BigDecimal(firstTransactionAmount),
                accountCurrency,
                firstTransactionDirection,
                firstTransactionDescription
        );
        CreateTransactionDto tx2 = new CreateTransactionDto(
                accountId,
                new BigDecimal(secondTransactionAmount),
                accountCurrency,
                secondTransactionDirection,
                secondTransactionDescription
        );

        transactionService.createTransaction(tx1);
        transactionService.createTransaction(tx2);

        List<GetTransactionDto> transactions = transactionService.getTransactions(accountId);

        assertEquals(expectedTransactionCount, transactions.size());
        assertTrue(transactions.stream().anyMatch(t -> t.description().equals(firstTransactionDescription)));
        assertTrue(transactions.stream().anyMatch(t -> t.description().equals(secondTransactionDescription)));
    }

    @Test
    void getTransactions_withInvalidAccount_throwsAccountNotFoundException() {
        Long invalidAccountId = 999999L;

        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> transactionService.getTransactions(invalidAccountId)
        );

        assertTrue(exception.getMessage().contains(String.valueOf(invalidAccountId)));
    }

    @Test
    void getTransactions_withNullAccountId_throwsAccountMissingException() {
        Long nullAccountId = null;

        assertThrows(
                com.example.banking.common.exception.AccountMissingException.class,
                () -> transactionService.getTransactions(nullAccountId)
        );
    }

    private GetAccountDto createAccountWithBalance(String currency) {
        CreateAccountDto createRequest = new CreateAccountDto(
                1L,
                "EE",
                List.of(currency)
        );
        return accountService.createAccount(createRequest);
    }
}
