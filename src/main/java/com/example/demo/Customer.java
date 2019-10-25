package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 1, max = 35)
    private String firstname;

    @NotNull
    @Size(min = 1, max = 35)
    private String lastname;

    //    @NotNull
//    @Size(min=1, max=35)
//    private String address;
//
//    @NotNull
//    @Size(min=1, max=20)
//    private String city;
//
//    @NotNull
//    @Size(min=1, max=2)
//    private String state;
//
//    @NotNull
//    @Size(min=5, max=5)
//    private String zipcode;
    @NotNull
    private String username;
    @NotNull
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Account> accounts;

    public Customer(@NotNull @Size(min = 1, max = 35) String firstname, @NotNull @Size(min = 1, max = 35) String lastname, @NotNull String username, @NotNull String password, Set<Account> accounts) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.accounts = accounts;
    }



    public Customer() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
