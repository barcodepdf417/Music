package com.test.music.retrofit;

import retrofit.http.GET;

public class RestClient {
    public static final String SERVER_URL = "https://dropbox.com";
    public static final String DATA_LOCATION_URL = "/s/hggb2a5ef9geber/data.json?dl=1";

    private RestClient() {}

    public interface Api {
        @GET(DATA_LOCATION_URL)
        ArtistResponse getData();
    }
}