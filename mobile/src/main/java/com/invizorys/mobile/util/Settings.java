package com.invizorys.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Paryshkura Roman on 13.12.2015.
 */
public class Settings {

    public static final String APP_NAME = "Matinee";
    public static final String TOKEN = "token";

    public static void saveToken(Context context, String token) {
        SharedPreferences mPrefs = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(TOKEN, token);
        prefsEditor.apply();
    }

    public static String fetchToken(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return mPrefs.getString(TOKEN, null);
    }

}
