package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class ProfileFragment extends Fragment {

    private View mView;
    private MainActivity mMainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        initUi();
        goToInforFragment();
        return mView;
    }

    private void initUi() {
        mMainActivity = (MainActivity) getActivity();
    }

    private void goToInforFragment() {
        FragmentTransaction fragmentTransaction = mMainActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_profile,new InforFragment());
        fragmentTransaction.addToBackStack(InforFragment.TAG);
        fragmentTransaction.commit();
    }
}