package com.paco.clip.domain.repository;

import com.paco.clip.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String> {

    /**
     *
     * @param clipId id to find  user.
     * @return check if user exists
     */
    User findByClipId(String clipId);
}
