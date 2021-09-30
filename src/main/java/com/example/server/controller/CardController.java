package com.example.server.controller;

import com.example.server.dto.*;
import com.example.server.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCardById(@PathVariable long id){
        return ResponseEntity.ok(service.getCardById(id));
    }

    @GetMapping("/totalBalance")
    public ResponseEntity<Long> getTotalPrice(){
        return ResponseEntity.ok(service.getTotalBalance());
    }

    @GetMapping("/getCardTransactions")
    public ResponseEntity<List<TransactionDto>> getCardTransactions(@PathVariable long id){
        return ResponseEntity.ok(service.getCardTransactions(id));
    }

    @GetMapping("/getCard/{code}")
    public ResponseEntity<CardDto> getCardByCode(@PathVariable String code){
        return ResponseEntity.ok(service.getCardByCode(code));
    }

    @PostMapping("/create")
    public ResponseEntity<CardDto> createCard(@RequestBody CreateCardRequest request){
        return ResponseEntity.ok(service.createCard(request));
    }

    @PutMapping("/uploadBalance")
    public ResponseEntity<CardDto> uploadBalance(@RequestBody UpdateCardBalanceRequest request){
        return ResponseEntity.ok(service.updateCardBalance(request));
    }

    @PutMapping("/getPayment")
    public ResponseEntity<CardDto> getPayment(@RequestBody GetPaymentRequest request){
        return ResponseEntity.ok(service.getPayment(request));
    }

   /* @PutMapping("/updateCustomer")
    public ResponseEntity<CardDto> updateCustomer(@RequestBody UpdateCardCustomerRequest request){
        return ResponseEntity.ok(service.updateCustomer(request));
    }*/

    @PatchMapping("activateCard/{id}")
    public ResponseEntity<Void> activateCard(@PathVariable("id") long id){
        service.activateCard(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("deActiveCard/{id}")
    public ResponseEntity<Void> deActiveCard(@PathVariable("id") long id){
        service.deActiveCard(id);
        return ResponseEntity.ok().build();
    }
}
