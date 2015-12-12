package com.letionik.matinee.controller;

import com.letionik.matinee.EventDto;
import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@RestController
@RequestMapping(value = "event")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public EventDto getCurrentEvent(HttpSession session) {
        Long userID = (Long) session.getAttribute("user");
        return eventService.getCurrentEvent(userID);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/enroll/{code}")
    public EventDto enroll(@PathVariable UUID code, HttpSession session) {
        eventService.enroll(code, (Long) session.getAttribute("user"));
        return new EventDto();
    }

    @RequestMapping(value = "/reveal/tasks/{eventId}", method = RequestMethod.POST)
    public EventDto revealTasks(@PathVariable Long eventId) {
        return eventService.revealTasks(eventId);
    }

    @RequestMapping(value = "/reveal/roles/{eventId}", method = RequestMethod.POST)
    public EventDto revealRoles() {
        return new EventDto();
    }

    @RequestMapping(value = "history/{eventId}", method = RequestMethod.GET)
    public List<TaskProgressDto> getHistory(@PathVariable String eventId) {
        return new ArrayList<>();
    }
}