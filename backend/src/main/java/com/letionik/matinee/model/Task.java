package com.letionik.matinee.model;

import com.letionik.matinee.TaskType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Entity
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "task_name")
    private String name;
    @NotNull
    @Column(name = "task_description")
    private String description;
    @NotNull
    @Column(name = "task_type")
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return  name + "\n\n" + description;
    }
}