package com.letionik.matinee.exception;

/**
 * Created by Alexey Zyabkin on 19.12.2015.
 */
public class EventNotFoundOrInWrongStateException extends Throwable {
    private String eventCode;

    public EventNotFoundOrInWrongStateException(String eventCode) {
        this.eventCode = eventCode;
    }
}
