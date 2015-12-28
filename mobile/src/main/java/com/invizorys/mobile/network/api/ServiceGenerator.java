package com.invizorys.mobile.network.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.invizorys.mobile.BuildConfig;
import com.invizorys.mobile.util.Settings;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public class ServiceGenerator {
    private ServiceGenerator() {
    }

    public static <T> T createService(Class<T> serviceClass, String baseUrl, final Context context) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    @Override
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                })
                .create();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setLogLevel(BuildConfig.DEBUG ?
                        RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(new OkHttpClient()))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json;versions=1");
                        request.addHeader("token", getToken(context));
                    }
                });

        RestAdapter restAdapter = builder.build();
        return restAdapter.create(serviceClass);
    }

    //TODO handle empty token
    private static String getToken(Context context) {
        return Settings.fetchToken(context);
    }
}