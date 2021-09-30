package com.example.server.repository;

import com.example.server.model.Transaction;
import com.example.server.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
