package com.invizorys.mobile.model.realm;

import com.letionik.matinee.RoleDto;

import io.realm.RealmObject;

/**
 * Created by Paryshkura Roman on 09.01.2016.
 */
public class Role extends RealmObject {
    private Long id;
    private String name;
    private String avatarUrl;

    public Role() {
    }

    public Role(RoleDto roleDto) {
        this.id = roleDto.getId();
        this.name = roleDto.getName();
        this.avatarUrl = roleDto.getAvatarUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
