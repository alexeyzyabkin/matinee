package com.letionik.matinee.controller;

import com.letionik.matinee.UserDto;
import com.letionik.matinee.exception.InvalidTokenException;
import com.letionik.matinee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<UserDto> authorize(@RequestBody UserDto user, HttpServletRequest request, HttpSession session) {
        String token = request.getHeader("token");
        try {
            UserDto userDto = userService.authorize(user, token);
            session.setAttribute("user", userDto.getId());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (InvalidTokenException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}