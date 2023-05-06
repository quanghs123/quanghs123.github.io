package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.models.Product;
import com.example.myapplication.service.ProductService;
import com.example.myapplication.service.ViewService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private View mView;
    private MainActivity mMainActivity;
    private RecyclerView rcvProduct;
    private ProgressDialog progressDialog;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private Button btnSearch;
    private EditText edProductName,edPriceFrom,edPriceTo;
    private TextView tvMessage;
    private Float priceFrom = 0f,priceTo = 0f;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_search, container, false);
        initUi();
        return mView;
    }

    private void initUi() {
        mMainActivity = (MainActivity) getActivity();
        productList = new ArrayList<>();
        rcvProduct = mView.findViewById(R.id.rcv_product);
        progressDialog = new ProgressDialog(mMainActivity);
        edProductName = mView.findViewById(R.id.ed_product_name);
        edPriceFrom = mView.findViewById(R.id.ed_price_from);
        edPriceTo = mView.findViewById(R.id.ed_price_to);
        btnSearch = mView.findViewById(R.id.btn_search);
        tvMessage = mView.findViewById(R.id.tv_message);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String productName = edProductName.getText().toString().trim();
                String strPriceFrom = edPriceFrom.getText().toString().trim();
                String strPriceTo = edPriceTo.getText().toString().trim();
                if(!strPriceFrom.isEmpty()){
                    priceFrom = Float.valueOf(strPriceFrom);
                }
                if(!strPriceFrom.isEmpty()){
                    priceTo = Float.valueOf(strPriceTo);
                }
                if(priceFrom >= 0 && priceTo > priceFrom){
                    ProductService.productService.searchProductWithPrice(productName,priceFrom,priceTo)
                            .enqueue(new Callback<List<Product>>() {
                                @Override
                                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                    progressDialog.dismiss();
                                    if(response.isSuccessful()){
                                        productList = response.body();
                                        productAdapter = new ProductAdapter(productList, mMainActivity, new ProductAdapter.IClickListener() {
                                            @Override
                                            public void onClickItemProduct(Product product) {
                                                if(mMainActivity.getAccountID() != null){
                                                    String authorization = "Bearer " + mMainActivity.getToken();
                                                    ViewService.viewService.save(mMainActivity.getAccountID(),product.getProductID(),authorization)
                                                            .enqueue(new Callback<Boolean>() {
                                                                @Override
                                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                                                }

                                                                @Override
                                                                public void onFailure(Call<Boolean> call, Throwable t) {
                                                                    Toast.makeText(mMainActivity, "Call Api Error", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                                mMainActivity.goToProductDetail(product);
                                            }
                                        });
                                        rcvProduct.setAdapter(productAdapter);
                                    }else{
                                        Toast.makeText(mMainActivity, "Response error", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Product>> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mMainActivity, "Call Api Error",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    ProductService.productService.searchProductWithoutPrice(productName)
                            .enqueue(new Callback<List<Product>>() {
                                @Override
                                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                    progressDialog.dismiss();
                                    if(response.isSuccessful()){
                                        productList = response.body();
                                        productAdapter = new ProductAdapter(productList, mMainActivity, new ProductAdapter.IClickListener() {
                                            @Override
                                            public void onClickItemProduct(Product product) {
                                                if(mMainActivity.getAccountID() != null){
                                                    String authorization = "Bearer " + mMainActivity.getToken();
                                                    ViewService.viewService.save(mMainActivity.getAccountID(),product.getProductID(),authorization)
                                                            .enqueue(new Callback<Boolean>() {
                                                                @Override
                                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                                                }

                                                                @Override
                                                                public void onFailure(Call<Boolean> call, Throwable t) {
                                                                    Toast.makeText(mMainActivity, "Call Api Error", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                                mMainActivity.goToProductDetail(product);
                                            }
                                        });
                                        rcvProduct.setAdapter(productAdapter);
                                    }else{
                                        Toast.makeText(mMainActivity, "Response error", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Product>> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mMainActivity, "Call Api Error",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                if (productList.size() != 0){
                    tvMessage.setVisibility(View.GONE);
                    rcvProduct.setVisibility(View.VISIBLE);
                }else{
                    rcvProduct.setVisibility(View.GONE);
                    tvMessage.setVisibility(View.VISIBLE);
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mMainActivity,2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        rcvProduct.setFocusable(false);
        rcvProduct.setNestedScrollingEnabled(false);
    }
}