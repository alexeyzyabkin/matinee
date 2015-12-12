package com.letionik.matinee.mapper;

import com.letionik.matinee.UserDto;
import com.letionik.matinee.model.User;
import org.modelmapper.PropertyMap;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public class UserMap extends PropertyMap<User, UserDto> {
    @Override
    protected void configure() {
        map().setId(source.getId());
        map().setName(source.getName());
        map().setSurname(source.getSurname());
        map().setSex(source.getSex());
        map().setAvatarUrl(source.getAvatarURL());
    }
}