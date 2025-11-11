package com.example.banking.balance.mapper;

import com.example.banking.balance.model.Balance;
import com.example.banking.balance.model.BalanceCurrency;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Mapper
public interface BalanceMapper {

    void insert(Balance balance);

    Optional<Balance> findByAccountIdAndCurrency(@Param("accountId") Long accountId, @Param("balanceCurrencyCode") BalanceCurrency balanceCurrencyCode);

    List<Balance> findByAccountId(@Param("accountId") Long accountId);

    void updateAmount(@Param("amount") BigDecimal amount, @Param("accountId") Long accountId, @Param("balanceCurrencyCode") BalanceCurrency balanceCurrencyCode);

    void update(Balance balance);

}