package com.letionik.matinee.repository;

import com.letionik.matinee.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findOneByCode(String code);
}
