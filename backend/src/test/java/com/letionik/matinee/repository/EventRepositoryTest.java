package com.letionik.matinee.repository;

import com.letionik.matinee.MatineeApplication;
import com.letionik.matinee.model.Event;
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
public class EventRepositoryTest {

    public static final String COMPLEX_EVENT_CODE_HERE = "complex-event-code-here";

    @Autowired
    private EventRepository eventRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void init() {
        Event event = new Event();
        event.setName("TestName");
        event.setCode(COMPLEX_EVENT_CODE_HERE);
        entityManager.persist(event);
    }

    @Test
    @Transactional
    public void testFindOneByCode() {
        Event foundedEvent = eventRepository.findOneByCode(COMPLEX_EVENT_CODE_HERE);
        assertNotNull(foundedEvent);
        assertEquals(COMPLEX_EVENT_CODE_HERE, foundedEvent.getCode());

        Event notFoundedEvent = eventRepository.findOneByCode("WRONG_EVENT_CODE");
        assertNull(notFoundedEvent);
    }
}
