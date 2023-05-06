package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Order;
import com.example.myapplication.models.OrderDetail;
import com.example.myapplication.service.OrderDetailService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order1Adapter extends RecyclerView.Adapter<Order1Adapter.Viewholder> {
    private List<Order> mList;
    private Context mContext;
    private String authorization;

    public Order1Adapter(List<Order> mList, Context mContext, String authorization) {
        this.mList = mList;
        this.mContext = mContext;
        this.authorization = authorization;
    }

    @NonNull
    @Override
    public Order1Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order1,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Order1Adapter.Viewholder holder, int position) {
        Order order = mList.get(position);
        OrderDetailService.orderDetailService.getByOrderId(order.getOrderID(),authorization)
                .enqueue(new Callback<List<OrderDetail>>() {
                    @Override
                    public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                        if(response.isSuccessful()){
                            ProductOrderAdapter adapter = new ProductOrderAdapter(response.body(),mContext);
                            holder.rcv.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OrderDetail>> call, Throwable t) {
                        Toast.makeText(mContext, "Call Api Error", Toast.LENGTH_SHORT).show();
                    }
                });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        holder.rcv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private RecyclerView rcv;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            rcv = itemView.findViewById(R.id.rcvOrderDetail);
        }
    }
}
