package com.paco.clip.representation.bo;

import lombok.Data;

import java.util.List;

@Data
public class TransactionBo {
    private Long transactionId;
    private Double amount;
    private Double total;
}

