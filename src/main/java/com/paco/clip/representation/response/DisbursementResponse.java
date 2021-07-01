package com.paco.clip.representation.response;

import lombok.Data;

import java.util.List;

@Data
public class DisbursementResponse {
    private double total;
    private String clipUser;
    private String client;
    private List<Double> transactions;

}
