package com.test.music.retrofit;

import com.test.music.activity.MainActivity;
import com.test.music.exception.MyErrorHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        injects = MainActivity.class,
        library = true,
        complete = false
)
public class RestService {

    @Provides
    @Named("realService")
    public RestClient.Api getService() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(RestClient.SERVER_URL)
                .setErrorHandler(new MyErrorHandler())
                .build();

        return adapter.create(RestClient.Api.class);
    }


}
