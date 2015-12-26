package com.letionik.matinee.model;

import com.letionik.matinee.ParticipantType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "participant_user_id", foreignKey = @ForeignKey(name = "participant_user_fk"))
    private User user;
    @Column(name = "participant_email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "participant_role_id", foreignKey = @ForeignKey(name = "participant_role_fk"))
    private Role role;
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<TaskProgress> progressTasks = new ArrayList<>();
    //@NotNull
    @ManyToOne
    @JoinColumn(name = "participant_event_id", foreignKey = @ForeignKey(name = "participant_event_fk"))
    private Event event;
    @Enumerated(EnumType.STRING)
    @Column(name = "participant_status")
    private ParticipantType status;

    public Participant(){}

    public Participant(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<TaskProgress> getProgressTasks() {
        return progressTasks;
    }

    public void setProgressTasks(List<TaskProgress> progressTasks) {
        this.progressTasks = progressTasks;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ParticipantType getStatus() {
        return status;
    }

    public void setStatus(ParticipantType status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class Builder {
        private Participant participant = new Participant();

        public Builder setEmail(String email){
            participant.email = email;
            return this;
        }

        public Participant build() {
            return participant;
        }
    }
}
