package com.letionik.matinee.repository;

import com.letionik.matinee.model.Task;
import com.letionik.matinee.model.TaskProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Bohdan Pohotilyi on 12.12.2015.
 */
public interface TaskProgressRepository extends JpaRepository<TaskProgress, Long>{
}
