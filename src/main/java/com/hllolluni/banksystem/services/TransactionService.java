package com.hllolluni.banksystem.services;

import com.hllolluni.banksystem.entities.Transaction;
import com.hllolluni.banksystem.exceptions.ApiRequestException;
import com.hllolluni.banksystem.repositories.TransactionRepository;
import com.hllolluni.banksystem.transfers.TransactionDTO;
import com.hllolluni.banksystem.utils.FlatFeeTransaction;
import com.hllolluni.banksystem.utils.PercentFeeTransaction;
import com.hllolluni.banksystem.utils.TransactionPerformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Transactional
    public Double performTransaction(TransactionDTO transactionDTO, Double flatFeeAmount, Double percentageFeeAmount) throws ApiRequestException {
        TransactionPerformer transactionPerformer;

        if (transactionDTO.getTransactionForm().equals("Flat fee")) {
            transactionPerformer = new FlatFeeTransaction(flatFeeAmount);
        } else if (transactionDTO.getTransactionForm().equals("Percent fee")) {
            transactionPerformer = new PercentFeeTransaction(percentageFeeAmount);
        } else {
            throw new ApiRequestException("Please, choose a right way of fee");
        }
        this.transactionRepository.save(
                Transaction.builder()
                        .amount(transactionDTO.getAmount())
                        .originatingAccountId(transactionDTO.getOriginatingAccountId())
                        .resultingAccountId(transactionDTO.getResultingAccountId())
                        .transactionReason(transactionDTO.getTransactionReason())
                        .build());

        return transactionPerformer.performTransaction(transactionDTO.getAmount());
    }

    @Transactional
    public Set<String> transactionOverviewsPerAccount(Long accountId) {
        List<Transaction> originatingTransactionList = transactionRepository.findTransactionsByOriginatingAccountId(accountId);
        List<Transaction> resultingTransactionList = transactionRepository.findTransactionsByResultingAccountId(accountId);
        if (originatingTransactionList.size() == 0 && resultingTransactionList.size() == 0){
            throw new ApiRequestException("The user with account id: "+ accountId+" has made no transaction!");
        }
        Set<Transaction> transactions = new HashSet<>();
        transactions.addAll(originatingTransactionList);
        transactions.addAll(resultingTransactionList);
        return transactions.stream().map(transaction -> transaction.toString()).collect(Collectors.toSet());
    }
}
