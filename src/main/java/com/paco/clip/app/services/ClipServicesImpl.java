package com.paco.clip.app.services;

import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.DisbursementResponse;
import com.paco.clip.representation.response.TransactionsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClipServicesImpl implements ClipServices {
    @Override
    public ClipResponse makeTransaction(MakeTransactionRequest request) {
        return null;
    }

    @Override
    public TransactionsResponse getTransactionsByUser(String user) {
        return null;
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
