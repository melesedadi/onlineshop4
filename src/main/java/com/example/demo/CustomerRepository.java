package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findById(long id);
    Customer findByUsernameAndPassword(String username, String password);
}
