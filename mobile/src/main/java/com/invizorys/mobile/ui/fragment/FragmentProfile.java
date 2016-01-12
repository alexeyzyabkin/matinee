package com.invizorys.mobile.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.invizorys.mobile.R;
import com.invizorys.mobile.model.realm.User;

public class FragmentProfile extends Fragment {
    private static final String USER = "user";
    private User user;

    //TODO Parcelable encountered IOException writing serializable object (name = io.realm.UserRealmProxy)
    public static FragmentProfile newInstance(User user) {
        FragmentProfile fragment = new FragmentProfile();
        Bundle args = new Bundle();
        args.putSerializable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(USER);
            String userFullName = user.getName() + " " + user.getSurname();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(userFullName);
        }

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
