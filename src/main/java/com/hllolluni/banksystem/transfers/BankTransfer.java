package com.hllolluni.banksystem.transfers;

import java.util.List;

public record BankTransfer(Long id, String bankName, List<AccountDTO>accounts, Double totalTransactionFeeAmount, Double totalTransferAmount, Double flatFeeAmount, Double transactionPercentFeeValue) {
}
