package com.letionik.matinee.model;

import com.letionik.matinee.TaskStatus;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

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
    @ManyToOne
    @JoinColumn(name = "task_progress_task_id", foreignKey = @ForeignKey(name = "task_progress_task_fk"))
    private Task task;
    @Column(name = "task_progress_executive_time")
    private LocalDateTime executiveTime;
    @ManyToOne
    @JoinColumn(name = "task_progress_participant_id",foreignKey = @ForeignKey(name = "task_progress_participant_fk"))
    private Participant participant;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "task_progress_task_status")
    private TaskStatus status = TaskStatus.NEW;

    public Long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public LocalDateTime getExecutiveTime() {
        return executiveTime;
    }

    public void setExecutiveTime(LocalDateTime executiveTime) {
        this.executiveTime = executiveTime;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
