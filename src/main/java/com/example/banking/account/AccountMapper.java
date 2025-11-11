package com.example.banking.account;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface AccountMapper {

    void insert(Account account);

    Optional<Account> findById(@Param("id") Long id);

    Optional<Account> findByCustomerId(@Param("customerId") UUID customerId);

}
