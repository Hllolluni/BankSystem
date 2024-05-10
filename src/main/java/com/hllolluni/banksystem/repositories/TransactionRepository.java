package com.hllolluni.banksystem.repositories;

import com.hllolluni.banksystem.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsByOriginatingAccountId(Long originatingAccountId);
    List<Transaction> findTransactionsByResultingAccountId(Long resultingAccountId);
}
