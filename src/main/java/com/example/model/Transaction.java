package com.example.model;

import java.time.LocalDateTime;

public class Transaction {
    private long id;
    private String customer;
    private LocalDateTime tranAt;
    private String tranType;
    private double amount;

    public Transaction(long id, String customer, LocalDateTime tranAt, String tranType, Double amount) {
        this.id = id;
        this.customer = customer;
        this.tranAt = tranAt;
        this.tranType = tranType;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + this.id +
                ", customer='" + this.customer + '\'' +
                ", tranAt=" + this.tranAt +
                ", tranType='" + this.tranType + '\'' +
                ", amount=" + this.amount +
                '}';
    }
}
