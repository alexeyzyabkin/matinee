package com.letionik.matinee;

import java.util.Date;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public class CreateEventRequestDto {
    private String name;
    private Date startDate;

    public CreateEventRequestDto() {
    }

    public CreateEventRequestDto(String name, Date startDate) {
        this.name = name;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
