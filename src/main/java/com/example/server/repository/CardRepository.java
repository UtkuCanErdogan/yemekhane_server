package com.example.server.repository;

import com.example.server.model.Card;
import com.example.server.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> getCardByCode(String code);

    @Query(value = "SELECT c from Card c WHERE c.isActive=1 and c.customerId=:customerId order by c.cardId desc Limit 0, 1",nativeQuery = true)
    Card getLastActiveCardOfCustomer(@Param("customerId")  long customerId);

}

