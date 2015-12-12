package com.letionik.testtost.model;

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
    @OneToOne(mappedBy = "participant")
    private User user;
    @NotNull
    @OneToOne(mappedBy = "participant")
    private Role role;
    @OneToMany(mappedBy = "participant")
    private List<Task> tasks = new ArrayList<>();
    @NotNull
    @ManyToOne
    @JoinColumn(name = "participant_event_id")
    private Event event;

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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Long getId() {
        return id;
    }
}
