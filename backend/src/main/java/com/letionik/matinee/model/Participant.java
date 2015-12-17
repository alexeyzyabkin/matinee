package com.letionik.matinee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @ManyToOne
    @JoinColumn(name = "participand_user_id", foreignKey = @ForeignKey(name = "paricipant_user_fk"))
    private User user;
    @ManyToOne
    @JoinColumn(name = "participant_role_id", foreignKey = @ForeignKey(name = "paricipant_role_fk"))
    private Role role;
    @OneToMany(mappedBy = "participant")
    private List<TaskProgress> progressTasks = new ArrayList<>();
    @NotNull
    @ManyToOne
    @JoinColumn(name = "participant_event_id", foreignKey = @ForeignKey(name = "paricipant_event_fk"))
    private Event event;
    @Enumerated(EnumType.STRING)
    @Column(name = "participant_status")
    private ParticipantStatus status;

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

    public ParticipantStatus getStatus() {
        return status;
    }

    public void setStatus(ParticipantStatus status) {
        this.status = status;
    }
}
