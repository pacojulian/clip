package com.paco.clip.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DISBURSEMENT")
public class Disbursement {

    @Id
    @Column(name = "disbursement_id")
    private Long disbursementId;

    @Column(name = "destination_user")
    private String destinationUser;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private Timestamp date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "disbursement_id")
    private List<Transaction> transactions;

}
