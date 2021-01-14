package com.jay.liqui.repository;

import com.jay.liqui.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
