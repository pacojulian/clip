package com.paco.clip.domain.repository;

import com.paco.clip.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String> {
}
