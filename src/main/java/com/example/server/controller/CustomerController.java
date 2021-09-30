package com.example.server.controller;

import com.example.server.dto.CreateCustomerRequest;
import com.example.server.dto.CustomerDto;
import com.example.server.dto.UpdateCustomerCard;
import com.example.server.dto.UpdateCustomerRequest;
import com.example.server.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable long id) {
        return ResponseEntity.ok(service.getCustomerById(id));  
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        return ResponseEntity.ok(service.getAllCustomers());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CustomerDto> getCustomerByCardCode(@PathVariable String code){
        return ResponseEntity.ok(service.findCustomerByCardCode(code));
    }

    @GetMapping("/getActiveCustomers")
    public ResponseEntity<List<CustomerDto>> getActiveCustomers(){
        return ResponseEntity.ok(service.getActiveCustomers());
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerRequest request) {
        return ResponseEntity.ok(service.createCustomer(request));
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody UpdateCustomerRequest request, @RequestBody long id){
        return ResponseEntity.ok(service.updateCustomer(request, id));
    }

    @PutMapping("/updateCustomerCard")
    public ResponseEntity<CustomerDto> updateCustomerCard(@RequestBody UpdateCustomerCard request){
        return ResponseEntity.ok(service.updateCustomerCard(request));
    }

    @PatchMapping("activateCustomer/{id}")
    public ResponseEntity<Void> toActiveCustomer(@PathVariable("id") long id){
        service.activateCustomer(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("deactiveCustomer/{id}")
    public ResponseEntity<Void> deActiveCustomer(@PathVariable("id") long id){
        service.deActiveCustomer(id);
        return ResponseEntity.ok().build();
    }
}
