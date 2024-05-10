package com.hllolluni.banksystem.utils;

import com.hllolluni.banksystem.transfers.TransactionDTO;
import lombok.Getter;

@Getter
public abstract class TransactionPerformer {
    private Double feeAmount;
    public TransactionPerformer(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public abstract Double performTransaction(Double amount);
}
