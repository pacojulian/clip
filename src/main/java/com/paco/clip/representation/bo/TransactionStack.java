package com.paco.clip.representation.bo;

import lombok.Data;

@Data
public class TransactionStack {
    private Long transactionId;
    private String user;
    private Double amount;
}
