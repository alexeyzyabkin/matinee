package com.invizorys.mobile.model;

import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.ParticipantType;
import com.letionik.matinee.RoleDto;
import com.letionik.matinee.TaskProgressDto;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Paryshkura Roman on 30.12.2015.
 */
public class Participant extends RealmObject {
    private Long id;
    private User user;
    private String email;
    private Date comeInDate;
    @Ignore
    private RoleDto role;
    @Ignore
    private List<TaskProgressDto> tasks;
    @Ignore
    private ParticipantType type;

    public Participant() {
    }

    public Participant(ParticipantDto participantDto) {
        this.id = participantDto.getId();
        this.user = new User(participantDto.getUser());
        this.email = participantDto.getEmail();
        this.comeInDate = participantDto.getComeInDate();
        this.role = participantDto.getRole();
        this.tasks = participantDto.getTasks();
        this.type = participantDto.getType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
