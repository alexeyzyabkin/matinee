package com.invizorys.mobile.model.realm;

import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.ParticipantType;
import com.letionik.matinee.RoleDto;
import com.letionik.matinee.TaskProgressDto;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Paryshkura Roman on 30.12.2015.
 */
public class Participant extends RealmObject {
    private Long id;
    private User user;
    private String email;
    private Date comeInDate;
    private Role role;
    private RealmList<TaskProgress> tasks;
    private String participantType;

    public Participant() {
    }

    public Participant(ParticipantDto participantDto) {
        this.id = participantDto.getId();
        this.user = new User(participantDto.getUser());
        this.email = participantDto.getEmail();
        this.comeInDate = participantDto.getComeInDate();
        RoleDto roleDto = participantDto.getRole();
        if (roleDto != null) {
            this.role = new Role(participantDto.getRole());
        }
        this.tasks = convertTasksDtoToTasks(participantDto.getTasks());
        ParticipantType participantType = participantDto.getType();
        if (participantType != null) {
            this.participantType = participantDto.getType().toString();
        }
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public RealmList<TaskProgress> getTasks() {
        return tasks;
    }

    public void setTasks(RealmList<TaskProgress> tasks) {
        this.tasks = tasks;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
        this.participantType = participantType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static RealmList<TaskProgress> convertTasksDtoToTasks(List<TaskProgressDto> taskProgressDtos) {
        RealmList<TaskProgress> result = new RealmList<>();
        for (TaskProgressDto taskProgressDto : taskProgressDtos) {
            TaskProgress taskProgress = new TaskProgress(taskProgressDto);
            result.add(taskProgress);
        }
        return result;
    }

    public static ParticipantType getEventStatusEnum(Participant participant) {
        return ParticipantType.valueOf(participant.getParticipantType());
    }

    public static void setEventStatusEnum(Participant participant, ParticipantType enumValue) {
        participant.setParticipantType(enumValue.toString());
    }
}
