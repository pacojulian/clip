package com.paco.clip.representation.request;

import lombok.Data;

@Data
public class MakeTransactionRequest {
    private String clipUser;
    private String card;
    private Double amount;
    private String date;
}
