package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Order;
import com.example.myapplication.models.OrderDetail;
import com.example.myapplication.service.OrderDetailService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Viewholder> {
    private List<Order> mList;
    private Context mContext;
    IClickListener mIClickListener;
    private String authorization;

    public interface IClickListener{
        void onClickBtnCancel(Order order);
    }

    public OrderAdapter(List<Order> mList, Context mContext, IClickListener mIClickListener, String authorization) {
        this.mList = mList;
        this.mContext = mContext;
        this.mIClickListener = mIClickListener;
        this.authorization = authorization;
    }

    @NonNull
    @Override
    public OrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.Viewholder holder, int position) {
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
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickBtnCancel(order);
            }
        });
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
        private RelativeLayout btnCancel;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            rcv = itemView.findViewById(R.id.rcvOrderDetail);
            btnCancel = itemView.findViewById(R.id.btnCacel);
        }
    }
}
