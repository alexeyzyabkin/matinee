package com.invizorys.mobile;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public class MatineeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);

        //TODO uncomment when on server side will be implemented token processing
//        Context context = getApplicationContext();
//        Intent loginIntent = new Intent(context, LoginActivity.class);
//        Intent mainIntent = new Intent(context, MainActivity.class);
//        String token = Settings.fetchToken(context);
//        PendingIntent pendingIntent;
//        if (token == null || token.isEmpty()) {
//            pendingIntent = PendingIntent.getActivity(context, 22, loginIntent, 0);
//        } else {
//            pendingIntent = PendingIntent.getActivity(context, 22, mainIntent, 0);
//        }
//        try {
//            pendingIntent.send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//        }
    }
}
