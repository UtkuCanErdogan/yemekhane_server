package com.example.server.dto.converter;

import com.example.server.dto.CardDto;
import com.example.server.model.Card;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardDtoConverter {
    private final TransactionDtoConverter converter;

    public CardDtoConverter(TransactionDtoConverter converter) {
        this.converter = converter;
    }

    public CardDto convert(Card from){
        return new CardDto(
                from.getId(),
                from.getCode(),
                from.getBalance(),
                from.isActive(),
                from.getCustomer().getId(),
                from.getTransaction() != null ?
                        (from.getTransaction()).stream().map(converter::convert).collect(Collectors.toList()) : null
        );
    }

    public CardDto getActiveCard(List<Card> cards){
        CardDto cardDto = null;
        for (Card card : cards){
            if (card.isActive()){
                cardDto =  convert(card);
            }
        }
        return cardDto;
    }
}
