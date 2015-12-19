package com.letionik.matinee.repository;

import com.letionik.matinee.EventStatus;
import com.letionik.matinee.MatineeApplication;
import com.letionik.matinee.TaskType;
import com.letionik.matinee.model.*;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by Alexey Zyabkin on 19.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test.app.properties")
@SpringApplicationConfiguration(classes = MatineeApplication.class)
public class TaskProgressRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TaskProgressRepository taskProgressRepository;

    private Long taskId;
    private Long taskProgressId;
    private Long eventId;

    @Before
    public void init() {
        Task task = new Task();
        task.setName("name");
        task.setDescription("description");
        task.setTaskType(TaskType.IN_TASK);
        entityManager.persist(task);
        taskId = task.getId();

        User user = new User();
        user.setName("name");
        user.setLogin("login");
        entityManager.persist(user);

        Event event = new Event.Builder().setName("name").setStatus(EventStatus.TASKS_REVEALED).build();
        entityManager.persist(event);
        eventId = event.getId();

        Participant participant = new Participant(user, event);
        entityManager.persist(participant);

        TaskProgress taskProgress = new TaskProgress();
        taskProgress.setTask(task);
        taskProgress.setParticipant(participant);
        entityManager.persist(taskProgress);
        taskProgressId = taskProgress.getId();

        TaskProgress secondTaskProgress = new TaskProgress();
        entityManager.persist(secondTaskProgress);
    }

    @Test
    @Transactional
    public void testFindOneByTaskId() {
        TaskProgress taskProgress = taskProgressRepository.findOneByTaskId(taskId);
        assertEquals(taskProgress.getId(), taskProgressId);
    }

    @Test
    @Transactional
    public void testFindAllByParticipantEventId() {
        List<TaskProgress> taskProgresses = taskProgressRepository.findAllByParticipantEventId(eventId);
        assertSame(1, taskProgresses.size());
        assertEquals(taskProgresses.get(0).getId(), taskProgressId);
    }
}
