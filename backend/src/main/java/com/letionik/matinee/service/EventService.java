package com.letionik.matinee.service;

import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.EventStatus;
import com.letionik.matinee.TaskStatus;
import com.letionik.matinee.model.*;
import com.letionik.matinee.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
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
    private TaskRepository taskRepository;
    @Autowired
    private TaskProgressRepository taskProgressRepository;
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
        if (event == null) return null;
        Participant participant = new Participant();
        participant.setUser(userRepository.findOne(id));
        participant.setEvent(event);
        participantRepository.save(participant);
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto revealTasks(Long eventID) {
        List<Task> tasks = taskRepository.findAll();
        Collections.shuffle(tasks);
        eventRepository.getOne(eventID).getParticipants().stream().forEachOrdered(p -> {
            for (int i = 0; i < 3; i++) {
                TaskProgress taskProgress = new TaskProgress();
                taskProgress.setTask(tasks.get(0));
                taskProgress.setStatus(TaskStatus.SENT);
                taskProgress.setParticipant(p);
                taskProgressRepository.save(taskProgress);
                tasks.remove(0);
            }
        });
        return modelMapper.map(eventRepository.getOne(eventID), EventDto.class);
    }
}