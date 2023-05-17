package com.example.myapplication.service;

import com.example.myapplication.models.AuthenticationRequest;
import com.example.myapplication.models.AuthenticationResponse;
import com.example.myapplication.models.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

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

public interface ProductService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ProductService productService = new Retrofit.Builder()
            .baseUrl("http://192.168.70.160:8080")

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductService.class);

    @GET("/api/v1/product/getall")
    Call<List<Product>> findAll();

    @GET("/api/v1/product/findByBrandID/{id}")
    Call<List<Product>> findByBrandID(@Path("id") Long id);

    @GET("/api/v1/product/findFavoriteByAccountID/{id}")
    Call<List<Product>> findAllProductFavorite(@Path("id") Long id,
                                              @Header("Authorization") String authorization);
    @GET("/api/v1/product/findHistoryByAccountID/{id}")
    Call<List<Product>> findAllProductHistory(@Path("id") Long id,
                                              @Header("Authorization") String authorization);
    @GET("/api/v1/product/searchProductWithPrice")
    Call<List<Product>> searchProductWithPrice(@Query("productName") String productName,
                                               @Query("priceFrom") Float priceFrom,
                                               @Query("priceTo") Float priceTo);
    @GET("/api/v1/product/searchProductWithoutPrice")
    Call<List<Product>> searchProductWithoutPrice(@Query("productName") String productName);
    @PUT("/api/v1/product/updateproductquantity/{productID}")
    Call<Boolean> updateProductQuantity(@Path("productID") Long productID,
                                        @Query("quantity") int quantity,
                                        @Query("flag") Boolean flag,
                                        @Header("Authorization") String authorization);
}
