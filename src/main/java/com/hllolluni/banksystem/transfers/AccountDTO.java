package com.hllolluni.banksystem.transfers;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AccountDTO {
    private final Long id;
    @Setter
    private String name;
    @Setter
    private Double balance;

    public AccountDTO(Long id, String name, Double balance) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
}
