package com.letionik.matinee.repository;

import com.letionik.matinee.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllLimit10OrderByPriority(int size);
}
