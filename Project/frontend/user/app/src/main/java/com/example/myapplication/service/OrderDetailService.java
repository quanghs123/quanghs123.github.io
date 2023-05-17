package com.example.myapplication.service;

import com.example.myapplication.models.DTO.AccountDTO;
import com.example.myapplication.models.DTO.OrderDetailDTO;
import com.example.myapplication.models.Order;
import com.example.myapplication.models.OrderDetail;
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

public interface OrderDetailService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    OrderDetailService orderDetailService = new Retrofit.Builder()
            .baseUrl("http://192.168.70.160:8080")

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(OrderDetailService.class);

    @GET("/api/v1/orderdetail/getbyorderid/{id}")
    Call<List<OrderDetail>> getByOrderId(@Path("id") Long id, @Header("Authorization") String authorization);

    @POST("/api/v1/orderdetail/addorderdetail")
    Call<OrderDetail> save(@Body OrderDetailDTO orderDetailDTO, @Header("Authorization") String authorization);

    @DELETE("/api/v1/orderdetail/deleteorderdetail/{orderID}")
    Call<Long> deleteOrderDetailByOrderID(@Path("orderID") Long orderID,@Header("Authorization") String authorization);
}
