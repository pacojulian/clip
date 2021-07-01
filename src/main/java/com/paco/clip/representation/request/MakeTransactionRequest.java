package com.paco.clip.representation.request;

import lombok.Data;

@Data
public class MakeTransactionRequest {
    private String destinationUser;
    private String creationUser;
    private Double amount;
    private String date;
}
