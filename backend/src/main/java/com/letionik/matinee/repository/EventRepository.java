package com.letionik.matinee.repository;

import com.letionik.matinee.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.code = :code")
    Event getEventByCode(@Param(value = "code") String code);

    @Query("SELECT p.event FROM Participant p WHERE p.id = :id")
    Event getEventByParticipantID(@Param(value = "id") Long id);
}
