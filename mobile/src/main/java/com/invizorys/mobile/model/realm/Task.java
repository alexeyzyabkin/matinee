package com.invizorys.mobile.model.realm;

import com.letionik.matinee.TaskDto;

import io.realm.RealmObject;

/**
 * Created by Paryshkura Roman on 09.01.2016.
 */
public class Task extends RealmObject {
    private Long id;
    private String name;
    private String description;
    private String type;

    public Task() {
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(TaskDto taskDto) {
        this.id = taskDto.getId();
        this.name = taskDto.getName();
        this.description = taskDto.getDescription();
        this.type = taskDto.getType().toString();
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
