package com.test.music.retrofit;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;


public interface Api {
    @GET("/{url}")
    void getJournal(@Path("url") String cityName,
                    Callback<ArtistResponse> callback);
}
