//package com.hllolluni.banksystem;
//
//import com.hllolluni.banksystem.entities.Bank;
//import com.hllolluni.banksystem.repositories.BankRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BootstrapClass implements CommandLineRunner {
//
//    @Autowired
//    private BankRepository bankRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Bank bank = Bank.builder()
//                .bankName("Kosovo Bank")
//                .totalTransactionFeeAmount(0.0)
//                .totalTransferAmount(0.0)
//                .totalTransferAmount(0.0)
//                .flatFeeAmount(10.0)
//                .transactionPercentFeeValue(0.05)
//                .build();
//
//        this.bankRepository.save(bank);
//    }
//}
