package com.letionik.matinee.service;

import com.letionik.matinee.*;
import com.letionik.matinee.exception.EventNotFoundException;
import com.letionik.matinee.model.*;
import com.letionik.matinee.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
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
        String code = UUID.randomUUID().toString().substring(0, 5);

        Event event = new Event.Builder().setName(eventDto.getName())
                .setCode(code)
                .setStatus(EventStatus.NEW)
                .setCreationDate(LocalDateTime.now())
                .build();
        Date date = eventDto.getStartDate();
        if (date != null) {
            event.setStartDate(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
        }
        Participant admin = new Participant(userRepository.getOne(userId), event);
        admin.setStatus(ParticipantStatus.ADMIN);
        event.addParticipant(admin);
        eventRepository.save(event);
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto enroll(String code, Long userId) throws EventNotFoundException {
        Event event = eventRepository.findOneByCode(code);
        if (event == null || event.getStatus() != EventStatus.NEW) throw new EventNotFoundException();
        Participant participant = new Participant(userRepository.findOne(userId), event);
        participantRepository.save(participant);
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto revealTasks(Long eventID) {
        Event event = eventRepository.findOne(eventID);
        event.setStatus(EventStatus.TASKS_REVEALED);

        Stack<Task> tasks = new Stack<>();
        final int totalTasksCount = event.getParticipants().size() * TASKS_PER_PARTICIPANT_COUNT;
        tasks.addAll(taskRepository.getRandomTasks(totalTasksCount));
        event.getParticipants().forEach(participant ->
                IntStream.range(0, TASKS_PER_PARTICIPANT_COUNT)
                        .forEach(i -> participant.getProgressTasks().add(tightTaskAndParticipant(tasks.pop(), participant))));
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto revealRoles(Long eventId) {
        Event event = eventRepository.getOne(eventId);
        event.setStatus(EventStatus.ROLES_REVEALED);

        List<Participant> participants = event.getParticipants();
        Stack<Role> roles = new Stack<>();
        roles.addAll(roleRepository.getRolesByPriority(participants.size()));
        participants.forEach(participant -> participant.setRole(roles.pop()));
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public List<TaskProgressDto> getHistory(Long id) {
        List<TaskProgress> tasks = taskProgressRepository.findAllByParticipantEventId(id);
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskProgressDto.class))
                .collect(Collectors.toList());
    }

    private TaskProgress tightTaskAndParticipant(Task task, Participant participant) {
        TaskProgress taskProgress = new TaskProgress();
        taskProgress.setTask(task);
        taskProgress.setParticipant(participant);
        return taskProgress;
    }

    @Transactional
    public SortedMap<Long, Participant> closeEvent(Long eventId) {
        Event event = eventRepository.getOne(eventId);
        event.setStatus(EventStatus.FINISHED);
        SortedMap<Long, Participant> statistics = new TreeMap<>();
        event.getParticipants().forEach(p ->
                statistics.put(p.getProgressTasks().stream().filter(t -> t.getStatus().equals(TaskStatus.DONE)).count(), p));
        return statistics;
    }
}
