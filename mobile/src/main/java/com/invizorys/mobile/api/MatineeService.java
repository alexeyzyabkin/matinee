package com.invizorys.mobile.api;

import com.letionik.matinee.UserDto;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public interface MatineeService {
    String BASE_URL = "http://test.url";

    @POST("/user")
    void register(@Body UserDto userDto, RetrofitCallback<UserDto> retrofitCallback);
}
