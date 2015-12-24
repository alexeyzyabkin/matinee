package com.letionik.matinee.repository;

import com.letionik.matinee.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findOneByCode(String code);

    @Query("SELECT DISTINCT p.event FROM Participant p WHERE p.user.id = :userId")
    List<Event> findAllByUserId(@Param("userId") Long userId);
}
