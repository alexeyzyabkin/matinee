package com.letionik.matinee.repository;

import com.letionik.matinee.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Bohdan Pohotilyi on 12.12.2015.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
