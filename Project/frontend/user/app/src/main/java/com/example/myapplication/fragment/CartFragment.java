package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.models.Cart;
import com.example.myapplication.models.DTO.OrderDetailDTO;
import com.example.myapplication.models.Order;
import com.example.myapplication.models.OrderDetail;
import com.example.myapplication.service.CartService;
import com.example.myapplication.service.OrderDetailService;
import com.example.myapplication.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    public static final String TAG = CartFragment.class.getName();
    private View mView;
    private MainActivity mMainActivity;
    private ProgressDialog progressDialog;
    private ImageView btnBack;
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;
    private List<Cart> cartList;
    private LinearLayout btnOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_cart, container, false);
        initUi();
        callCart();
        return mView;
    }
    private void callCart() {
        progressDialog.show();
        cartList = new ArrayList<>();
        CartService.cartService.getAll(mMainActivity.getAccountID(),mMainActivity.getAuthorization())
                .enqueue(new Callback<List<Cart>>() {
                    @Override
                    public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()){
                            cartList = response.body();
                            cartAdapter = new CartAdapter(cartList, mMainActivity, new CartAdapter.IClickListener() {
                                @Override
                                public void onClickDeleteCartItem(Cart cart) {
                                    CartService.cartService.delete(cart.getCartID(), mMainActivity.getAuthorization())
                                            .enqueue(new Callback<Boolean>() {
                                                @Override
                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                    if (response.isSuccessful()){
                                                        if(Boolean.TRUE.equals(response.body())){
                                                            cartList.remove(cart);
                                                            rcvCart.setAdapter(cartAdapter);
                                                        }
                                                    }else {
                                                        Toast.makeText(mMainActivity, "Response error", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Boolean> call, Throwable t) {
                                                    Toast.makeText(mMainActivity, "Call Api Error", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                            rcvCart.setAdapter(cartAdapter);
                        }else{
                            Toast.makeText(mMainActivity, "Response error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Cart>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(mMainActivity, "Call Api Error", Toast.LENGTH_SHORT).show();
                    }
                });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity,RecyclerView.VERTICAL,false);
        rcvCart.setLayoutManager(linearLayoutManager);
    }

    private void initUi() {
        mMainActivity = (MainActivity) getActivity();
        progressDialog = new ProgressDialog(mMainActivity);
        btnBack = mView.findViewById(R.id.btn_back);
        rcvCart = mView.findViewById(R.id.rcvCart);
        btnOrder = mView.findViewById(R.id.btnAddToOrder);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderListProduct();
            }
        });
    }

    private void orderListProduct() {
        progressDialog.show();
        OrderService.orderService.save(mMainActivity.getAccountDTO(),mMainActivity.getAuthorization())
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            createOrderDetail(response.body().getOrderID());
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(mMainActivity, "Call Api Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createOrderDetail(Long orderID) {
        for(Cart cart : cartList){
            progressDialog.show();
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderID,cart.getCartID().getProductID(),cart.getProductQuantity());
            OrderDetailService.orderDetailService.save(orderDetailDTO,mMainActivity.getAuthorization())
                    .enqueue(new Callback<OrderDetail>() {
                        @Override
                        public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<OrderDetail> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(mMainActivity, "Call Api Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        cartList.clear();
        rcvCart.setAdapter(cartAdapter);
        clearCart();
    }

    private void clearCart() {
        progressDialog.show();
        CartService.cartService.deleteByAccountId(mMainActivity.getAccountID(),mMainActivity.getAuthorization())
                .enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(mMainActivity, "Call Api Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}