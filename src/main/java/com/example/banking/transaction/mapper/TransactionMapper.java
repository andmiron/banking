package com.example.banking.transaction.mapper;

import com.example.banking.transaction.model.Transaction;
import com.example.banking.balance.model.BalanceCurrency;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TransactionMapper {

    void insert(Transaction transaction);

    Optional<Transaction> findById(@Param("id") Long id);

    List<Transaction> findByAccountId(@Param("accountId") Long accountId);

    List<Transaction> findByAccountIdAndCurrency(@Param("accountId") Long accountId, @Param("currencyCode") BalanceCurrency currencyCode);

}
