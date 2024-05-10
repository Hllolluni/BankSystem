package com.hllolluni.banksystem.utils;

import com.hllolluni.banksystem.transfers.TransactionDTO;

public class FlatFeeTransaction extends TransactionPerformer{
    public FlatFeeTransaction(Double feeAmount) {
        super(feeAmount);
    }

    @Override
    public Double performTransaction(Double amount) {
        return this.getFeeAmount();
    }
}
