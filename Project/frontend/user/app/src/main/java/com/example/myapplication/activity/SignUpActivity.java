package com.example.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.models.Account;
import com.example.myapplication.models.RegisterResponse;
import com.example.myapplication.service.AccountService;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText edFullName, edEmail, edPhone, edUserName, edPassword, edConfirmPassword;
    TextView tvFullName, tvEmail, tvPhone, tvUserName, tvPassword, tvConfirmPassword;
    Button btnSignUp;
    private ProgressDialog progressDialog;
    LinearLayout layoutSiginIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUi();
        initListener();
    }

    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });

        layoutSiginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    private void onClickSignUp() {
        String strFullName = edFullName.getText().toString().trim();
        String strEmail = edEmail.getText().toString().trim();
        String strPhone = edPhone.getText().toString().trim();
        String strUserName = edUserName.getText().toString().trim();
        String strPassword = edPassword.getText().toString().trim();
        String strConfirmPassword = edConfirmPassword.getText().toString().trim();

        Pattern patternFullName = Pattern.compile("^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\W|_]+$");
        Pattern patternEmail = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Pattern patternPhone = Pattern.compile("^[0-9\\-\\+]{9,15}$");
        Pattern patternUserName = Pattern.compile("^[a-zA-Z0-9]{3,}$");
        Pattern patternPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");


        if (!patternFullName.matcher(strFullName).find()) {
            tvFullName.setVisibility(View.VISIBLE);
            tvFullName.setText("Invalid full name!");
            tvFullName.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else if (!patternEmail.matcher(strEmail).find()) {
            tvEmail.setVisibility(View.VISIBLE);
            tvEmail.setText("Invalid email!");
            tvEmail.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else if (!patternPhone.matcher(strPhone).find()) {
            tvPhone.setVisibility(View.VISIBLE);
            tvPhone.setText("Invalid phnoe number!");
            tvPhone.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else if (!patternUserName.matcher(strUserName).find()) {
            tvUserName.setVisibility(View.VISIBLE);
            tvUserName.setText("User name must be between 3 and 25 characters!");
            tvUserName.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else if (!patternPassword.matcher(strPassword).find()) {
            tvPassword.setVisibility(View.VISIBLE);
            tvPassword.setText("Minimum eight characters, at least one letter and one number!");
            tvPassword.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else if (strPassword.compareTo(strConfirmPassword) != 0) {
            tvConfirmPassword.setVisibility(View.VISIBLE);
            tvConfirmPassword.setText("Password are not matching!");
            tvConfirmPassword.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else {
            progressDialog.show();
            Account account = new Account(strUserName, strPassword, strFullName, strEmail, strPhone, "USER");

            AccountService.accountService.register(account)
                    .enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()){
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }else{
                                tvUserName.setVisibility(View.VISIBLE);
                                tvUserName.setText("User name already exists!");
                                tvUserName.setTextColor(getResources().getColor(R.color.design_default_color_error));
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void initUi() {
        edFullName = findViewById(R.id.ed_full_name);
        edEmail = findViewById(R.id.ed_email);
        edPhone = findViewById(R.id.ed_phone);
        edUserName = findViewById(R.id.ed_user_name);
        edPassword = findViewById(R.id.ed_password);
        edConfirmPassword = findViewById(R.id.ed_confirm_password);

        tvFullName = findViewById(R.id.tv_validate_full_name);
        tvEmail = findViewById(R.id.tv_validate_email);
        tvPhone = findViewById(R.id.tv_validate_phone);
        tvUserName = findViewById(R.id.tv_validate_user_name);
        tvPassword = findViewById(R.id.tv_validate_password);
        tvConfirmPassword = findViewById(R.id.tv_validate_confirm_password);

        btnSignUp = findViewById(R.id.btn_sign_up);
        layoutSiginIn = findViewById(R.id.layout_sign_in);

        progressDialog = new ProgressDialog(this);
    }
}