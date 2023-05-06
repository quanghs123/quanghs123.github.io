package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.DTO.AccountDTO;
import com.example.myapplication.service.AccountService;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAccFragment extends Fragment {
    public static final String TAG = UpdateAccFragment.class.getName();
    private View mView;
    private EditText edFullName, edEmail, edPhone;
    TextView tvFullName, tvEmail, tvPhone;
    private ImageView btnBack, btnUpdate;
    private MainActivity mMainActivity;
    private AccountDTO accountDTO;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_update_acc, container, false);
        initUi();
        initListener();
        return mView;
    }

    private void initUi() {
        edFullName = mView.findViewById(R.id.ed_full_name);
        edEmail = mView.findViewById(R.id.ed_email);
        edPhone = mView.findViewById(R.id.ed_phone);
        btnBack = mView.findViewById(R.id.btn_back);
        btnUpdate = mView.findViewById(R.id.btn_update);

        tvFullName = mView.findViewById(R.id.tv_validate_full_name);
        tvEmail = mView.findViewById(R.id.tv_validate_email);
        tvPhone = mView.findViewById(R.id.tv_validate_phone);

        mMainActivity = (MainActivity) getActivity();
        if (mMainActivity != null) {
            accountDTO = mMainActivity.getAccountDTO();
        }
        edFullName.setText(accountDTO.getFullName());
        edEmail.setText(accountDTO.getEmail());
        edPhone.setText(accountDTO.getPhone());

        progressDialog = new ProgressDialog(mMainActivity);
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

    private void onClickUpdate() {
        String strFullName = edFullName.getText().toString().trim();
        String strEmail = edEmail.getText().toString().trim();
        String strPhone = edPhone.getText().toString().trim();

        Pattern patternFullName = Pattern.compile("^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\W|_]+$");
        Pattern patternEmail = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Pattern patternPhone = Pattern.compile("^[0-9\\-\\+]{9,15}$");

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
        }else{
            progressDialog.show();
            accountDTO.setFullName(edFullName.getText().toString().trim());
            accountDTO.setEmail(edEmail.getText().toString().trim());
            accountDTO.setPhone(edPhone.getText().toString().trim());
            AccountService.accountService.update(accountDTO,accountDTO.getAccountID(),mMainActivity.getAuthorization())
                    .enqueue(new Callback<AccountDTO>() {
                        @Override
                        public void onResponse(Call<AccountDTO> call, Response<AccountDTO> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                onClickBack();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(mMainActivity, "Call Api Error",
                                        Toast.LENGTH_SHORT).show();
                                onClickBack();
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountDTO> call, Throwable t) {
                            progressDialog.show();
                            Toast.makeText(mMainActivity, "Call Api Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void onClickBack() {
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        }
    }
}