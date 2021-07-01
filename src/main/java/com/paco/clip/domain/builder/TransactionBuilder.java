package com.paco.clip.domain.builder;

import com.paco.clip.domain.model.Transaction;
import com.paco.clip.representation.request.MakeTransactionRequest;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

// TODO add exception handling
@Component
public class TransactionBuilder {
    public Transaction buildTransactionObject(MakeTransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(Math.abs(new Random().nextLong()));
        transaction.setAmount(request.getAmount());
        transaction.setClipUser(request.getClipUser());
        transaction.setDate(convertDate(request.getDate()));
        transaction.setIsDisbursement(false);
        transaction.setCardData(request.getCard());
        return transaction;
    }

    private Timestamp convertDate(String date) {
        Date date1;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            return new Timestamp(date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}


