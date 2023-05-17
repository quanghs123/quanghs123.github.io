package com.example.myapplication.service;

import com.example.myapplication.models.Account;
import com.example.myapplication.models.DTO.AccountDTO;
import com.example.myapplication.models.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    OrderService orderService = new Retrofit.Builder()
            .baseUrl("http://192.168.70.160:8080")

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(OrderService.class);

    @POST("/api/v1/order/addorder")
    Call<Order> save(@Body AccountDTO accountDTO, @Header("Authorization") String authorization);

    @GET("/api/v1/order/getbyaccountid/{id}")
    Call<List<Order>> getByAccountId(@Path(value = "id") Long id, @Header("Authorization") String authorization);

    @GET("/api/v1/order/getbyaccountid1/{id}")
    Call<List<Order>> getByAccountId1(@Path(value = "id") Long id, @Header("Authorization") String authorization);

    @DELETE("/api/v1/order/deleteorder/{id}")
    Call<Boolean> delete(@Path("id") Long id, @Header("Authorization") String authorization);
}
