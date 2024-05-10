package com.hllolluni.banksystem.controllers;

import com.hllolluni.banksystem.exceptions.ApiRequestException;
import com.hllolluni.banksystem.services.AccountService;
import com.hllolluni.banksystem.services.BankService;
import com.hllolluni.banksystem.services.TransactionService;
import com.hllolluni.banksystem.transfers.TransactionDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    public AccountService accountService;
    public BankService bankService;
    public TransactionService transactionService;

    @PostMapping("/bank")
    public void saveBank(@RequestParam String bankName) {
        bankService.createBank(bankName);
    }

    @GetMapping("/bank/{bankId}")
    public String getAllBanks(@PathVariable Long bankId) throws ApiRequestException {
        return bankService.getBankById(bankId).toString();
    }

    @PostMapping("/account/{bankId}")
    public void createAccount(@RequestParam String name, @PathVariable Long bankId) {
        this.accountService.createAccount(name, bankId);
    }

    @GetMapping("/account/{accountId}")
    public String getAccount(@PathVariable Long accountId) {
        return this.accountService.getAccountById(accountId);
    }

    @PostMapping("/transaction")
    public void performTransaction(@RequestBody TransactionDTO transactionDTO) throws ApiRequestException {
        this.accountService.performTransaction(transactionDTO);
    }

    @PostMapping("/transaction/deposit")
    public void deposit(@RequestBody TransactionDTO transactionDTO) {
        this.accountService.deposit(transactionDTO);
    }

    @PostMapping("/transaction/withdraw")
    public void withdraw(@RequestBody TransactionDTO transactionDTO) {
        this.accountService.withdrawAccount(transactionDTO);
    }

    @GetMapping("/transaction/{accountId}")
    public Set<String> transactionsPerAccount(@PathVariable Long accountId) {
        return this.transactionService.transactionOverviewsPerAccount(accountId);
    }

    @GetMapping("/account/balance/{accountId}")
    public String accountBalance(@PathVariable Long accountId) {
        return this.accountService.accountsBalance(accountId);
    }

    @GetMapping("/bank/account/{bankId}")
    public List<String> accountPerBank(@PathVariable Long bankId) {
        return this.bankService.accounts(bankId);
    }

    @GetMapping("/bank/totalTransactionFeeAmount")
    public String getTotalTransactionFeeAmountPerBank(@RequestParam String bankName) {
        return this.bankService.checkTotalTransactionFeeAmountPerBank(bankName);
    }

    @GetMapping("/bank/totalTransferAmountPerBank")
    public String getTotalTransferAmountPerBank(@RequestParam String bankName) {
        return this.bankService.checkTotalTransferAmountPerBank(bankName);
    }
}
