package com.letionik.matinee.repository;

import com.letionik.matinee.MatineeApplication;
import com.letionik.matinee.TaskType;
import com.letionik.matinee.model.Task;
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

import static org.junit.Assert.assertTrue;

/**
 * Created by Alexey Zyabkin on 03.10.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test.app.properties")
@SpringApplicationConfiguration(classes = MatineeApplication.class)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void init() {
        IntStream.range(0, 10).forEach(value ->
                entityManager.persist(generateRandomTask(value)));
    }

    @Test
    @Transactional
    public void testGetRandomTasks() {
        List<Task> tasks = taskRepository.getRandomTasks(5);
        assertTrue(tasks.size() == 5);
    }

    private Task generateRandomTask(int i) {
        Task task = new Task();
        task.setName("testname" + i);
        task.setTaskType(TaskType.IN_TASK);
        task.setDescription("testdescription");
        return task;
    }
}
