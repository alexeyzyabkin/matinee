package com.invizorys.mobile.model;

/**
 * @author Created by rparishkura@rightandabove.com at 11.01.2016
 * @author Last modified by $Author$ <br>
 * @author Last modified on $LastChangedDate$ at revision $Revision$ <br>
 */
public class EventUpdated {
    private boolean isSuccessful;

    public EventUpdated(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }
}
