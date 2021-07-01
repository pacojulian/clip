package com.paco.clip.app.services;

import com.paco.clip.representation.request.MakeTransactionRequest;
import com.paco.clip.representation.response.ClipResponse;
import com.paco.clip.representation.response.DisbursementResponse;
import com.paco.clip.representation.response.TransactionsResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ClipServices {

    /**
     * @param request includes the destination and amount to whom you are transaction it
     * @return message and status code if the transaction was successful
     */
    ClipResponse makeTransaction(@RequestBody MakeTransactionRequest request);

    /**
     * @param user the id which user you are trying to retrieve the information
     * @return list of transactions that a certain user has received.
     */
    TransactionsResponse getTransactionsByUser(@RequestParam("user") String user);

    /**
     * @param user which is going to receive the disbursements
     * @return message and status code if the transaction was successful
     */
    ClipResponse makeDisbursement(@RequestParam("user") String user);

    /**
     * @param user which is going to receive the disbursements
     * @return message and status code if the transaction was successful
     */
    DisbursementResponse getDisbursementByUSer(@RequestParam("user") String user);
}
