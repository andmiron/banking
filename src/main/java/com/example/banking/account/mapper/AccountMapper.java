package com.example.banking.account.mapper;

import com.example.banking.account.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface AccountMapper {

    void insert(Account account);

    Optional<Account> findById(@Param("id") Long id);

    Optional<Account> findByCustomerId(@Param("customerId") UUID customerId);

    void update(Account account);

    void deleteById(@Param("id") Long id);
}
