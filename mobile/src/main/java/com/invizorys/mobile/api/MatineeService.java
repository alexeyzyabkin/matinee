package com.invizorys.mobile.api;

import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.UserDto;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public interface MatineeService {
    String BASE_URL = "http://192.168.1.109:8080";

    @POST("/user")
    void register(@Body UserDto userDto, RetrofitCallback<UserDto> userDtoRetrofitCallback);

    @POST("/event")
    void  createEvent(@Body CreateEventRequestDto createEventRequestDto,
                      RetrofitCallback<EventDto> eventDtoRetrofitCallback);
}
