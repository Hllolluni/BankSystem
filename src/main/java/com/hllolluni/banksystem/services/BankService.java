package com.hllolluni.banksystem.services;

import com.hllolluni.banksystem.entities.Bank;
import com.hllolluni.banksystem.exceptions.ApiRequestException;
import com.hllolluni.banksystem.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class BankService {
    private final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public void createBank(String bankName) {
        Bank bank = Bank.builder()
                .bankName(bankName)
                .totalTransactionFeeAmount(0.0)
                .totalTransferAmount(0.0)
                .flatFeeAmount(10.0)
                .transactionPercentFeeValue(0.05)
                .build();

        bankRepository.save(bank);
    }

    public Bank getBankById(Long id) throws ApiRequestException {
        Optional<Bank> bankOptional = bankRepository.findById(id);
        if (bankOptional.isPresent()){
            return bankOptional.get();
        }else {
            throw new ApiRequestException("The bank with this id does not exist!");
        }
    }

    public List<String> accounts(Long bankId) {
        Optional<Bank> bank = bankRepository.findById(bankId);
        if (bank.isPresent()) {
            return bank.get().getAccounts().stream()
                    .map(account -> account.toString())
                    .collect(Collectors.toList());
        }
        throw new ApiRequestException("This bank does not exists on the list!");
    }

    public void addTransactionFeeAndTransferAmount(Bank bank, Double transactionFeeAmount, Double transferAmount) {
        bank.setTotalTransactionFeeAmount(bank.getTotalTransactionFeeAmount() + transactionFeeAmount);
        bank.setTotalTransferAmount(bank.getTotalTransferAmount() + transferAmount);

        bankRepository.save(bank);
    }

    public String checkTotalTransactionFeeAmountPerBank(String bankName) {
        Double amount = bankRepository.findTotalTransactionFeeAmountPerBank(bankName);
//        if (amount.isPresent()){
        return "The total transaction fee amount per bank " + bankName + " is: " + amount + "$";
//        }else {
//            throw new ApiRequestException("This bank does not exist!");
//        }
    }

    public String checkTotalTransferAmountPerBank(String bankName) {
        Double amount = bankRepository.findTotalTransferAmountPerBank(bankName);
//        if (amount.isPresent()){
        return "The total transaction fee amount per bank " + bankName + " is: " + amount + "$";
//        }
//
//        throw new ApiRequestException("This bank does not exist!");
    }
}
