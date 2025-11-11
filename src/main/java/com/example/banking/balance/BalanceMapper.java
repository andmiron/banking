package com.example.banking.balance;

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

    void updateAmount(@Param("newAmount") BigDecimal newAmount,
                      @Param("accountId") Long accountId,
                      @Param("balanceCurrencyCode") BalanceCurrency balanceCurrencyCode);

}
