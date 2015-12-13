package com.letionik.matinee.service;

import com.letionik.matinee.UserDto;
import com.letionik.matinee.model.User;
import com.letionik.matinee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Bohdan Pohotilyi on 12.12.2015.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public UserDto authorize(UserDto userDto) {
        User user = userRepository.findOneByLogin(userDto.getLogin());
        if (user == null) {
            user = userRepository.save(modelMapper.map(userDto, User.class));
        }
        userDto.setId(user.getId());
        return userDto;
    }
}