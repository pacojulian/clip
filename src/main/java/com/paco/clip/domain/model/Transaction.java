package com.paco.clip.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "clip_user")
    private String clipUser;

    @Column(name = "card_data")
    private String cardData;

    @Column(name = "client")
    private String client;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "disbursement_id")
    private Long disbursementId;

    @Column(name = "is_disbursement")
    private Boolean isDisbursement;

    @Column(name = "date")
    private Timestamp date;
}
