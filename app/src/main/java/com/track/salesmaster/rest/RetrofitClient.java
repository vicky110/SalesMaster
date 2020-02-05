package com.track.salesmaster.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RetrofitClient {

    private static final String BASE_URL = "http://shiftfast.in/ci/index.php/map/";
    private static RetrofitClient mInstance;
    private static Retrofit retrofit;

    public RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //singleton class
    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public RestInterface getApi() {
        return retrofit.create(RestInterface.class);
    }

}
