package com.paco.clip.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "CARD")
public class Card {

    @Id
    @Column(name = "card_id")
    private String cardId;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "credit_line")
    private Float creditLine;

    @Column(name = "last_digits")
    private String lastDigits;

    @OneToOne(mappedBy = "card")
    private User user;

}
