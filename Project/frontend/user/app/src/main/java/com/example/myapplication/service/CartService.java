package com.example.myapplication.service;

import com.example.myapplication.models.Cart;
import com.example.myapplication.models.CartKey;
import com.example.myapplication.models.DTO.CartDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    CartService cartService = new Retrofit.Builder()
            .baseUrl("http://192.168.70.160:8080")

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CartService.class);

    @GET("/api/v1/cart/getall")
    Call<List<Cart>> getAll(@Query("accountID") Long accountID,
                            @Header("Authorization") String authorization);

    @POST("/api/v1/cart/addcart")
    Call<Boolean> save(@Body CartDTO cartDTO,
                       @Header("Authorization") String authorization);

    @HTTP(method = "DELETE", path = "/api/v1/cart/delete", hasBody = true)
    Call<Boolean> delete(@Body CartKey cartKey,
                         @Header("Authorization") String authorization);

    @DELETE("/api/v1/cart/deletebyaccountid/{accountID}")
    Call<Long> deleteByAccountId(@Path("accountID") Long accountID,
                                 @Header("Authorization") String authorization
    );
}
