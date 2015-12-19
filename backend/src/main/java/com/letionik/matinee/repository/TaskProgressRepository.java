package com.letionik.matinee.repository;

import com.letionik.matinee.model.TaskProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Bohdan Pohotilyi on 12.12.2015.
 */
public interface TaskProgressRepository extends JpaRepository<TaskProgress, Long> {
    TaskProgress findOneByTaskId(Long taskId);

    List<TaskProgress> findAllByParticipantEventId(Long eventId);

    @Query("SELECT task FROM TaskProgress task WHERE task.participant.event.id = :eventId AND task.status=2")
    List<TaskProgress> findDoneTasksByEventId(@Param(value = "eventId") Long eventId);
}
