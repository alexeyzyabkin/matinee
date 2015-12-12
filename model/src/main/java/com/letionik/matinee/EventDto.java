package com.letionik.matinee;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public class EventDto {
    private Long id;
    private String name;
    private UserDto admin;
    private List<ParticipantDto> participants;
    private LocalDateTime startDate;
    private EventStatus eventStatus;
    private LocalDateTime creationDate;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getId() {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}
