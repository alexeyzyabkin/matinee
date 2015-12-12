package com.letionik.matinee.controller;

import com.letionik.matinee.TaskProgressDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @RequestMapping(value = "/done/{taskId}", method = RequestMethod.POST)
    public TaskProgressDto markAsDone(@PathVariable String taskId) {

        return new TaskProgressDto();
    }
}