package com.letionik.matinee.converter;

import com.letionik.matinee.UserDto;
import com.letionik.matinee.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@Component
public class UserDtoConverter implements Converter<UserDto, User>{
    @Override
    public UserDto convertTo(User obj) {
        UserDto userDto = new UserDto();
        userDto.setId(obj.getId());
        userDto.setName(obj.getName());
        userDto.setAvatarUrl(obj.getAvatarURL());
        userDto.setSex(obj.getSex());
        userDto.setSurname(obj.getSurname());
        return userDto;
    }
}
