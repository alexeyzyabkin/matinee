package com.letionik.matinee;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public class ParticipantDto {
    private Long id;
    private UserDto userDto;
    private LocalDateTime comeInDate;
    private RoleDto role;
    private List<TaskProgressDto> tasks;

    public Long getId() {
        return id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public LocalDateTime getComeInDate() {
        return comeInDate;
    }

    public void setComeInDate(LocalDateTime comeInDate) {
        this.comeInDate = comeInDate;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public List<TaskProgressDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskProgressDto> tasks) {
        this.tasks = tasks;
    }
}