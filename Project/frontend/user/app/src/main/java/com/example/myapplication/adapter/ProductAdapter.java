package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Product;
import com.example.myapplication.service.FavoriteService;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> mListProduct;
    private Context mContext;
    IClickListener mIClickListener;

    public interface IClickListener{
        void onClickItemProduct(Product product);
    }

    public ProductAdapter(List<Product> mListProduct, Context mContext, IClickListener mIClickListener) {
        this.mListProduct = mListProduct;
        this.mContext = mContext;
        this.mIClickListener = mIClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if(product == null){
            return;
        }
        String linkImage = "http://192.168.0.104:8080/getimage/product/"+product.getProductImage();
        Glide.with(this.mContext).load(linkImage).into(holder.imgProduct);
        holder.tvProductName.setText(product.getProductName());
        Locale locale = new Locale("vi","VN");
        NumberFormat currentVN = NumberFormat.getCurrencyInstance(locale);
        String price = currentVN.format(product.getPrice());
        holder.tvProductPrice.setText(price);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickItemProduct(product);
            }
        });
        FavoriteService.favoriteService.getFavorite(product.getProductID())
                .enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if(response.isSuccessful()){
                            holder.tvLikes.setText(String.valueOf(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Toast.makeText(mContext, "Call Api Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (mListProduct != null){
            return mListProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView tvProductName,tvProductPrice,tvLikes;
        private LinearLayout linearLayout;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            linearLayout = itemView.findViewById(R.id.linearlayout);
            tvLikes = itemView.findViewById(R.id.tv_likes);
        }
    }
}
