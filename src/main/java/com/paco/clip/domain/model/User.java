package com.paco.clip.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "clip_id")
    private String clipId;

    @Column(name = "name")
    private String name;

    @Column(name = "account")
    private String account;

    @Column(name = "account_line")
    private Double accountLine;


    @Column(name = "pwd")
    private String pwd;

}
