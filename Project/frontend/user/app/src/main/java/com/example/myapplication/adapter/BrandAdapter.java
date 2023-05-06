package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Brand;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder>{
    private List<Brand> mListBrand;
    private Context mContext;
    IClickListener mIClickListener;

    public interface IClickListener{
        void onClickItemBrand(Brand brand);
    }

    public BrandAdapter(List<Brand> mListBrand, Context mContext, IClickListener mIClickListener) {
        this.mListBrand = mListBrand;
        this.mContext = mContext;
        this.mIClickListener = mIClickListener;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand,parent,false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = mListBrand.get(position);
        if(brand == null){
            return;
        }
        String linkImage = "http://192.168.0.104:8080/getimage/brand/"+brand.getBrandImage();
        Glide.with(this.mContext).load(linkImage).into(holder.imgBrand);
        holder.tvBrandName.setText(brand.getBrandName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickItemBrand(brand);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListBrand != null){
            return mListBrand.size();
        }
        return 0;
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBrand;
        private TextView tvBrandName;
        private LinearLayout linearLayout;
        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBrand = itemView.findViewById(R.id.brand_image);
            tvBrandName = itemView.findViewById(R.id.tv_brand_name);
            linearLayout = itemView.findViewById(R.id.linearlayout);
        }
    }
}
