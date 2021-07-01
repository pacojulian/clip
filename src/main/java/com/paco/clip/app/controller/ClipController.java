package com.paco.clip.app.controller;

import com.paco.clip.app.resource.ClipResource;
import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.DisbursementResponse;
import com.paco.clip.representation.response.TransactionsResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class ClipController implements ClipResource {

    Logger logger =  LoggerFactory.getLogger(ClipController.class);

    @Override
    public ClipResponse makeTransaction(MakeTransactionRequest request) {
        return null;
    }

    @Override
    public TransactionsResponse getTransactionsByUser(String user) {
        return new TransactionsResponse();
    }

    @Override
    public ClipResponse makeDisbursement(String user) {
        return null;
    }

    @Override
    public DisbursementResponse getDisbursementByUSer(String user) {
        return null;
    }
}
