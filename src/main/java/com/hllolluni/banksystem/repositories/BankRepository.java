package com.hllolluni.banksystem.repositories;

import com.hllolluni.banksystem.entities.Account;
import com.hllolluni.banksystem.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.OptionalDouble;

public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query(value = "select total_transaction_fee_amount from bank b where b.bank_name = ?", nativeQuery = true)
    Double findTotalTransactionFeeAmountPerBank(String bankName);

    @Query(value = "select total_transfer_amount from bank b where b.bank_name = ?", nativeQuery = true)
    Double findTotalTransferAmountPerBank(String bankName);
}
