package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.BrandAdapter;
import com.example.myapplication.adapter.PhotoViewpager2Adapter;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.models.Brand;
import com.example.myapplication.models.Photo;
import com.example.myapplication.models.Product;
import com.example.myapplication.service.BrandService;
import com.example.myapplication.service.ProductService;
import com.example.myapplication.service.ViewService;
import com.example.myapplication.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private View mView;
    private RecyclerView rcvProduct,rcvBrand;
    private ProductAdapter productAdapter;
    private BrandAdapter brandAdapter;
    private MainActivity mMainActivity;
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private List<Photo> mListPhoto;
    private ProgressDialog progressDialog;
    private List<Product> productList;
    private List<Brand> brandList;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager2.getCurrentItem() == mListPhoto.size()-1){
                mViewPager2.setCurrentItem(0);
            }else{
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem()+1);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        initUi();
        callProduct();
        callBrand();
        mListPhoto = getListPhoto();
        PhotoViewpager2Adapter adapter = new PhotoViewpager2Adapter(mListPhoto);
        mViewPager2.setAdapter(adapter);
        mCircleIndicator3.setViewPager(mViewPager2);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,2000);
            }
        });
        mViewPager2.setPageTransformer(new DepthPageTransformer());

        return mView;
    }

    private void callBrand() {
        progressDialog.show();
        brandList = new ArrayList<>();
        BrandService.brandService.findAll()
                .enqueue(new Callback<List<Brand>>() {
                    @Override
                    public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()){
                            brandList = response.body();
                            brandAdapter = new BrandAdapter(brandList, mMainActivity, new BrandAdapter.IClickListener() {
                                @Override
                                public void onClickItemBrand(Brand brand) {
                                    mMainActivity.goToListProductOfBrand(brand);
                                }
                            });
                            rcvBrand.setAdapter(brandAdapter);
                        }else{
                            Toast.makeText(mMainActivity, "Response error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Brand>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(mMainActivity, "Call Api Error", Toast.LENGTH_SHORT).show();
                    }
                });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mMainActivity,3);
        rcvBrand.setLayoutManager(gridLayoutManager);
        rcvBrand.setFocusable(false);
        rcvBrand.setNestedScrollingEnabled(false);
    }

    private void callProduct() {
        progressDialog.show();
        productList = new ArrayList<>();
        ProductService.productService.findAll()
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
        mViewPager2 = mView.findViewById(R.id.view_pagger_2);
        mCircleIndicator3 = mView.findViewById(R.id.circle_indicator_3);
        progressDialog = new ProgressDialog(mMainActivity);

        rcvProduct = mView.findViewById(R.id.rcv_product);
        rcvBrand = mView.findViewById(R.id.rcv_brand);
    }
    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();

        list.add(new Photo(R.drawable.anh1));
        list.add(new Photo(R.drawable.anh2));
        list.add(new Photo(R.drawable.anh3));
        list.add(new Photo(R.drawable.anh4));

        return list;
    }
}