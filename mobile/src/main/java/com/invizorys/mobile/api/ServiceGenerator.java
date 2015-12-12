package com.invizorys.mobile.api;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public class ServiceGenerator {
    private ServiceGenerator() {
    }

    public static <T> T createService(Class<T> serviceClass, String baseUrl) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(new OkHttpClient()));

        RestAdapter restAdapter = builder.build();

        return restAdapter.create(serviceClass);
    }
}
