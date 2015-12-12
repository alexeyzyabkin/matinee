package com.invizorys.mobile.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.invizorys.mobile.R;
import com.invizorys.mobile.model.User;
import com.invizorys.mobile.util.Settings;
import com.squareup.picasso.Picasso;

public class FragmentEvent extends Fragment {
    private User currentUser;
    private ImageView imageViewAvatar;
    private TextView textViewName;

    public static FragmentEvent newInstance() {
        return new FragmentEvent();
    }

    public FragmentEvent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = Settings.fetchUser(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        imageViewAvatar = (ImageView) view.findViewById(R.id.imageview_avatar);
        Picasso.with(getActivity()).load(currentUser.getAvatarUrl()).into(imageViewAvatar);

        textViewName = (TextView) view.findViewById(R.id.textview_name);
        String name = currentUser.getFirstName() + " " + currentUser.getLastName();
        textViewName.setText(name);

        return view;
    }
}
