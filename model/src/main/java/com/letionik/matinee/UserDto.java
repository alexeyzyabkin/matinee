package com.letionik.matinee;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private Sex sex;
    private String avatarUrl;

    public UserDto() {
    }

    public UserDto(String name, String surname, Sex sex, String avatarUrl) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
