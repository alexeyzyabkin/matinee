package com.invizorys.mobile.model.realm;

import com.letionik.matinee.TaskProgressDto;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Paryshkura Roman on 09.01.2016.
 */
public class TaskProgress extends RealmObject {
    private Long id;
    private Task task;
    private String status;
    private Date doneDate;

    public TaskProgress() {
    }

    public TaskProgress(TaskProgressDto taskProgressDto) {
        this.id = taskProgressDto.getId();
        this.task = new Task(taskProgressDto.getTask());
        this.status = taskProgressDto.getStatus().toString();
        this.doneDate = taskProgressDto.getDoneDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }
}
