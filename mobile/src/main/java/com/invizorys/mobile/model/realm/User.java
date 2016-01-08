package com.invizorys.mobile.model.realm;

import com.letionik.matinee.Sex;
import com.letionik.matinee.UserDto;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public class User extends RealmObject {
    private Long id;
    private String name;
    private String surname;
    @PrimaryKey
    private String socialId;
    @Ignore
    private Sex sex;
    private String avatarUrl;
    private int age;
    private String birthDate;

    public User() {
    }

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.name = userDto.getName();
        this.surname = userDto.getSurname();
        this.socialId = userDto.getLogin();
        this.sex = userDto.getSex();
        this.avatarUrl = userDto.getAvatarUrl();
    }

    public User(String name, String surname, String avatarUrl) {
        this.name = name;
        this.surname = surname;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getName() {
        return name;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}