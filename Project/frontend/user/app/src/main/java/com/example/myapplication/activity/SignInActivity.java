package com.example.myapplication.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.AuthenticationRequest;
import com.example.myapplication.models.AuthenticationResponse;
import com.example.myapplication.models.DTO.AccountDTO;
import com.example.myapplication.service.AccountService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private LinearLayout layoutSignUp, layoutForgotPassword;
    private EditText edUserName, edPassword;
    private TextView tvUserName, tvPassword;
    private Button btnSignIn;
    private ProgressDialog progressDialog;
    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initUi();
        initListener();
    }

    private void initUi() {
        layoutSignUp = findViewById(R.id.layout_sign_up);
        layoutForgotPassword = findViewById(R.id.layout_forgot_password);
        edUserName = findViewById(R.id.ed_user_name);
        edPassword = findViewById(R.id.ed_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        progressDialog = new ProgressDialog(this);

        tvUserName = findViewById(R.id.tv_validate_user_name);
        tvPassword = findViewById(R.id.tv_validate_password);
    }

    private void initListener() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn();
            }
        });

        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        layoutForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgotPasswordDialog();
            }
        });
    }

    private void openForgotPasswordDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_forgot_password);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowArtributes = window.getAttributes();
        windowArtributes.gravity = Gravity.CENTER;
        window.setAttributes(windowArtributes);

        dialog.setCancelable(true);

        EditText edEmail = dialog.findViewById(R.id.edEmail);
        Button btnNoThank = dialog.findViewById(R.id.btnNoThank);
        Button btnSend = dialog.findViewById(R.id.btnSend);

        btnNoThank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onClickForgotPassword(edEmail.getText().toString().trim());
            }
        });
        dialog.show();
    }

    private void onClickForgotPassword(String email) {
        progressDialog.show();
        AccountService.accountService.forgotPassword(email)
                .enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            if(Boolean.TRUE.equals(response.body())){
                                Toast.makeText(SignInActivity.this, "Check Your Email!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignInActivity.this, "Unregistered email!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void onClickSignIn() {
        String strUserName = edUserName.getText().toString().trim();
        String strPassword = edPassword.getText().toString().trim();
        if (strUserName.length() == 0) {
            tvUserName.setVisibility(View.VISIBLE);
            tvUserName.setText("User name cannot be empty!");
            tvUserName.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else if (strPassword.length() == 0) {
            tvPassword.setVisibility(View.VISIBLE);
            tvPassword.setText("Password cannot be empty!");
            tvPassword.setTextColor(getResources().getColor(R.color.design_default_color_error));
        } else {
            progressDialog.show();
            AuthenticationRequest request = new AuthenticationRequest(strUserName, strPassword);
            AccountService.accountService.authenticate(request)
                    .enqueue(new Callback<AuthenticationResponse>() {
                        @Override
                        public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("data_intent", response.body());
                                intent.putExtras(bundle);
                                mActivityResultLauncher.launch(intent);
                                finishAffinity();
                            } else {
                                openLoginFailDialog();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(SignInActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void openLoginFailDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_login_fail);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowArtributes = window.getAttributes();
        windowArtributes.gravity = Gravity.CENTER;
        window.setAttributes(windowArtributes);

        dialog.setCancelable(true);

        Button btnOk = dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}