package com.paco.clip.domain.repository;

import com.paco.clip.domain.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {
}
