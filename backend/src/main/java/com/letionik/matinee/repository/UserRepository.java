package com.letionik.matinee.repository;

import com.letionik.matinee.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Bohdan Pohotilyi on 12.12.2015.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByLogin(String login);
}
