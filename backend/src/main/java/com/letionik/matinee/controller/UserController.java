package com.letionik.matinee.controller;

import com.letionik.matinee.UserDto;
import com.letionik.matinee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public UserDto authorize(@RequestBody UserDto user, HttpSession session) {
        userService.authorize(user);
        session.setAttribute("user", user.getId());
        return user;
    }
}
