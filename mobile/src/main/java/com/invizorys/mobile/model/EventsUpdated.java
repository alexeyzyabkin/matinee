package com.invizorys.mobile.model;

/**
 * Created by Paryshkura Roman on 27.12.2015.
 */
public class EventsUpdated {
    private boolean isSuccessful;

    public EventsUpdated(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }
}
