package com.invizorys.mobile;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public class MatineeAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
