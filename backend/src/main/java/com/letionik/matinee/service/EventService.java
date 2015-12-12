package com.letionik.matinee.service;

import com.letionik.matinee.EventDto;
import com.letionik.matinee.model.Event;
import com.letionik.matinee.model.Participant;
import com.letionik.matinee.model.User;
import com.letionik.matinee.repository.EventRepository;
import com.letionik.matinee.repository.ParticipantRepository;
import com.letionik.matinee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public EventDto getCurrentEvent(Long id) {
        Participant participant = participantRepository.getParticipantByUserID(id);
        Event event = eventRepository.getEventByParticipantID(participant.getId());
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto createEvent(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDateTime(eventDto.getStartDate());

        User user = userRepository.getOne(eventDto.getAdmin().getId());
        Participant admin = new Participant();
        admin.setUser(user);
        admin.setEvent(event);
        participantRepository.save(admin);

        event.addParticipant(admin);
        UUID code = UUID.randomUUID();
        event.setCode(code);
        eventRepository.save(event);

        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto enroll(UUID code, Long id) {
        Participant participant = new Participant();
        Event event = eventRepository.getEventByCode(code);

        participant.setUser(userRepository.findOne(id));
        participant.setEvent(event);
        //TODO: it can be wrong code

        event.addParticipant(participant);
        participant.setEvent(event);
        return modelMapper.map(event, EventDto.class);
    }

//    getHistory
}
