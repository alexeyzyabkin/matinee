package com.letionik.matinee.repository;

import com.letionik.matinee.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT * FROM (SELECT * from role order by role_priority DESC LIMIT :limit) r ORDER BY RAND()", nativeQuery = true)
    List<Role> getRolesByPriority(@Param("limit") int rolesCount);
}