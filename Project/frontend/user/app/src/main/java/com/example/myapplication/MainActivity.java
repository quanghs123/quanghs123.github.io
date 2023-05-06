package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.activity.SignInActivity;
import com.example.myapplication.adapter.MyViewPagerAdapter;
import com.example.myapplication.fragment.BrandProductFragment;
import com.example.myapplication.fragment.CartFragment;
import com.example.myapplication.fragment.ChangeFragment;
import com.example.myapplication.fragment.FavoriteProductFragment;
import com.example.myapplication.fragment.HistoryProductFragment;
import com.example.myapplication.fragment.Order1Fragment;
import com.example.myapplication.fragment.OrderFragment;
import com.example.myapplication.fragment.ProductDetailFragment;
import com.example.myapplication.fragment.UpdateAccFragment;
import com.example.myapplication.models.AuthenticationResponse;
import com.example.myapplication.models.Brand;
import com.example.myapplication.models.DTO.AccountDTO;
import com.example.myapplication.models.Product;
import com.example.myapplication.service.AccountService;
import com.example.myapplication.transformer.ZoomOutPageTransformer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private AccountDTO accountDTO;
    private String token;
    private Long accountID;

    private String authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
        viewPager();
        callUser();
    }

    private void viewPager() {
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_home) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.bottom_search) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.bottom_infor) {
                    viewPager2.setCurrentItem(2);
                }
                return true;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_search).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_infor).setChecked(true);
                        break;
                }
            }
        });
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
    }

    private void initUi() {
        viewPager2 = findViewById(R.id.view_pagger_2);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void callUser() {
        accountDTO = new AccountDTO();
        if (getIntent().getExtras() != null) {
            AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent()
                    .getExtras().get("data_intent");
            token = authenticationResponse.getToken();
            accountID = authenticationResponse.getId();
            AccountService.accountService.findById(authenticationResponse.getId())
                    .enqueue(new Callback<AccountDTO>() {
                        @Override
                        public void onResponse(Call<AccountDTO> call, Response<AccountDTO> response) {
                            if (response.isSuccessful())
                                accountDTO = response.body();
                        }

                        @Override
                        public void onFailure(Call<AccountDTO> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Call Api Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    public AccountDTO getAccountDTO() {
        return accountDTO;
    }
    public String getToken() {
        return token;
    }
    public Long getAccountID() {
        return accountID;
    }

    public String getAuthorization() {
        return "Bearer " + getToken();
    }

    public void goToEditProfileFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_profile,new UpdateAccFragment());
        fragmentTransaction.addToBackStack(UpdateAccFragment.TAG);
        fragmentTransaction.commit();
    }
    public void goToChangePasswordFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_profile,new ChangeFragment());
        fragmentTransaction.addToBackStack(ChangeFragment.TAG);
        fragmentTransaction.commit();
    }
    public void goToListProductOfBrand(Brand brand) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BrandProductFragment brandProductFragment = new BrandProductFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_brand",brand);
        brandProductFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_frame,brandProductFragment);
        fragmentTransaction.addToBackStack(BrandProductFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToProductDetail(Product product) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product",product);
        productDetailFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_frame,productDetailFragment);
        fragmentTransaction.addToBackStack(ProductDetailFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToFavoriteProductFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_profile,new FavoriteProductFragment());
        fragmentTransaction.addToBackStack(FavoriteProductFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToHistoryProductFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_profile,new HistoryProductFragment());
        fragmentTransaction.addToBackStack(HistoryProductFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToCartFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_profile,new CartFragment());
        fragmentTransaction.addToBackStack(CartFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToOrderFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_profile,new OrderFragment());
        fragmentTransaction.addToBackStack(OrderFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToOrder1Fragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_profile,new Order1Fragment());
        fragmentTransaction.addToBackStack(Order1Fragment.TAG);
        fragmentTransaction.commit();
    }
}