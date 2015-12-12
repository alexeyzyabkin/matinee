package com.letionik.matinee.repository;

import com.letionik.matinee.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
