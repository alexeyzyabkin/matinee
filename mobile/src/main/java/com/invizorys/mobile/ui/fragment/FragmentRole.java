package com.invizorys.mobile.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.invizorys.mobile.R;

public class FragmentRole extends Fragment {

    public static FragmentRole newInstance() {
        return new FragmentRole();
    }

    public FragmentRole() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_role, container, false);
        return view;
    }
}
