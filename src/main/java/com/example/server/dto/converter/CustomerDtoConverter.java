package com.example.server.dto.converter;

import com.example.server.dto.CustomerDto;
import com.example.server.model.Card;
import com.example.server.model.Customer;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {

    private final CardDtoConverter converter;

    public CustomerDtoConverter(@Lazy CardDtoConverter converter) {
        this.converter = converter;
    }


    public CustomerDto convert(Customer from){
        return new CustomerDto(
                from.getId(),
                from.getTc(),
                from.getMail(),
                from.getName(),
                from.getSurname(),
                from.getAge(),
                from.isActive(),
                from.getCards().size()>0?
                        converter.convert(from.getCards().stream().filter(c->c.isActive()).sorted(Comparator.comparing(Card::getId).reversed()).findFirst().orElse(null)
                        ) : null
        );
    }

    public List<CustomerDto> convert(List<Customer> fromList){
        return fromList.stream()
                .map(this::convert).collect(Collectors.toList());
    }
}
