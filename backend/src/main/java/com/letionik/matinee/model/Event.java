package com.letionik.matinee.model;

import com.letionik.matinee.EventStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;
    @NotNull
    @Column(name = "event_name")
    private String name;
    @Column(name = "event_creation_date_time")
    private LocalDateTime creationDate;
    @Column(name = "event_start_date_time")
    private LocalDateTime startDate;
    @OneToMany(mappedBy = "event")
    private List<Participant> participants = new ArrayList<>();
    @Column(name = "event_code")
    private String code;
    @Column(name = "event_status")
    private EventStatus status;

    public void addParticipant(Participant p) {
        participants.add(p);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
