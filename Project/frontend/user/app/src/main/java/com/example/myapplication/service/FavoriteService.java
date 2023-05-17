package com.example.myapplication.service;

import com.example.myapplication.models.DTO.AccountDTO;
import com.example.myapplication.models.Favorite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FavoriteService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    FavoriteService favoriteService = new Retrofit.Builder()
            .baseUrl("http://192.168.70.160:8080")

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(FavoriteService.class);

    @GET("/api/v1/favorite/getfavoritebyproductid/{id}")
    Call<Long> getFavorite(@Path("id") Long productID);

    @GET("/api/v1/favorite/getfavoritebyid")
    Call<Boolean> getFavoriteById(@Query("accountID") Long accountID,
                                  @Query("productID") Long productID,
                                  @Header("Authorization") String authorization);
    @POST("/api/v1/favorite/addfavorite")
    Call<Boolean> save(@Query("accountID") Long accountID,
                        @Query("productID") Long productID,
                        @Header("Authorization") String authorization);
    @DELETE("/api/v1/favorite/deletefavorite")
    Call<Boolean> delete(@Query("accountID") Long accountID,
                       @Query("productID") Long productID,
                       @Header("Authorization") String authorization);
}
