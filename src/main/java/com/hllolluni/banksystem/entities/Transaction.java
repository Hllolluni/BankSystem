package com.hllolluni.banksystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @SequenceGenerator(
            name = "transaction_id_sequence"
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private Long originatingAccountId;
    private Long resultingAccountId;
    private String transactionReason;


    @Override
    public String toString() {
        if (resultingAccountId == null) {
            return "Transaction{" +
                    "id=" + id +
                    ", AccountId=" + originatingAccountId +
                    ", deposit=" + amount;
        } else if (originatingAccountId == null) {
            return "Transaction{" +
                    "id=" + id +
                    ", AccountId=" + resultingAccountId +
                    ", withdraw=" + amount;
        }
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", originatingAccountId=" + originatingAccountId +
                ", resultingAccountId=" + resultingAccountId +
                ", transactionReason='" + transactionReason + '\'' +
                '}';
    }
}
