package com.letionik.matinee.service;

import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.TaskStatus;
import com.letionik.matinee.model.TaskProgress;
import com.letionik.matinee.repository.TaskProgressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by Bohdan Pohotilyi on 12.12.2015.
 */
@Service
public class TaskService {
    @Autowired
    private TaskProgressRepository taskProgressRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public TaskProgressDto markAsDone(Long taskId) {
        //TODO: check that this task is marked by its owner participant
        TaskProgress taskProgress = taskProgressRepository.getTaskFromProcess(taskId);
        taskProgress.setStatus(TaskStatus.DONE);
        return modelMapper.map(taskProgress, TaskProgressDto.class);
    }
}
