package com.invizorys.mobile.model;

import com.letionik.matinee.EventDto;
import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.ParticipantType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Paryshkura Roman on 26.12.2015.
 */
public class Event extends RealmObject implements Serializable {
    @PrimaryKey
    private Long id;
    private String name;
    private RealmList<Participant> participants;
    private Date startDate;
    private String eventStatus;
    private Date creationDate;
    private String code;

    public Event() {
    }

    public Event(EventDto eventDto) {
        this.id = eventDto.getId();
        this.name = eventDto.getName();
        this.participants = convertParticipantsDtoToParticipants(eventDto.getParticipants());
        this.startDate = eventDto.getStartDate();
        this.eventStatus = eventDto.getEventStatus().toString();
        this.creationDate = eventDto.getCreationDate();
        this.code = eventDto.getCode();
    }

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

    public RealmList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(RealmList<Participant> participants) {
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

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public static RealmList<Participant> convertParticipantsDtoToParticipants(List<ParticipantDto> participantDtos) {
        RealmList<Participant> result = new RealmList<>();
        for (ParticipantDto participantDto : participantDtos) {
            Participant participant = new Participant(participantDto);
            result.add(participant);
        }
        return result;
    }

    public static ParticipantDto getAdmin(List<ParticipantDto> participants) {
        for (ParticipantDto participant : participants) {
            if (participant.getType() == ParticipantType.ADMIN) return participant;
        }
        return null;
    }

    public static EventStatus getEventStatusEnum(Event event) {
        return EventStatus.valueOf(event.getEventStatus());
    }

    public static void setEventStatusEnum(Event event, EventStatus enumValue) {
        event.setEventStatus(enumValue.toString());
    }
}
