package com.letionik.matinee.service;

import com.letionik.matinee.*;
import com.letionik.matinee.model.Event;
import com.letionik.matinee.model.User;
import com.letionik.matinee.repository.EventRepository;
import com.letionik.matinee.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alexey Zyabkin on 19.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test.app.properties")
@SpringApplicationConfiguration(classes = MatineeApplication.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventRepository eventRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(eventService, "modelMapper", modelMapper);
    }

    @Test
    public void testCreateEvent() {
        final long userId = 5L;
        final String testLogin = "testLogin";
        final String eventName = "New Year Party";

        CreateEventRequestDto eventRequestDto = new CreateEventRequestDto();
        eventRequestDto.setName(eventName);
        eventRequestDto.setStartDate(new Date());
        User user = new User();
        user.setId(userId);
        user.setLogin(testLogin);

        when(userRepository.getOne(userId)).thenReturn(user);

        EventDto result = eventService.createEvent(eventRequestDto, userId);

        verify(eventRepository).save(any(Event.class));
        assertEquals(result.getParticipants().size(), 1);
        assertFalse(result.getCode().isEmpty());
        assertSame(result.getParticipants().get(0).getUser().getId(), userId);
        assertEquals(result.getParticipants().get(0).getUser().getLogin(), testLogin);
        assertSame(result.getParticipants().get(0).getStatus(), ParticipantStatus.ADMIN);
        assertNull(result.getParticipants().get(0).getRole());
        assertSame(result.getParticipants().get(0).getTasks().size(), 0);
        assertEquals(result.getName(), eventName);
        assertEquals(result.getEventStatus(), EventStatus.NEW);
    }

    @Test
    public void testEnroll() {

    }

    @Test
    public void testRevealTasks() {

    }

    @Test
    public void testRevealRoles() {

    }

    @Test
    public void testCloseEvent() {

    }

    @Test
    public void testGetHistory() {

    }

    @Test
    public void testGetEventInfo() {

    }
}