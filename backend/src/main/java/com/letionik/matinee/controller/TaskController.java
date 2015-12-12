package com.letionik.matinee.controller;

import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.repository.TaskProgressRepository;
import com.letionik.matinee.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/done/{taskId}", method = RequestMethod.POST)
    public TaskProgressDto markAsDone(@PathVariable Long taskId) {
        return taskService.markAsDone(taskId);
    }
}