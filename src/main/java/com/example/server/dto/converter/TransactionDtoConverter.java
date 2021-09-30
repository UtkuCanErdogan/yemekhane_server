package com.example.server.dto.converter;

import com.example.server.dto.CustomerDto;
import com.example.server.dto.TransactionDto;
import com.example.server.model.Customer;
import com.example.server.model.Transaction;
import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TransactionDtoConverter {

    public TransactionDto convert(Transaction from){
        return new TransactionDto(
                from.getId(),
                from.getType(),
                from.getCreationDate(),
                Objects.requireNonNull(from.getCard()).getId()
        );
    }

    public List<TransactionDto> convert(List<Transaction> fromList){
        return fromList.stream()
                .map(this::convert).collect(Collectors.toList());
    }
}
