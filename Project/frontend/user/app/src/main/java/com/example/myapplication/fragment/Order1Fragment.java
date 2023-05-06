package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.Order1Adapter;
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.models.Order;
import com.example.myapplication.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order1Fragment extends Fragment {
    public static final String TAG = Order1Fragment.class.getName();
    private View mView;
    private RecyclerView rcvOrder;
    private MainActivity mMainActivity;
    private ProgressDialog progressDialog;
    private ImageView btnBack;
    private Order1Adapter adapter;
    private List<Order> orders;
    private TextView tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_order, container, false);
        initUi();
        callList();
        return mView;
    }

    private void callList() {
        progressDialog.show();
        orders = new ArrayList<>();
        OrderService.orderService.getByAccountId1(mMainActivity.getAccountID(),mMainActivity.getAuthorization())
                .enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()){
                            orders = response.body();
                            adapter = new Order1Adapter(orders, mMainActivity,mMainActivity.getAuthorization());
                            rcvOrder.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(mMainActivity, "Call Api Error", Toast.LENGTH_SHORT).show();
                    }
                });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity,RecyclerView.VERTICAL,false);
        rcvOrder.setLayoutManager(linearLayoutManager);
    }

    private void initUi() {
        mMainActivity = (MainActivity) getActivity();
        progressDialog = new ProgressDialog(mMainActivity);
        btnBack = mView.findViewById(R.id.btn_back);
        rcvOrder = mView.findViewById(R.id.rcvOrder);
        tvTitle = mView.findViewById(R.id.tvTitle);
        tvTitle.setText("Delivering");
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