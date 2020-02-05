package com.track.salesmaster.rest;

import com.track.salesmaster.response.LoginResponse;
import com.track.salesmaster.response.RegisterResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestInterface {

    @FormUrlEncoded
    @POST("LoginRegistration/userLogin")
    Call<LoginResponse> login(@Field("userEmail") String userEmail,
                             @Field("userPassword") String userPassword
                              );

    @FormUrlEncoded
    @POST("LoginRegistration/registerUser")
    Call<RegisterResponse> register(@Field("userEmail") String userEmail,
                                 @Field("userPassword") String userPassword,
                                 @Field("userName") String userName,
                                 @Field("userMobile") String userMobile
    );

}
