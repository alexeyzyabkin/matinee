package com.letionik.matinee.service;

import com.letionik.matinee.*;
import com.letionik.matinee.model.*;
import com.letionik.matinee.repository.*;
import com.letionik.matinee.*;
import com.letionik.matinee.model.Event;
import com.letionik.matinee.model.Participant;
import com.letionik.matinee.model.TaskProgress;
import com.letionik.matinee.model.User;
import com.letionik.matinee.repository.EventRepository;
import com.letionik.matinee.repository.ParticipantRepository;
import com.letionik.matinee.repository.RoleRepository;
import com.letionik.matinee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public EventDto getEventInfo(Long id) {
        Event event = eventRepository.findOne(id);
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

    @Transactional
    public EventDto revealRoles(Long eventId) {
        Event event = eventRepository.getOne(eventId);
        List<Participant> participants = event.getParticipants();
        Collections.shuffle(participants);
        Pageable topParticipant = new PageRequest(0,participants.size());
        List<Role> roles = roleRepository.findAllByOrderByPriority(topParticipant);
        for(int i = 0; i < participants.size(); i++){
            participants.get(i).setRole(roles.get(0));
            roles.remove(0);
        }
        EventDto eventDto = modelMapper.map(event, EventDto.class);
        return eventDto;
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
