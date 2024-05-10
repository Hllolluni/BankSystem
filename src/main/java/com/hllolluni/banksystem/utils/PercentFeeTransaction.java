package com.hllolluni.banksystem.utils;

public class PercentFeeTransaction extends TransactionPerformer {
    public PercentFeeTransaction(Double feeAmount) {
        super(feeAmount);
    }

    @Override
    public Double performTransaction(Double amount) {
        return amount * this.getFeeAmount();
    }
}
