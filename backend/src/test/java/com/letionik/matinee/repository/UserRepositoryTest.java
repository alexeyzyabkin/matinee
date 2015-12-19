package com.letionik.matinee.repository;

import com.letionik.matinee.MatineeApplication;
import com.letionik.matinee.model.User;
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

import static org.junit.Assert.*;

/**
 * Created by Alexey Zyabkin on 19.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test.app.properties")
@SpringApplicationConfiguration(classes = MatineeApplication.class)
public class UserRepositoryTest {
    private static final String TEST_USER_LOGIN = "TEST_LOGIN";

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void init() {
        User user = new User();
        user.setName("TestName");
        user.setLogin(TEST_USER_LOGIN);
        entityManager.persist(user);
    }

    @Test
    @Transactional
    public void testFindOneByCode() {
        User foundedUser = userRepository.findOneByLogin(TEST_USER_LOGIN);
        assertNotNull(foundedUser);
        assertEquals(TEST_USER_LOGIN, foundedUser.getLogin());

        User notFoundedUser = userRepository.findOneByLogin("WRONG_USER_LOGIN");
        assertNull(notFoundedUser);
    }
}
