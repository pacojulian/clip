package com.paco.clip.domain.repository;

import com.paco.clip.domain.model.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, String> {
}
