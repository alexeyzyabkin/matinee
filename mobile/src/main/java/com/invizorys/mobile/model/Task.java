package com.invizorys.mobile.model;

/**
 * Created by Paryshkura Roman on 20.12.2015.
 */
public class Task {
    private String text;

    public Task(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
