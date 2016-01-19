package com.letionik.matinee.controller;

import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.exception.EventEnrollException;
import com.letionik.matinee.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@RestController
@RequestMapping(value = "event")
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public EventDto getEvent(@PathVariable Long eventId) {
        return eventService.getEvent(eventId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<EventDto> getEvents(HttpSession session) {
        Long userId = (Long)session.getAttribute("user");
        return eventService.getEvents(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public EventDto create(@RequestBody CreateEventRequestDto createEventRequest, HttpSession session) {
        return eventService.createEvent(createEventRequest, (Long) session.getAttribute("user"));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/enroll/{code}")
    public ResponseEntity<EventDto> enroll(@PathVariable String code, HttpSession session) {
        try {
            final EventDto event = eventService.enroll(code, (Long) session.getAttribute("user"));
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (EventEnrollException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reveal/roles/{eventId}", method = RequestMethod.POST)
    public EventDto revealRoles(@PathVariable Long eventId) {
        return eventService.revealRoles(eventId);
    }

    @RequestMapping(value = "/reveal/tasks/{eventId}", method = RequestMethod.POST)
    public EventDto revealTasks(@PathVariable Long eventId) {
        return eventService.revealTasks(eventId);
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.POST)
    public EventDto close(@PathVariable Long eventId) {
        return eventService.closeEvent(eventId);
    }
}