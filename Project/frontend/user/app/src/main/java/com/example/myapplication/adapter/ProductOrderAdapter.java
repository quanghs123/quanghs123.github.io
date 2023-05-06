package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.OrderDetail;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.Viewholder> {
    private List<OrderDetail> mListOrderDetails;
    private Context mContext;

    public ProductOrderAdapter(List<OrderDetail> mListOrderDetails, Context mContext) {
        this.mListOrderDetails = mListOrderDetails;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProductOrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_order,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderAdapter.Viewholder holder, int position) {
        OrderDetail orderDetail = mListOrderDetails.get(position);
        if (orderDetail == null){
            return;
        }
        String linkImage = "http://192.168.0.104:8080/getimage/product/"+orderDetail.getProduct().getProductImage();
        Glide.with(this.mContext).load(linkImage).into(holder.imgProduct);
        holder.tvProductName.setText(orderDetail.getProduct().getProductName());
        Locale locale = new Locale("vi","VN");
        NumberFormat currentVN = NumberFormat.getCurrencyInstance(locale);
        String price = currentVN.format(orderDetail.getProduct().getPrice());
        holder.tvProductPrice.setText(price);
        holder.tvProductQuantity.setText(String.valueOf(orderDetail.getOrderQuantity()));
    }

    @Override
    public int getItemCount() {
        if(mListOrderDetails != null){
            return mListOrderDetails.size();
        }
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvProductName,tvProductPrice,tvProductQuantity;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }
}
