package com.letionik.matinee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "role_name")
    private String name;

    @Column(name = "role_costume")
    private String costume;

    @NotNull
    @Column(name = "role_description")
    private String description;

    @OneToOne(mappedBy = "role")
    private Participant participant;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCostume() {
        return costume;
    }

    public void setCostume(String costume) {
        this.costume = costume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}
