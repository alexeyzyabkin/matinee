package com.letionik.matinee.model;

import com.letionik.matinee.Sex;
import org.hibernate.validator.constraints.URL;
import javax.persistence.*;

import javax.validation.constraints.NotNull;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NotNull
    @Column(name = "user_login")
    private String login;
    @NotNull
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_surname")
    private String surname;
    @URL
    @Column(name = "user_avatar_url")
    private String avatarURL;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_sex")
    private Sex sex;

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }
}
