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
    @Column(name = "role_description")
    private String description;
    @Column(name = "role_picture_url")
    private String pictureUrl;
    @Column(name = "role_priority")
    private int priority;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
