package com.paco.clip.app.controller;

import com.paco.clip.app.resource.ClipResource;
import com.paco.clip.app.services.ClipServices;
import com.paco.clip.domain.model.Disbursement;
import com.paco.clip.domain.model.Transaction;
import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.DisbursementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ClipController implements ClipResource {

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
    public List<DisbursementResponse> makeDisbursement(String user) {
        return clipServices.makeDisbursement(user);
    }

    @Override
    public List<Disbursement> getDisbursementByUSer(String user) {
        return clipServices.getDisbursementByUSer(user);
    }
}
