package com.letionik.matinee.controller;

import com.letionik.matinee.EventDto;
import com.letionik.matinee.TaskProgressDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@RestController
@RequestMapping(value = "/event")
public class EventController {
    @RequestMapping(method = RequestMethod.GET, name = "/current")
    public EventDto getCurrentEvent() {
        return new EventDto();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/enroll/{code}")
    public EventDto enroll(@PathVariable String code) {
        return new EventDto();
    }

    @RequestMapping(value = "/reveal/tasks/{eventId}", method = RequestMethod.POST)
    public EventDto revealTasks() {
        return new EventDto();
    }

    @RequestMapping(value = "/reveal/roles/{eventId}", method = RequestMethod.POST)
    public EventDto revealRoles() {
        return new EventDto();
    }

    @RequestMapping(value = "/history/{eventId}", method = RequestMethod.GET)
    public List<TaskProgressDto> getHistory() {
        return new ArrayList<>();
    }
}