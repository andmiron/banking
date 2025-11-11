package com.example.banking.account;

import com.example.banking.balance.BalanceCurrency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateAccountRequest(
        @NotNull UUID customerId,
        @NotBlank @Size(min = 2, max = 2) String country,
        @NotNull @Size(min = 1) List<BalanceCurrency> currencies
) {
}
