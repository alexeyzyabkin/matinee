package com.invizorys.mobile.api;

import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.UserDto;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public interface MatineeService {
    String BASE_URL = "http://192.168.11.179:8080";

    @POST("/user")
    void register(@Body UserDto userDto, RetrofitCallback<UserDto> userDtoRetrofitCallback);

    @POST("/event")
    void createEvent(@Body CreateEventRequestDto createEventRequestDto,
                     RetrofitCallback<EventDto> eventDtoRetrofitCallback);

    @POST("/event/enroll/{code}")
    void enroll(@Path("code") String code, RetrofitCallback<EventDto> userDtoRetrofitCallback);

    @POST("/event/reveal/tasks/{eventId}")
    void revealTasks(@Path("eventId") Long eventId, RetrofitCallback<EventDto> userDtoRetrofitCallback);

    @POST("/event/reveal/roles/{eventId}")
    void revealRoles(@Path("eventId") Long eventId, RetrofitCallback<EventDto> userDtoRetrofitCallback);

    @POST("/event/history/{eventId}")
    void getHistory(@Path("eventId") String eventId, RetrofitCallback<List<TaskProgressDto>> userDtoRetrofitCallback);

    @GET("/event/{eventId}")
    void getCurrentEvent(@Path("eventId") Long eventId, RetrofitCallback<EventDto> userDtoRetrofitCallback);
}
