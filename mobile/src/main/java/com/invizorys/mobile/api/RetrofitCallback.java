package com.invizorys.mobile.api;

import android.content.Context;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public abstract class RetrofitCallback<T> implements Callback<T> {
    private Context context;
    public static final int INTERNAL_SERVER_ERROR = 500;

    public RetrofitCallback(Context context) {
        this.context = context;
    }

    @Override
    public void failure(RetrofitError error) {
        if (error.getResponse() != null) {
            switch (error.getResponse().getStatus()) {
                case INTERNAL_SERVER_ERROR:
                    break;
                default:
                    Toast.makeText(context, error.getResponse().getStatus() + ": " +
                            error.getResponse().getReason(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

