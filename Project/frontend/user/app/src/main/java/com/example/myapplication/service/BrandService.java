package com.example.myapplication.service;

import com.example.myapplication.models.Brand;
import com.example.myapplication.models.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface BrandService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    BrandService brandService = new Retrofit.Builder()
            .baseUrl("http://192.168.70.160:8080")

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(BrandService.class);

    @GET("/api/v1/brand/getall")
    Call<List<Brand>> findAll();
}
