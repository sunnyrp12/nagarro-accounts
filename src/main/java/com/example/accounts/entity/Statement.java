package com.example.accounts.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "statement")
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    private String amount;

    @Column(name = "datefield")
    private String datefield;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
