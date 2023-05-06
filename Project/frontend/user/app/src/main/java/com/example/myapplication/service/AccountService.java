package com.example.myapplication.service;

import com.example.myapplication.models.Account;
import com.example.myapplication.models.AuthenticationRequest;
import com.example.myapplication.models.AuthenticationResponse;
import com.example.myapplication.models.DTO.AccountDTO;
import com.example.myapplication.models.RegisterResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    AccountService accountService = new Retrofit.Builder()
//            .baseUrl("http://192.168.70.160:8080")
            .baseUrl("http://192.168.0.102:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AccountService.class);

    @POST("/api/v1/auth/register")
    Call<RegisterResponse> register(@Body Account account);

    @POST("/api/v1/auth/authenticate")
    Call<AuthenticationResponse> authenticate(@Body AuthenticationRequest request);
    @GET("/api/v1/account/getbyid/{id}")
    Call<AccountDTO> findById(@Path("id") Long id);
    @PUT("/api/v1/account/editaccount/{id}")
    Call<AccountDTO> update(@Body AccountDTO accountDTO,
                            @Path("id") Long id,
                            @Header("Authorization") String authorization);
    @PUT("/api/v1/account/changeAccount/{id}")
    Call<Boolean> changePassword(@Path("id") Long id,
                                 @Query("oldPassword") String oldPassword,
                                 @Query("newPassword") String newPassword,
                                 @Header("Authorization") String authorization);
    @PUT("/api/v1/account/forgotPassword")
    Call<Boolean> forgotPassword(@Query("email") String email);
}
