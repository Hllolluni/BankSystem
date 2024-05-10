package com.hllolluni.banksystem.transfers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO{
    private Double amount;
    private Long originatingAccountId;
    private Long resultingAccountId;
    private String transactionReason;
    private String transactionForm;
}
