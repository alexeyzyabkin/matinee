package com.letionik.matinee.repository;

import com.letionik.matinee.MatineeApplication;
import com.letionik.matinee.model.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.IntStream;

import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Alexey Zyabkin on 19.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test.app.properties")
@SpringApplicationConfiguration(classes = MatineeApplication.class)
public class RoleRepositoryTest {

    private static final int MAX_ROLE_PRIORITY = 10;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void init() {
        IntStream.range(1, MAX_ROLE_PRIORITY).forEach(value -> entityManager.persist(generateRoleWithPriority(value)));
    }

    @Test
    @Transactional
    public void testGetRolesByPriority() {
        int rolesCount = 5;
        List<Role> roles = roleRepository.getRolesByPriority(rolesCount);
        assertSame(roles.size(), rolesCount);
        roles.forEach(role -> assertTrue(role.getPriority() >= 5 && role.getPriority() < 10));
    }

    private Role generateRoleWithPriority(int value) {
        Role role = new Role();
        role.setName(String.format("ROLE %d", value));
        role.setPriority(value);
        return role;
    }
}