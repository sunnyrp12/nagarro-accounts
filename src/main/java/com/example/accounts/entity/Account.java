package com.example.accounts.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "account")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_number")
    private String accountNumber;

}
