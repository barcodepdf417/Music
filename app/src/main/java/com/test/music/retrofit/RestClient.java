package com.test.music.retrofit;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import retrofit.RestAdapter;
import retrofit.client.OkClient;


public class RestClient {

    private static Api REST_CLIENT;
    private static String ROOT =
            "https://dl.dropboxusercontent.com/s/hggb2a5ef9geber/";
    static {
        setupRestClient();
    }

    private RestClient() {}

    public static Api get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        Cache cache = null;
        try {
            cache = new Cache(new File("C://"), 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient().setCache(cache)))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(Api.class);
    }
}