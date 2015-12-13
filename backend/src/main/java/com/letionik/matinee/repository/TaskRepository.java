package com.letionik.matinee.repository;

import com.letionik.matinee.model.Event;
import com.letionik.matinee.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Iryna Guzenko on 13.12.2015.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}
