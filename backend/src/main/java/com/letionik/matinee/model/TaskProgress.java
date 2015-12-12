package com.letionik.matinee.model;

import com.letionik.matinee.TaskStatus;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Entity
@Table(name = "task_progress")
public class TaskProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_progress_id")
    private Long id;
    private Task task;
    private LocalDateTime executiveTime;
    private Participant participant;
    private TaskStatus status = TaskStatus.NEW;
}
