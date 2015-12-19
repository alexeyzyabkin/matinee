package com.letionik.matinee.repository;

import com.letionik.matinee.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findOneByCode(String code);
}
