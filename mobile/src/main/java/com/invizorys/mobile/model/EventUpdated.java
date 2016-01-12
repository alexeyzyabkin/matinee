package com.invizorys.mobile.model;

/**
 * @author Created by rparishkura@rightandabove.com at 11.01.2016
 * @author Last modified by $Author$ <br>
 * @author Last modified on $LastChangedDate$ at revision $Revision$ <br>
 */
public class EventUpdated {
    private long eventId;
    private boolean isSuccessful;

    public EventUpdated(long eventId, boolean isSuccessful) {
        this.eventId = eventId;
        this.isSuccessful = isSuccessful;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }
}
