package com.example.myapplication.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ViewService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ViewService viewService = new Retrofit.Builder()
            .baseUrl("http://192.168.70.160:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ViewService.class);

    @POST("/api/v1/view/addview")
    Call<Boolean> save(@Query("accountID") Long accountID,
                       @Query("productID") Long productID,
                       @Header("Authorization") String authorization);
}
