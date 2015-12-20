package com.letionik.matinee;

import java.util.Date;
import java.util.List;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public class ParticipantDto {
    private Long id;
    private UserDto user;
    private Date comeInDate;
    private RoleDto role;
    private List<TaskProgressDto> tasks;
    private ParticipantType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Date getComeInDate() {
        return comeInDate;
    }

    public void setComeInDate(Date comeInDate) {
        this.comeInDate = comeInDate;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public List<TaskProgressDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskProgressDto> tasks) {
        this.tasks = tasks;
    }

    public ParticipantType getType() {
        return type;
    }

    public void setType(ParticipantType type) {
        this.type = type;
    }
}