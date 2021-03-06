package com.letionik.matinee.controller;

import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/done/{taskProgressId}", method = RequestMethod.POST)
    public TaskProgressDto markAsDone(@PathVariable Long taskProgressId) {
        return taskService.markAsDone(taskProgressId);
    }
}