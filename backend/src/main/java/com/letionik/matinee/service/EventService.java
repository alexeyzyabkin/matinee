package com.letionik.matinee.service;

import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.EventStatus;
import com.letionik.matinee.ParticipantType;
import com.letionik.matinee.exception.EventEnrollException;
import com.letionik.matinee.model.*;
import com.letionik.matinee.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Service
public class EventService {
    private static final int TASKS_PER_PARTICIPANT_COUNT = 3;
    private static final String ROLE_SUBJECT = "role";
    private static final String TASKS_SUBJECT = "tasks";
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
    @Autowired
    private MailService mailService;

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
        admin.setType(ParticipantType.ADMIN);
        event.addParticipant(admin);
        eventRepository.save(event);
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto enroll(String code, Long userId) throws EventEnrollException {
        Event event = eventRepository.findOneByCode(code);
        if (event == null || event.getStatus() != EventStatus.NEW) throw new EventEnrollException(code);
        for (Participant participant : event.getParticipants()) {
            if (participant.getUser().getId().equals(userId)) throw new EventEnrollException(code);
        }
        Participant participant = new Participant(userRepository.findOne(userId), event);
        participant.setType(ParticipantType.PARTICIPANT);
        participantRepository.save(participant);
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto revealTasks(Long eventID) {
        Event event = eventRepository.findOne(eventID);
        event.setStatus(EventStatus.TASKS_REVEALED);
        List<Participant> participants = event.getParticipants();

        Stack<Task> tasks = new Stack<>();
        final int totalTasksCount = event.getParticipants().size() * TASKS_PER_PARTICIPANT_COUNT;
        tasks.addAll(taskRepository.getRandomTasks(totalTasksCount));
        participants.forEach(participant ->
                IntStream.range(0, TASKS_PER_PARTICIPANT_COUNT)
                        .forEach(i -> participant.getProgressTasks().add(tightTaskAndParticipant(tasks.pop(), participant))));
        participants.forEach(participant ->  mailService.sendMail(participant.getId(),TASKS_SUBJECT));
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto revealRoles(Long eventId) {
        Event event = eventRepository.getOne(eventId);
        event.setStatus(EventStatus.ROLES_REVEALED);

        List<Participant> participants = event.getParticipants();
        Stack<Role> roles = new Stack<>();
        roles.addAll(roleRepository.getRolesByPriority(participants.size()));
        participants.forEach(participant -> {
            participant.setRole(roles.pop());
            mailService.sendMail(participant.getId(), ROLE_SUBJECT);
        });
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional
    public EventDto closeEvent(Long eventId) {
        Event event = eventRepository.getOne(eventId);
        event.setStatus(EventStatus.FINISHED);
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional(readOnly = true)
    public EventDto getEvent(Long id) {
        Event event = eventRepository.findOne(id);
        return modelMapper.map(event, EventDto.class);
    }

    @Transactional(readOnly = true)
    public List<EventDto> getEvents(Long userId) {
        List<Event> events = eventRepository.findAllByUserId(userId);
        return events.stream()
                .map(event -> modelMapper.map(event, EventDto.class))
                .collect(Collectors.toList());
    }

    private TaskProgress tightTaskAndParticipant(Task task, Participant participant) {
        TaskProgress taskProgress = new TaskProgress();
        taskProgress.setTask(task);
        taskProgress.setParticipant(participant);
        taskProgressRepository.save(taskProgress);
        return taskProgress;
    }
}