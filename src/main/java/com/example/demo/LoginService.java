package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private CustomerRepository customerRepository;

    public void registerUser(Customer customer)
    {
        customerRepository.save(customer);
    }

    public Customer findUser(String username,String password)
    {
        return customerRepository.findByUsernameAndPassword(username, password);
    }
}
