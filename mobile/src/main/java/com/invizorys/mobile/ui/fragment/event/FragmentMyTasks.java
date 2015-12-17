package com.invizorys.mobile.ui.fragment.event;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.invizorys.mobile.R;

public class FragmentMyTasks extends Fragment {

    public static FragmentMyTasks newInstance() {
        return new FragmentMyTasks();
    }

    public FragmentMyTasks() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_tasks, container, false);
    }
}
