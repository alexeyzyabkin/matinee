package com.letionik.matinee.exception;

/**
 * Created by Alexey Zyabkin on 19.12.2015.
 */
public class EventEnrollException extends Throwable {
    private String eventCode;

    public EventEnrollException(String eventCode) {
        this.eventCode = eventCode;
    }
}
