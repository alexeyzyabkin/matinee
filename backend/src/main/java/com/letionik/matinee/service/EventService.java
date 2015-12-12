package com.letionik.matinee.service;

import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.TaskStatus;
import com.letionik.matinee.EventStatus;
import com.letionik.matinee.model.Event;
import com.letionik.matinee.model.Participant;
import com.letionik.matinee.model.TaskProgress;
import com.letionik.matinee.model.User;
import com.letionik.matinee.repository.EventRepository;
import com.letionik.matinee.repository.ParticipantRepository;
import com.letionik.matinee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
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
    public EventDto createEvent(CreateEventRequestDto eventDto, Long userId) {
        Event event = new Event();
        event.setName(eventDto.getName());
        Date date = eventDto.getStartDate();
        if (date != null) {
            event.setStartDate(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
        }

        String code = UUID.randomUUID().toString().substring(0, 5);
        event.setCode(code);
        event.setStatus(EventStatus.NEW);
        event.setCreationDate(LocalDateTime.now());
        eventRepository.save(event);

        User user = userRepository.getOne(userId);
        Participant admin = new Participant();
        admin.setUser(user);
        admin.setEvent(event);
        event.setAdmin(user);
        participantRepository.save(admin);

        event.addParticipant(admin);
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto enroll(String code, Long id) {
        Event event = eventRepository.getEventByCode(code);
        if(event == null) return null;
        Participant participant = new Participant();
        participant.setUser(userRepository.findOne(id));
        participant.setEvent(event);
        event.addParticipant(participant);
        participant.setEvent(event);
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public List<TaskProgressDto> getHistory(Long id){
        Event event = eventRepository.getOne(id);
        List<TaskProgress> taskProgresses = new ArrayList<>();
        for(Participant participant: event.getParticipants()){
            for (TaskProgress taskProgress : participant.getProgressTasks()) {
                if(taskProgress.getStatus().equals(TaskStatus.DONE)){
                    taskProgresses.add(taskProgress);
                }
            }
        }
        Type listType =  new TypeToken<List<TaskProgress>>() {}.getType();
        return modelMapper.map(taskProgresses, listType);
    }
}
