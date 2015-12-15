package com.invizorys.mobile.fragment.event;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.invizorys.mobile.R;

public class FragmentHistory extends Fragment {

    public static FragmentHistory newInstance() {
        return new FragmentHistory();
    }

    public FragmentHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }


}
