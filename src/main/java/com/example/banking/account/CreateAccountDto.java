package com.example.banking.account;

import com.example.banking.common.IsoCountry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateAccountDto(
        @NotNull Long customerId,
        @NotBlank @Size(min = 2, max = 2) @IsoCountry String country,
        @NotNull @Size(min = 1) List<String> currencies
) {
}
