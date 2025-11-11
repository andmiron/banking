package com.example.banking.transaction;

import com.example.banking.balance.BalanceCurrencyCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TransactionMapper {

    void insert(Transaction transaction);

    Optional<Transaction> findById(@Param("id") Long id);

    List<Transaction> findByAccountId(@Param("accountId") Long accountId);

    List<Transaction> findByAccountIdAndCurrency(@Param("accountId") Long accountId, @Param("currencyCode") BalanceCurrencyCode currencyCode);

    Optional<Transaction> findLatestByAccountId(@Param("accountId") Long accountId);

}
