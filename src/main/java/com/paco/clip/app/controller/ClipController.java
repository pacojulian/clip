package com.paco.clip.app.controller;

import com.paco.clip.app.resource.ClipResource;
import com.paco.clip.app.services.ClipServices;
import com.paco.clip.domain.model.Transaction;
import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.DisbursementResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ClipController implements ClipResource {

    Logger logger = LoggerFactory.getLogger(ClipController.class);

    private final ClipServices clipServices;

    @Override
    public ClipResponse makeTransaction(MakeTransactionRequest request) {
        return clipServices.makeTransaction(request);
    }

    @Override
    public List<Transaction> getTransactionsByUser(String user) {
        return clipServices.getTransactionsByUser(user);
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
