package com.hllolluni.banksystem.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bank {
    @Id
    @SequenceGenerator(
            name = "bank_id_sequence"
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankName;
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Account> accounts;
    @Min(value = 0, message = "totalTransactionFeeAmount should be greater than or equal to 0")
    private Double totalTransactionFeeAmount;
    @Min(value = 0, message = "totalTransferAmount should be greater than or equal to 0")
    private Double totalTransferAmount;
    @Min(value = 0, message = "flatFeeAmount should be greater than or equal to 0")
    private Double flatFeeAmount;
    @Min(value = 0, message = "transactionPercentFeeValue should be greater than or equal to 0")
    private Double transactionPercentFeeValue;

    public void addAccount(Account account){
        if (this.accounts == null) {
            this.accounts = new ArrayList<>();
        }

        this.accounts.add(account);
    }
}
