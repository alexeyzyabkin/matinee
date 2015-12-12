package com.invizorys.mobile.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.invizorys.mobile.R;
import com.invizorys.mobile.activity.MainActivity;
import com.invizorys.mobile.util.FragmentHelper;

public class FragmentCreateFindEvent extends Fragment implements View.OnClickListener {
    private FragmentManager fragmentManager;

    public static FragmentCreateFindEvent newInstance() {
        return new FragmentCreateFindEvent();
    }

    public FragmentCreateFindEvent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_find_event, container, false);
        view.findViewById(R.id.button_create_event).setOnClickListener(this);

        fragmentManager = getActivity().getFragmentManager();

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_create_event:
                FragmentHelper.add(fragmentManager, FragmentCreateEvent.newInstance(),
                        MainActivity.FRAME_CONTAINER);
                break;
        }
    }
}
