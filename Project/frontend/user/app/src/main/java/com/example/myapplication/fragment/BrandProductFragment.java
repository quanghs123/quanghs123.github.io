package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.models.Brand;
import com.example.myapplication.models.Product;
import com.example.myapplication.service.ProductService;
import com.example.myapplication.service.ViewService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandProductFragment extends Fragment {
    public static final String TAG = BrandProductFragment.class.getName();
    private View mView;
    private MainActivity mMainActivity;
    private TextView tvBrandName;
    private ImageView btnBack;
    private RecyclerView rcvProduct;
    private ProgressDialog progressDialog;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private Brand brand;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_brand_product, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            brand = (Brand) bundle.get("object_brand");
        }
        initUi();
        callProduct();
        return mView;
    }

    private void callProduct() {
        progressDialog.show();
        productList = new ArrayList<>();
        ProductService.productService.findByBrandID(brand.getBrandID())
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
                        Toast.makeText(mMainActivity, "Call Api Error", Toast.LENGTH_SHORT).show();
                    }
                });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mMainActivity,2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        rcvProduct.setFocusable(false);
        rcvProduct.setNestedScrollingEnabled(false);
    }

    private void initUi() {
        mMainActivity = (MainActivity) getActivity();
        tvBrandName = mView.findViewById(R.id.tv_brand_name);
        rcvProduct = mView.findViewById(R.id.rcv_product);
        progressDialog = new ProgressDialog(mMainActivity);
        tvBrandName.setText(brand.getBrandName());
        btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });
    }
}