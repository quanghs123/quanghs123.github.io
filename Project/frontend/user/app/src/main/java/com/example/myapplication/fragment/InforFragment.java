package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.activity.SignInActivity;
import com.example.myapplication.activity.SignUpActivity;
import com.example.myapplication.models.DTO.AccountDTO;

public class InforFragment extends Fragment {
    public static final String TAG = InforFragment.class.getName();
    private TextView tvFullName;
    private LinearLayout btnSignOut,layoutBtn,layoutInfor,btnSignIn,btnSignUp,btnEditProfile
            ,btnChangePassword,layoutFavorite,layoutHistory,layoutCart,btnUnconfirmed,btnDelivering;
    private View mView;
    private MainActivity mMainActivity;
    private AccountDTO accountDTO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_infor, container, false);
        initUi();
        initListener();
        return mView;
    }

    private void initListener() {
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignOut();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEditProfile();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChangePassword();
            }
        });
        layoutFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFavorite();
            }
        });
        layoutHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHistory();
            }
        });
        layoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCart();
            }
        });
        btnUnconfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUnconfirmed();
            }
        });
        btnDelivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelivering();
            }
        });
    }

    private void onClickDelivering() {
        if(mMainActivity.getAccountID() != null){
            mMainActivity.goToOrder1Fragment();
        }else{
            Toast.makeText(mMainActivity, "Your must Login",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickUnconfirmed() {
        if(mMainActivity.getAccountID() != null){
            mMainActivity.goToOrderFragment();
        }else{
            Toast.makeText(mMainActivity, "Your must Login",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickCart() {
        if(mMainActivity.getAccountID() != null){
            mMainActivity.goToCartFragment();
        }else{
            Toast.makeText(mMainActivity, "Your must Login",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickHistory() {
        if(mMainActivity.getAccountID() != null){
            mMainActivity.goToHistoryProductFragment();
        }else{
            Toast.makeText(mMainActivity, "Your must Login",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickFavorite() {
        if(mMainActivity.getAccountID() != null){
            mMainActivity.goToFavoriteProductFragment();
        }else{
            Toast.makeText(mMainActivity, "Your must Login",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickChangePassword() {
        if(mMainActivity.getAccountID() != null){
            mMainActivity.goToChangePasswordFragment();
        }else{
            Toast.makeText(mMainActivity, "Your must Login",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickEditProfile() {
        mMainActivity.goToEditProfileFragment();
    }

    private void onClickSignUp() {
        Intent intent = new Intent(mMainActivity, SignUpActivity.class);
        startActivity(intent);
    }

    private void onClickSignIn() {
        Intent intent = new Intent(mMainActivity, SignInActivity.class);
        startActivity(intent);
    }

    private void onClickSignOut() {
        Intent intent = new Intent(mMainActivity, SignInActivity.class);
        startActivity(intent);
        mMainActivity.finishAffinity();
    }

    private void initUi() {
        tvFullName = mView.findViewById(R.id.tv_full_name);
        btnSignOut = mView.findViewById(R.id.btn_sign_out);
        btnChangePassword = mView.findViewById(R.id.btn_change_password);
        layoutBtn = mView.findViewById(R.id.layout_btn);
        layoutInfor = mView.findViewById(R.id.layout_infor);
        btnSignIn = mView.findViewById(R.id.btn_sign_in);
        btnSignUp = mView.findViewById(R.id.btn_sign_up);
        btnEditProfile = mView.findViewById(R.id.btn_edit_profile);
        layoutFavorite = mView.findViewById(R.id.layout_favorite);
        layoutHistory = mView.findViewById(R.id.layout_history);
        layoutCart = mView.findViewById(R.id.layout_cart);
        btnUnconfirmed = mView.findViewById(R.id.btnUnconfirmed);
        btnDelivering = mView.findViewById(R.id.btnDelivering);
        mMainActivity = (MainActivity) getActivity();
        if(mMainActivity != null){
            accountDTO = mMainActivity.getAccountDTO();
        }
        if(accountDTO.getAccountID() != null){
            layoutInfor.setVisibility(View.VISIBLE);
            layoutBtn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
        }
        tvFullName.setText(accountDTO.getFullName());
    }
}