package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.DTO.AccountDTO;
import com.example.myapplication.service.AccountService;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeFragment extends Fragment {
    public static final String TAG = ChangeFragment.class.getName();
    private View mView;
    private MainActivity mMainActivity;
    private ImageView btnBack, btnUpdate;
    private EditText edPassword, edNewPassword, edConfirmPassword;
    private TextView tvPassword, tvNewPassword, tvConfirmPassword;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_change, container, false);
        initUi();
        initListener();
        return mView;
    }

    private void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBack();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdate();
            }
        });
    }

    private void initUi() {
        btnBack = mView.findViewById(R.id.btn_back);
        btnUpdate = mView.findViewById(R.id.btn_update);
        edPassword = mView.findViewById(R.id.ed_password);
        edNewPassword = mView.findViewById(R.id.ed_new_password);
        edConfirmPassword = mView.findViewById(R.id.ed_confirm_password);
        tvPassword = mView.findViewById(R.id.tv_validate_password);
        tvNewPassword = mView.findViewById(R.id.tv_validate_new_password);
        tvConfirmPassword = mView.findViewById(R.id.tv_validate_confirm_password);
        mMainActivity = (MainActivity) getActivity();
        progressDialog = new ProgressDialog(mMainActivity);
    }

    private void onClickBack() {
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        }
    }

    private void onClickUpdate() {
        String strPassword = edPassword.getText().toString().trim();
        String strNewPassword = edNewPassword.getText().toString().trim();
        String strConfirmPassword = edConfirmPassword.getText().toString().trim();

        Pattern patternPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

        if (strPassword.length() == 0) {
            tvPassword.setVisibility(View.VISIBLE);
            tvPassword.setText("Password can not be blank!");
            tvPassword.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else if (!patternPassword.matcher(strNewPassword).find()) {
            tvNewPassword.setVisibility(View.VISIBLE);
            tvNewPassword.setText("Invalid Password!");
            tvNewPassword.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else if (strNewPassword.compareTo(strConfirmPassword) != 0) {
            tvConfirmPassword.setVisibility(View.VISIBLE);
            tvConfirmPassword.setText("Password are not matching!");
            tvConfirmPassword.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else {
            progressDialog.show();
            AccountService.accountService.changePassword(mMainActivity.getAccountID(), strPassword
                    , strNewPassword,mMainActivity.getAuthorization()).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        if(Boolean.TRUE.equals(response.body())){
                            onClickBack();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(mMainActivity, "Password Incorrect",
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
    }
}