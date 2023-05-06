package com.example.myapplication.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.DTO.CartDTO;
import com.example.myapplication.models.DTO.OrderDetailDTO;
import com.example.myapplication.models.Order;
import com.example.myapplication.models.OrderDetail;
import com.example.myapplication.models.OrderDetailKey;
import com.example.myapplication.models.Product;
import com.example.myapplication.service.CartService;
import com.example.myapplication.service.FavoriteService;
import com.example.myapplication.service.OrderDetailService;
import com.example.myapplication.service.OrderService;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailFragment extends Fragment {
    public static final String TAG = ProductDetailFragment.class.getName();
    private View mView;
    private MainActivity mMainActivity;
    private ImageView imgProductImage, btnBack, icFavorite, icFavorite1;
    private TextView tvProduct, tvProductName, tvPrice, tvSellNumber, tvLikes, tvRam, tvScreenSize, tvPin, tvOperatingSystem;
    private Product product;
    private RelativeLayout btnLike, btnUnlike;
    private LinearLayout btnAddToCart,btnOrder;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_product_detail, container, false);
        initUi();
        Bundle bundle = getArguments();
        if (bundle != null) {
            product = (Product) bundle.get("object_product");
            String linkImage = "http://192.168.0.104:8080/getimage/product/" + product.getProductImage();
            Glide.with(mMainActivity).load(linkImage).into(imgProductImage);
            tvProductName.setText(product.getProductName());
            tvProduct.setText(product.getProductName());
            Locale locale = new Locale("vi", "VN");
            NumberFormat currentVN = NumberFormat.getCurrencyInstance(locale);
            String price = currentVN.format(product.getPrice());
            tvPrice.setText(price);
            tvRam.setText(String.valueOf(product.getRam()));
            tvScreenSize.setText(String.valueOf(product.getScreenSize()));
            tvPin.setText(String.valueOf(product.getPin()));
            tvOperatingSystem.setText(product.getOperatingSystem());
            FavoriteService.favoriteService.getFavorite(product.getProductID())
                    .enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            if (response.isSuccessful()) {
                                tvLikes.setText(String.valueOf(response.body()));
                            }
                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable t) {
                            Toast.makeText(mMainActivity, "Call Api Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            if (mMainActivity.getAccountID() == null) {
                icFavorite.setVisibility(View.GONE);
                icFavorite1.setVisibility(View.VISIBLE);
                btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mMainActivity, "Your must Login",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                FavoriteService.favoriteService.getFavoriteById(mMainActivity.getAccountID(), product.getProductID(), mMainActivity.getAuthorization())
                        .enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if (response.isSuccessful()) {
                                    if (Boolean.TRUE.equals(response.body())) {
                                        icFavorite.setVisibility(View.VISIBLE);
                                        icFavorite1.setVisibility(View.GONE);
                                        btnLike.setVisibility(View.GONE);
                                        btnUnlike.setVisibility(View.VISIBLE);
                                    } else {
                                        icFavorite.setVisibility(View.GONE);
                                        icFavorite1.setVisibility(View.VISIBLE);
                                        btnLike.setVisibility(View.VISIBLE);
                                        btnUnlike.setVisibility(View.GONE);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(mMainActivity, "Call Api Error",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FavoriteService.favoriteService.save(mMainActivity.getAccountID(), product.getProductID(), mMainActivity.getAuthorization())
                                .enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful()) {
                                            if (Boolean.TRUE.equals(response.body())) {
                                                Toast.makeText(mMainActivity, "Add product to your favorite",
                                                        Toast.LENGTH_SHORT).show();
                                                icFavorite.setVisibility(View.VISIBLE);
                                                icFavorite1.setVisibility(View.GONE);
                                                btnLike.setVisibility(View.GONE);
                                                btnUnlike.setVisibility(View.VISIBLE);
                                                tvLikes.setText(String.valueOf(Integer.parseInt(tvLikes.getText().toString().trim())+1));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Toast.makeText(mMainActivity, "Call Api Error",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                btnUnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FavoriteService.favoriteService.delete(mMainActivity.getAccountID(), product.getProductID(),mMainActivity.getAuthorization())
                                .enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful()) {
                                            if (Boolean.TRUE.equals(response.body())) {
                                                Toast.makeText(mMainActivity, "Remove product from your favorite",
                                                        Toast.LENGTH_SHORT).show();
                                                icFavorite.setVisibility(View.GONE);
                                                icFavorite1.setVisibility(View.VISIBLE);
                                                btnLike.setVisibility(View.VISIBLE);
                                                btnUnlike.setVisibility(View.GONE);
                                                tvLikes.setText(String.valueOf(Integer.parseInt(tvLikes.getText().toString().trim())-1));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Toast.makeText(mMainActivity, "Call Api Error",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        }
        initListener();
        return mView;
    }

    private void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMainActivity.getAccountID() != null) {
                    openAddToCartDialog();
                } else {
                    Toast.makeText(mMainActivity, "Your must Login",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMainActivity.getAccountID() != null) {
                    openAddToOrderDialog();
                } else {
                    Toast.makeText(mMainActivity, "Your must Login",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openAddToOrderDialog() {
        final Dialog dialog = new Dialog(mMainActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_cart);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowArtributes = window.getAttributes();
        windowArtributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowArtributes);

        dialog.setCancelable(true);

        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        ImageView imgProduct = dialog.findViewById(R.id.imgProduct);
        TextView tvProductPrice = dialog.findViewById(R.id.tv_product_price);
        TextView btnReduce = dialog.findViewById(R.id.btnReduce);
        TextView btnIncrease = dialog.findViewById(R.id.btnIncrease);
        EditText edQuantity = dialog.findViewById(R.id.edQuantity);
        TextView tvProductQuantity = dialog.findViewById(R.id.tvProductQuantity);
        RelativeLayout btnAddToCart = dialog.findViewById(R.id.btnAddToCart);
        TextView tvBtnText = dialog.findViewById(R.id.tvBtnText);

        String linkImage = "http://192.168.0.104:8080/getimage/product/" + product.getProductImage();
        Glide.with(mMainActivity).load(linkImage).into(imgProduct);
        Locale locale = new Locale("vi", "VN");
        NumberFormat currentVN = NumberFormat.getCurrencyInstance(locale);
        String price = currentVN.format(product.getPrice());
        tvProductPrice.setText(price);
        tvProductQuantity.setText(String.valueOf(product.getProductQuantity()));
        tvBtnText.setText("Order");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int quantity = Integer.parseInt(edQuantity.getText().toString().trim());
                onClickOrder(quantity);
            }
        });
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(edQuantity.getText().toString().trim());
                if(quantity < product.getProductQuantity()){
                    edQuantity.setText(String.valueOf(quantity+1));
                }
            }
        });
        btnReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(edQuantity.getText().toString().trim());
                if(quantity > 1){
                    edQuantity.setText(String.valueOf(quantity-1));
                }
            }
        });
        dialog.show();
    }

    private void onClickOrder(int quantity) {
        progressDialog.show();
        OrderService.orderService.save(mMainActivity.getAccountDTO(),mMainActivity.getAuthorization())
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            createOrderDetail(response.body().getOrderID(),quantity);
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

    private void createOrderDetail(Long orderID,int quantity) {
        progressDialog.show();
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderID, product.getProductID(), quantity);
        OrderDetailService.orderDetailService.save(orderDetailDTO,mMainActivity.getAuthorization())
                .enqueue(new Callback<OrderDetail>() {
                    @Override
                    public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()){
                            Toast.makeText(mMainActivity, "Order Successful",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderDetail> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(mMainActivity, "Call Api Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void openAddToCartDialog() {
        final Dialog dialog = new Dialog(mMainActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_cart);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowArtributes = window.getAttributes();
        windowArtributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowArtributes);

        dialog.setCancelable(true);

        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        ImageView imgProduct = dialog.findViewById(R.id.imgProduct);
        TextView tvProductPrice = dialog.findViewById(R.id.tv_product_price);
        TextView btnReduce = dialog.findViewById(R.id.btnReduce);
        TextView btnIncrease = dialog.findViewById(R.id.btnIncrease);
        EditText edQuantity = dialog.findViewById(R.id.edQuantity);
        TextView tvProductQuantity = dialog.findViewById(R.id.tvProductQuantity);
        RelativeLayout btnAddToCart = dialog.findViewById(R.id.btnAddToCart);

        String linkImage = "http://192.168.0.104:8080/getimage/product/" + product.getProductImage();
        Glide.with(mMainActivity).load(linkImage).into(imgProduct);
        Locale locale = new Locale("vi", "VN");
        NumberFormat currentVN = NumberFormat.getCurrencyInstance(locale);
        String price = currentVN.format(product.getPrice());
        tvProductPrice.setText(price);
        tvProductQuantity.setText(String.valueOf(product.getProductQuantity()));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int quantity = Integer.parseInt(edQuantity.getText().toString().trim());
                onClickAddToCart(quantity);
            }
        });
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(edQuantity.getText().toString().trim());
                if(quantity < product.getProductQuantity()){
                    edQuantity.setText(String.valueOf(quantity+1));
                }
            }
        });
        btnReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(edQuantity.getText().toString().trim());
                if(quantity > 1){
                    edQuantity.setText(String.valueOf(quantity-1));
                }
            }
        });
        dialog.show();
    }

    private void onClickAddToCart(int quantity) {
        progressDialog.show();
        CartDTO cartDTO = new CartDTO(mMainActivity.getAccountID(), product.getProductID(), quantity);
        CartService.cartService.save(cartDTO, mMainActivity.getAuthorization())
                .enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            if (Boolean.TRUE.equals(response.body())) {
                                Toast.makeText(mMainActivity, "Add To Cart successful!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(mMainActivity, "Call Api Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initUi() {
        mMainActivity = (MainActivity) getActivity();
        imgProductImage = mView.findViewById(R.id.img_product);
        btnBack = mView.findViewById(R.id.btn_back);
        tvProduct = mView.findViewById(R.id.tv_product);
        tvProductName = mView.findViewById(R.id.tv_product_name);
        tvPrice = mView.findViewById(R.id.tv_product_price);
        tvSellNumber = mView.findViewById(R.id.tv_sell_number);
        tvLikes = mView.findViewById(R.id.tv_likes);
        tvRam = mView.findViewById(R.id.tv_ram);
        tvScreenSize = mView.findViewById(R.id.tv_screen_size);
        tvPin = mView.findViewById(R.id.tv_pin);
        tvOperatingSystem = mView.findViewById(R.id.tv_operating_system);
        icFavorite = mView.findViewById(R.id.ic_favorite);
        icFavorite1 = mView.findViewById(R.id.ic_favorite1);
        btnLike = mView.findViewById(R.id.btnLike);
        btnUnlike = mView.findViewById(R.id.btnUnLike);
        btnAddToCart = mView.findViewById(R.id.btnAddToCart);
        progressDialog = new ProgressDialog(mMainActivity);
        btnOrder = mView.findViewById(R.id.btnAddToOrder);
    }
}