package com.letionik.matinee.service;

import com.letionik.matinee.UserDto;
import com.letionik.matinee.exception.InvalidTokenException;
import com.letionik.matinee.model.User;
import com.letionik.matinee.repository.UserRepository;
import com.letionik.matinee.utils.UrlConnectionReader;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created by Bohdan Pohotilyi on 12.12.2015.
 */
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private static final String VK_API_URL = "https://api.vk.com/method/users.get?access_token={token}";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UrlConnectionReader urlConnectionReader;

    @Transactional
    public UserDto authorize(UserDto userDto, String token) throws InvalidTokenException {
        if (!isTokenValid(token, userDto.getLogin())) throw new InvalidTokenException();
        User user = userRepository.findOneByLogin(userDto.getLogin());
        if (user == null) {
            user = userRepository.save(modelMapper.map(userDto, User.class));
        }
        userDto.setId(user.getId());
        return userDto;
    }

    private boolean isTokenValid(String token, String userId) {
        String url = VK_API_URL.replace("{token}", token);
        try {
            JSONObject response = new JSONObject(urlConnectionReader.getText(url));
            String login = String.valueOf(response.getJSONArray("response").getJSONObject(0).getLong("uid"));
            return login.equals(userId);
        } catch (IOException e) {
            log.error("Exception during token validation", e);
        } catch (NullPointerException e) {
            log.error("Wrong response structure", e);
        }
        return false;
    }
}