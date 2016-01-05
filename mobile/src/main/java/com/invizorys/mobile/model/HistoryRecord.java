package com.invizorys.mobile.model;

import java.util.Date;

/**
 * Created by Paryshkura Roman on 05.01.2016.
 */
public class HistoryRecord {
    private String taskName;
    private String userName;
    private Date taskDone;

    public HistoryRecord() {
    }

    public HistoryRecord(String userName, String taskName, Date taskDone) {
        this.userName = userName;
        this.taskName = taskName;
        this.taskDone = taskDone;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTaskDone() {
        return taskDone;
    }

    public void setTaskDone(Date taskDone) {
        this.taskDone = taskDone;
    }
}
