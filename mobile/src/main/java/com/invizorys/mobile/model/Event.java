package com.invizorys.mobile.model;

import com.letionik.matinee.EventStatus;
import com.letionik.matinee.ParticipantDto;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Paryshkura Roman on 26.12.2015.
 */
public class Event extends RealmObject {
    private Long id;
    private String name;
    @Ignore
    private List<ParticipantDto> participants;
    private Date startDate;
    @Ignore
    private EventStatus eventStatus;
    private Date creationDate;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Long getId() {
        return id;
    }

    public List<ParticipantDto> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDto> participants) {
        this.participants = participants;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

//    public ParticipantDto getAdmin() {
//        for (ParticipantDto participant : participants) {
//            if (participant.getType() == ParticipantType.ADMIN) return participant;
//        }
//        return null;
//    }
}
