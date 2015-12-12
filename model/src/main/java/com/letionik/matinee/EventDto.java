package com.letionik.matinee;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public class EventDto {
    private Long id;
    private String name;
    private UserDto admin;
    private List<ParticipantDto> participants;
    private Date startDate;
    private EventStatus eventStatus;
    private Date creationDate;
    private UUID code;

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

    public UserDto getAdmin() {
        return admin;
    }

    public void setAdmin(UserDto admin) {
        this.admin = admin;
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
}
