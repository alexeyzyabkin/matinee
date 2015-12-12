package com.letionik.testtost.model;

import com.letionik.testtost.TaskStatus;

import java.time.LocalDateTime;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
public class TaskPerfomance {
    private Long id;
    private Task task;
    private LocalDateTime executiveTime;
    private Participant participant;
    private TaskStatus status = TaskStatus.NEW;
}
