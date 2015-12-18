package com.letionik.matinee.controller;

import com.letionik.matinee.UserDto;
import com.letionik.matinee.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public UserDto authorize(@RequestBody UserDto user, String token, HttpSession session) {
        String url = "https://api.vk.com/method/users.get?access_token=" + token;
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            if (connection != null) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                if (in.readLine().substring(2, 10).equals("response")) {
                    userService.authorize(user);
                    session.setAttribute("user", user.getId());
                    return user;
                }
            }
        } catch (IOException e) {
            log.error("Not relevant token");
        }
        return null;
    }
}