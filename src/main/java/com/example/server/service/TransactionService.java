package com.example.server.service;

import com.example.server.dto.converter.TransactionDtoConverter;
import com.example.server.model.Card;
import com.example.server.model.Transaction;
import com.example.server.model.TransactionType;
import com.example.server.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final TransactionDtoConverter converter;

    public TransactionService(TransactionRepository repository, TransactionDtoConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    private Transaction addTransaction(final TransactionType type, final Card card){
        Transaction transaction = new Transaction(
                type,
                card
        );
        return repository.save(transaction);
    }

    public Transaction dinnerTransaction(final Card card){
        return addTransaction(TransactionType.DINNER, card);
    }

    public Transaction loadBalanceTransaction(final Card card){
        return addTransaction(TransactionType.LOAD_BALANCE, card);
    }


}
