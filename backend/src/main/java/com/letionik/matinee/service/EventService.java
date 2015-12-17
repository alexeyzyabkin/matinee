package com.letionik.matinee.service;

import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.EventStatus;
import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.model.*;
import com.letionik.matinee.repository.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Service
public class EventService {
    private static final int TASKS_PER_PARTICIPANT_COUNT = 3;

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
        Event event = eventRepository.findOne(eventID);
        Stack<Task> tasks = new Stack<>();
        tasks.addAll(taskRepository.getRandomTasks(event.getParticipants().size() * TASKS_PER_PARTICIPANT_COUNT));
        event.getParticipants().forEach(participant ->
                IntStream.range(0, TASKS_PER_PARTICIPANT_COUNT)
                        .forEach(i -> participant.getProgressTasks().add(tightTaskAndParticipant(tasks.pop(), participant))));
        return modelMapper.map(event, EventDto.class);
    }

    private TaskProgress tightTaskAndParticipant(Task task, Participant participant) {
        TaskProgress taskProgress = new TaskProgress();
        taskProgress.setTask(task);
        taskProgress.setParticipant(participant);
        return taskProgress;
    }

    @Transactional
    public EventDto revealRoles(Long eventId) {
        Event event = eventRepository.getOne(eventId);
        event.setStatus(EventStatus.ROLES_REVEALED);
        List<Participant> participants = event.getParticipants();
        Collections.shuffle(participants);
        List<Role> roles = roleRepository.findAllByOrderByPriority();
        for (Participant participant : participants) {
            participant.setRole(roles.get(0));
            roles.remove(0);
        }
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public List<TaskProgressDto> getHistory(Long id) {
        List<TaskProgress> tasks = taskProgressRepository.findTasksByEventId(id);
        Type listType = new TypeToken<List<TaskProgressDto>>() {
        }.getType();
        return modelMapper.map(tasks, listType);
    }
}
