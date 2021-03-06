package com.invizorys.mobile.network.api;

import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.UserDto;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public interface MatineeService {
    String BASE_URL = "http://matinee-letionik.rhcloud.com";

    @POST("/user")
    void register(@Body UserDto userDto, RetrofitCallback<UserDto> userDtoRetrofitCallback);

    //TODO need to change it to POST
    @PUT("/event")
    void createEvent(@Body CreateEventRequestDto createEventRequestDto,
                     RetrofitCallback<EventDto> eventDtoRetrofitCallback);

    @POST("/event/enroll/{code}")
    void enroll(@Path("code") String code, RetrofitCallback<EventDto> eventDtoRetrofitCallback);

    @POST("/event/reveal/tasks/{eventId}")
    void revealTasks(@Path("eventId") Long eventId, RetrofitCallback<EventDto> eventDtoRetrofitCallback);

    @POST("/event/reveal/roles/{eventId}")
    void revealRoles(@Path("eventId") Long eventId, RetrofitCallback<EventDto> eventDtoRetrofitCallback);

    @POST("/event/history/{eventId}")
    void getHistory(@Path("eventId") String eventId, RetrofitCallback<List<TaskProgressDto>> retrofitCallback);

    @GET("/event/{eventId}")
    void getEvent(@Path("eventId") Long eventId, RetrofitCallback<EventDto> eventDtoRetrofitCallback);

    @POST("/task/done/{taskId}")
    void markTaskAsDone(@Path("taskId") Long taskId, RetrofitCallback<Object> retrofitCallback);

    @GET("/event")
    void getEvents(RetrofitCallback<List<EventDto>> retrofitCallback);

    @POST("/email")
    void addParticipantEmail(@Path("email") String email, RetrofitCallback<ParticipantDto> participantDtoRetrofitCallback);
}