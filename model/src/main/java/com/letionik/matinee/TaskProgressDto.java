package com.letionik.matinee;

import java.time.LocalDateTime;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public class TaskProgressDto {
    private TaskDto task;
    private TaskStatus status;
    private LocalDateTime receivedDate;
    private LocalDateTime doneDate;

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

    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
    }
}
