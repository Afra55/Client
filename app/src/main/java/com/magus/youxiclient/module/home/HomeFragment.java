package com.magus.youxiclient.module.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magus.youxiclient.R;
import com.magus.youxiclient.base.BaseFragment;

public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance(String param1, String param2) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, String message) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void onFragmentSeleted(boolean isFirst) {

    }

    @Override
    protected void onFragmentUnSeleted() {

    }

    @Override
    public void onClick(View v) {

    }
}
