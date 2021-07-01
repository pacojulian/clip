package com.paco.clip.domain.repository;

import com.paco.clip.domain.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {

    /**
     *
     * @param clipUser id to find transactions
     * @return list of transactions that the user has received
     */
    List<Transaction> findAllByClipUser(String clipUser);


    /**
     *
     * @param clipUser id of the clip
     * @return all transactions of a user grouped by client.
     */
    List<Transaction> findAllByClipUserAndIsDisbursementFalseOrderByClient(String clipUser);

    /**
     *
     * @param id to search transaction
     * @return transaction information
     */
    Transaction findByTransactionId(Long id);
}
