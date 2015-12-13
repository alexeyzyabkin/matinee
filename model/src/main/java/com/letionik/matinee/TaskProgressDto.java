package com.letionik.matinee;

import java.util.Date;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public class TaskProgressDto {
    private Long id;
    private TaskDto task;
    private TaskStatus status;
    private Date doneDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskDto getTask() {
        return task;
    }

    public void setTask(TaskDto task) {
        this.task = task;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }
}
