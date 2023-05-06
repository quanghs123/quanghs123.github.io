package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Cart;
import com.example.myapplication.models.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    private List<Cart> list;
    private Context mContext;
    IClickListener mIClickListener;

    public interface IClickListener {
        void onClickDeleteCartItem(Cart cart);
    }

    @SuppressLint("NotifyDataSetChanged")
    public CartAdapter(List<Cart> list, Context mContext, IClickListener mIClickListener) {
        this.list = list;
        this.mContext = mContext;
        this.mIClickListener = mIClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholder holder, int position) {
        Cart cart = list.get(position);
        if (cart == null) {
            return;
        }
        String linkImage = "http://192.168.0.104:8080/getimage/product/" + cart.getProduct().getProductImage();
        Glide.with(this.mContext).load(linkImage).into(holder.imgProduct);
        holder.tvProductName.setText(cart.getProduct().getProductName());
        Locale locale = new Locale("vi", "VN");
        NumberFormat currentVN = NumberFormat.getCurrencyInstance(locale);
        String price = currentVN.format(cart.getProduct().getPrice());
        holder.tvProductPrice.setText(price);
        holder.edQuantity.setText(String.valueOf(cart.getProductQuantity()));
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickDeleteCartItem(cart);
            }
        });
        holder.btnReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.edQuantity.getText().toString().trim());
                if (quantity > 1) {
                    holder.edQuantity.setText(String.valueOf(quantity - 1));
                }
            }
        });
        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.edQuantity.getText().toString().trim());
                if (quantity < cart.getProduct().getProductQuantity()) {
                    holder.edQuantity.setText(String.valueOf(quantity + 1));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView imgProduct, btnDelete;
        private TextView tvProductName, tvProductPrice, btnReduce, btnIncrease;
        private EditText edQuantity;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            edQuantity = itemView.findViewById(R.id.edQuantity);
            btnReduce = itemView.findViewById(R.id.btnReduce);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
