package com.example.server.repository;

import com.example.server.model.Customer;
import com.example.server.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c from Customer c WHERE c.isActive=?1 order by c.name asc")
    List<Customer> getActiveCustomers(boolean bool);

}

