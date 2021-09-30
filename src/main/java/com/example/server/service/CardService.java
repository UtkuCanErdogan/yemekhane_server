package com.example.server.service;

import com.example.server.dto.*;
import com.example.server.dto.converter.CardDtoConverter;
import com.example.server.dto.converter.TransactionDtoConverter;
import com.example.server.exception.CardIsNotActiveException;
import com.example.server.exception.CardNotFoundException;
import com.example.server.exception.CustomerIsNotActiveException;
import com.example.server.exception.InsufficientBalanceException;
import com.example.server.model.Card;
import com.example.server.model.Customer;
import com.example.server.model.Transaction;
import com.example.server.model.TransactionType;
import com.example.server.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CardService {

    private final CardRepository repository;
    private final CardDtoConverter converter;
    private final CustomerService service;
    private final TransactionService transactionService;
    private final TransactionDtoConverter transactionDtoConverter;

    public CardService(CardRepository repository, CardDtoConverter converter, CustomerService service,
                       TransactionService transactionService, TransactionDtoConverter transactionDtoConverter) {
        this.repository = repository;
        this.converter = converter;
        this.service = service;
        this.transactionService = transactionService;
        this.transactionDtoConverter = transactionDtoConverter;
    }

    protected Card findCardById(final long id){
        return repository.findById(id).orElseThrow(() -> new CardNotFoundException("Kart Bulunamadı"));
    }

    protected Card findCardByCode(final String code){
        return repository.getCardByCode(code).orElseThrow(() -> new CardNotFoundException("Kart Bulunamadı"));
    }

    public CardDto getCardByCode(final String code){
        return converter.convert(findCardByCode(code));
    }


    private void changeActivate(final boolean isActive, final long id){
        Card card = findCardById(id);
        Card changeActivate = new Card(
                card.getId(),
                card.getCode(),
                card.getBalance(),
                isActive,
                card.getCreationDate(),
                card.getCustomer(),
                card.getTransaction()
        );
        repository.save(changeActivate);
    }

    public CardDto getCardById(final long id){
        return converter.convert(findCardById(id));
    }

    protected Card cardCreate(Customer customer, String code){
        return new Card(
                code,
                customer
        );
    }


    public CardDto createCard(final CreateCardRequest request){
        Customer customer = service.findCustomerById(request.getCustomerId());
        if (customer.isActive()){
            Card card = new Card(
                    request.getCode(),
                    customer
            );
            return converter.convert(repository.save(card));
        }
        else throw new CustomerIsNotActiveException("Kullanıcı Aktif Değil");
    }

    public CardDto updateCardBalance(final UpdateCardBalanceRequest request){
            Card card = findCardByCode(request.getCode());
            if (card.getCustomer().isActive()){
                if (card.isActive()){
                    Transaction transaction = new Transaction(
                            TransactionType.LOAD_BALANCE,
                            card
                    );
                    card.getTransaction().add(transaction);
                    Card updatedCard = new Card(
                            card.getId(),
                            card.getCode(),
                            card.getBalance() + request.getBalance(),
                            card.isActive(),
                            card.getCreationDate(),
                            card.getCustomer(),
                            card.getTransaction()
                    );

                    return converter.convert(repository.save(updatedCard));
                }
                else throw new CardIsNotActiveException("Kart Aktfi Değil");
            }
            else throw new CustomerIsNotActiveException("Kullanıcı Aktif Değil");
        }



    public CardDto getPayment(final GetPaymentRequest request){
        Card card = findCardByCode(request.getCode());
        if (card.getCustomer().isActive()){
            if (card.isActive()){
               if (card.getBalance() >= request.getAmount()){
                   Transaction transaction = new Transaction(
                           TransactionType.DINNER,
                           card
                   );
                   card.getTransaction().add(transaction);
                   Card updatedCard = new Card(
                           card.getId(),
                           card.getCode(),
                           card.getBalance() - request.getAmount(),
                           card.isActive(),
                           card.getCreationDate(),
                           card.getCustomer(),
                           card.getTransaction()
                   );
                   return converter.convert(repository.save(updatedCard));
               }
               else throw new InsufficientBalanceException("Yetersiz Bakiye");
            }
            else throw new CardIsNotActiveException("Kart Aktif Değil");
        }
        else throw new CustomerIsNotActiveException("Kullanıcı Aktfi Değil");
    }

    public Long getTotalBalance(){
        List<Card> cards = repository.findAll();
        return cards.stream().mapToLong(Card::getBalance).sum();
    }

    /*public CardDto updateCustomer(final UpdateCardCustomerRequest request){
        Customer customer = service.findCustomerById(request.getCustomerId());
        Card card = findCardById(request.getId());
        Card updatedCard = new Card(
                card.getId(),
                card.getCode(),
                card.getBalance(),
                card.isActive(),
                card.getCreationDate(),
                customer,
                card.getTransaction()
        );
        return converter.convert(repository.save(updatedCard));
    }*/

    public List<TransactionDto> getCardTransactions(final long id){
        Card card = findCardById(id);
        List<Transaction> transactions = card.getTransaction();
        if (card.getTransaction() == null){
            return null;
        }
        return transactionDtoConverter.convert(transactions);
    }

    public void deActiveCard(final long id) {
        changeActivate(false, id);
    }

    public void activateCard(final long id) {
        changeActivate(true, id);
    }


}
