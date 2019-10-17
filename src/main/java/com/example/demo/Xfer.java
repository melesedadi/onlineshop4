package com.example.demo;

import javax.persistence.*;

@Entity
public class Xfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String acctno;
    private double balance;
    private double amount;
    private boolean deposit;
    private String date;

    @ManyToOne()
    @JoinColumn(name="account_id")
    private Account account;

    public Xfer() {
        this.amount = 0;
        this.deposit = true;
        this.account = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAcctno() {
        return acctno;
    }

    public void setAcctno(String acctno) {
        this.acctno = acctno;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isDeposit() {
        return deposit;
    }

    public void setDeposit(boolean deposit) {
        this.deposit = deposit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
