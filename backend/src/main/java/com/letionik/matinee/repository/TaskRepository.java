package com.letionik.matinee.repository;

import com.letionik.matinee.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Iryna Guzenko on 13.12.2015.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}
