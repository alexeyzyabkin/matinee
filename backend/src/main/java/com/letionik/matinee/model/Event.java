package com.letionik.matinee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @ManyToOne
    @JoinColumn(name = "event_admin_user_id")
    private User admin;
    @OneToMany(mappedBy = "event")
    private List<Participant> participants = new ArrayList<>();
    @Column(name = "event_code")
    private UUID code;

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

    public UUID getCode() {
        return code;
    }

    public void setCode(UUID code) {
        this.code = code;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}
