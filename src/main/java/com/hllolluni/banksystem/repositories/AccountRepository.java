package com.hllolluni.banksystem.repositories;

import com.hllolluni.banksystem.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByName(String name);
}
