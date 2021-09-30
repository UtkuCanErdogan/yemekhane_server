package com.example.server.service;

import com.example.server.dto.CreateCustomerRequest;
import com.example.server.dto.CustomerDto;
import com.example.server.dto.UpdateCustomerCard;
import com.example.server.dto.UpdateCustomerRequest;
import com.example.server.dto.converter.CustomerDtoConverter;
import com.example.server.exception.CustomerIsNotActiveException;
import com.example.server.exception.CustomerNotFoundException;
import com.example.server.exception.DoesNotHaveActiveCardException;
import com.example.server.exception.SameTypeException;
import com.example.server.model.Card;
import com.example.server.model.Customer;
import com.example.server.repository.CustomerRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerDtoConverter converter;
    private final CardService service;

    public CustomerService(CustomerRepository repository, CustomerDtoConverter converter, @Lazy CardService service) {
        this.repository = repository;
        this.converter = converter;
        this.service = service;
    }

    protected Customer findCustomerById(final long id){
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Kullanıcı Bulunamadı"));
    }

    private void changeActivate(final boolean isActive, final long id) {

        Customer customer = findCustomerById(id);
        if (customer.isActive() == isActive){
            throw new SameTypeException("Değiştirilmek İstenen Değere Zaten Sahip!!");
        }
        else {
            Customer changeActivateCustomer = new Customer(
                    customer.getId(),
                    customer.getTc(),
                    customer.getMail(),
                    customer.getName(),
                    customer.getSurname(),
                    customer.getAge(),
                    customer.getCreationDate(),
                    customer.getRole(),
                    isActive,
                    customer.getCards()
            );

            repository.save(changeActivateCustomer);
        }
    }

    public CustomerDto getCustomerById(final long id){
        return converter.convert(findCustomerById(id));
    }

    public List<CustomerDto> getAllCustomers(){

        return converter.convert(repository.findAll());
    }

    public List<CustomerDto> getActiveCustomers(){
        return converter.convert(repository.getActiveCustomers(true));
    }

    public CustomerDto createCustomer(final CreateCustomerRequest request){
        Customer customer = new Customer(
                request.getTc(),
                request.getMail(),
                request.getName(),
                request.getSurname(),
                request.getAge()
        );
        return converter.convert(repository.save(customer));
    }

    public CustomerDto updateCustomer(final UpdateCustomerRequest request, final long id){
        Customer customer = findCustomerById(id);
        if (customer.isActive()){
            Customer updatedCustomer = new Customer(
                    customer.getId(),
                    request.getTc(),
                    request.getMail(),
                    request.getName(),
                    request.getSurname(),
                    request.getAge(),
                    customer.getCreationDate(),
                    customer.getRole(),
                    customer.isActive(),
                    customer.getCards()
            );
            return converter.convert(updatedCustomer);
        }
        else throw new CustomerIsNotActiveException("Aktif Olmayan Kullanıcı Güncellenemez");
    }

    public CustomerDto updateCustomerCard(final UpdateCustomerCard request) {
        Customer customer = findCustomerById(request.getId());
        if (customer.isActive()) {
            Card card1 = null;
            for (Card card : Objects.requireNonNull(customer.getCards())) {
                if (card.isActive()) {
                    card1 = card;
                }
            }
            if (card1 != null){
                service.deActiveCard(Objects.requireNonNull(card1).getId());
                customer.getCards().add(service.cardCreate(customer, request.getCode()));
                Customer updatedCustomer = new Customer(
                        customer.getId(),
                        customer.getTc(),
                        customer.getMail(),
                        customer.getName(),
                        customer.getSurname(),
                        customer.getAge(),
                        customer.getCreationDate(),
                        customer.getRole(),
                        customer.isActive(),
                        customer.getCards()
                );
                return converter.convert(repository.save(updatedCustomer));
            }
            else throw new DoesNotHaveActiveCardException("Herhangi Bir Karta Sahip Değilsiniz");

        }
        else throw new CustomerIsNotActiveException("Kullanıcı Aktif Değil");
    }

    public CustomerDto findCustomerByCardCode(final String code){
        Card card = service.findCardByCode(code);
        return converter.convert(card.getCustomer());
    }

    public void deActiveCustomer(final long id){
        changeActivate(false, id);
    }

    public void activateCustomer(final long id){
        changeActivate(true, id);
    }





}
