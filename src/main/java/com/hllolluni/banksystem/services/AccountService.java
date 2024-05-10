package com.hllolluni.banksystem.services;

import com.hllolluni.banksystem.entities.Account;
import com.hllolluni.banksystem.entities.Bank;
import com.hllolluni.banksystem.exceptions.ApiRequestException;
import com.hllolluni.banksystem.repositories.AccountRepository;
import com.hllolluni.banksystem.transfers.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final BankService bankService;

    @Autowired
    public AccountService(AccountRepository accountRepository, TransactionService transactionService, BankService bankService) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.bankService = bankService;
    }

    public void createAccount(String name, Long bankId) throws ApiRequestException {
        Bank bank = bankService.getBankById(bankId);
        Account account = Account.builder()
                .name(name)
                .balance(0)
                .bank(bank)
                .build();

        accountRepository.save(account);
    }

    public String getAccountById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            return accountOptional.get().toString();
        }
        throw new ApiRequestException("The account with id: " + id + " does not exist!");
    }

    @Transactional
    public void performTransaction(TransactionDTO transactionDTO) throws ApiRequestException {
        Optional<Account> originatingAccountOptional = accountRepository.findById(transactionDTO.getOriginatingAccountId());
        Optional<Account> resultingAccountOptional = accountRepository.findById(transactionDTO.getResultingAccountId());
        if (originatingAccountOptional.isPresent() && resultingAccountOptional.isPresent()) {
            Account originatingAccount = originatingAccountOptional.get();
            Account resultingAccount = resultingAccountOptional.get();
            Bank bank = originatingAccount.getBank();
            Double fee = transactionService.performTransaction(transactionDTO, bank.getFlatFeeAmount(), bank.getTransactionPercentFeeValue());
            if (originatingAccount.getBalance() - (transactionDTO.getAmount() + fee) > 0){
                bankService.addTransactionFeeAndTransferAmount(bank, fee, transactionDTO.getAmount());
                originatingAccount.setBalance(originatingAccount.getBalance() - (transactionDTO.getAmount() + fee));
                resultingAccount.setBalance(resultingAccount.getBalance() + transactionDTO.getAmount());
                this.accountRepository.save(originatingAccount);
                this.accountRepository.save(resultingAccount);
            } else throw new ApiRequestException("Not enough money in the account to make the transaction!");
        } else throw new ApiRequestException("The transaction can not be executed!");
    }

    @Transactional
    public void withdrawAccount(TransactionDTO transactionDTO) throws ApiRequestException {
        Optional<Account> accountOptional = accountRepository.findById(transactionDTO.getOriginatingAccountId());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Bank bank = account.getBank();
            transactionDTO.setOriginatingAccountId(null);
            Double fee = transactionService.performTransaction(transactionDTO, bank.getFlatFeeAmount(), bank.getTransactionPercentFeeValue());
            if (account.getBalance() - (transactionDTO.getAmount() + fee) > 0) {
                bankService.addTransactionFeeAndTransferAmount(bank, fee, transactionDTO.getAmount());
                account.setBalance(account.getBalance() - (transactionDTO.getAmount() + fee));
                this.accountRepository.save(account);
            }else throw new ApiRequestException("Not enough money in the account to make the transaction!")
        } else throw new ApiRequestException("The withdraw transaction can not be executed!");
    }

    @Transactional
    public void deposit(TransactionDTO transactionDTO) throws ApiRequestException {
        Optional<Account> accountOptional = accountRepository.findById(transactionDTO.getOriginatingAccountId());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Bank bank = account.getBank();
            transactionDTO.setResultingAccountId(null);
            Double fee = transactionService.performTransaction(transactionDTO, bank.getFlatFeeAmount(), bank.getTransactionPercentFeeValue());
            bankService.addTransactionFeeAndTransferAmount(bank, fee, transactionDTO.getAmount());
            if (account.getBalance() > fee){
                account.setBalance(account.getBalance() + (transactionDTO.getAmount() - fee));
                this.accountRepository.save(account);
            }else throw new ApiRequestException("Not enough money to deposit!");
        } else throw new ApiRequestException("The deposit can not be executed!");
    }

    public String accountsBalance(Long accountId) throws ApiRequestException {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            return account.get().toString();
        } else {
            throw new ApiRequestException("The account with this id does not exist!");
        }
    }
}
