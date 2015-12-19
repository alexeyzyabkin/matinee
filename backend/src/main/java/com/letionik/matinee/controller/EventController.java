package com.letionik.matinee.controller;

import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.exception.EventNotFoundOrInWrongStateException;
import com.letionik.matinee.model.Participant;
import com.letionik.matinee.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.SortedMap;

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
    public EventDto getCurrentEvent(@PathVariable Long eventId) {
        return eventService.getEventInfo(eventId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public EventDto createEvent(@RequestBody CreateEventRequestDto createEventRequest, HttpSession session) {
        return eventService.createEvent(createEventRequest, (Long) session.getAttribute("user"));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/enroll/{code}")
    public ResponseEntity<EventDto> enroll(@PathVariable String code, HttpSession session) {
        try {
            final EventDto event = eventService.enroll(code, (Long) session.getAttribute("user"));
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (EventNotFoundOrInWrongStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reveal/tasks/{eventId}", method = RequestMethod.POST)
    public EventDto revealTasks(@PathVariable Long eventId) {
        return eventService.revealTasks(eventId);
    }

    @RequestMapping(value = "/reveal/roles/{eventId}", method = RequestMethod.POST)
    public EventDto revealRoles(@PathVariable Long eventId) {
        return eventService.revealRoles(eventId);
    }

    @RequestMapping(value = "history/{eventId}", method = RequestMethod.GET)
    public List<TaskProgressDto> getHistory(@PathVariable Long eventId) {
        return eventService.getHistory(eventId);
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.POST)
    public SortedMap<Long, Participant> closeEvent(@PathVariable Long eventId) {
        return eventService.closeEvent(eventId);
    }
}