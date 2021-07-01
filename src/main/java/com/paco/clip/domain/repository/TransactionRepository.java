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
}
