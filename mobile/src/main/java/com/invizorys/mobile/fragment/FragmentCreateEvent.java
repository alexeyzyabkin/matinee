package com.invizorys.mobile.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.invizorys.mobile.R;

public class FragmentCreateEvent extends Fragment implements View.OnClickListener {

    public static FragmentCreateEvent newInstance() {
        return new FragmentCreateEvent();
    }

    public FragmentCreateEvent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        view.findViewById(R.id.button_create_event).setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {

    }
}
