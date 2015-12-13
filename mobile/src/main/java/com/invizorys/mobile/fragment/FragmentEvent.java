package com.invizorys.mobile.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.invizorys.mobile.R;
import com.invizorys.mobile.adapter.ParticipantRecyclerAdapter;
import com.invizorys.mobile.api.MatineeService;
import com.invizorys.mobile.api.RetrofitCallback;
import com.invizorys.mobile.api.ServiceGenerator;
import com.invizorys.mobile.model.User;
import com.invizorys.mobile.util.Settings;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.UserDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentEvent extends Fragment implements View.OnClickListener {
    private User currentUser;
    private ImageView imageViewAvatar;
    private TextView textViewName, textViewEventCode;
    private MatineeService matineeService;
    private Long eventId;
    private RecyclerView recyclerView;

    private static final String EVENT_ID = "event_id";

    public static FragmentEvent newInstance(Long eventId) {
        FragmentEvent fragment = new FragmentEvent();
        Bundle args = new Bundle();
        args.putLong(EVENT_ID, eventId);
        fragment.setArguments(args);
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
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            eventId = getArguments().getLong(EVENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        matineeService = ServiceGenerator.createService(MatineeService.class, MatineeService.BASE_URL);
        textViewEventCode = (TextView) view.findViewById(R.id.textview_event_code);
        view.findViewById(R.id.button_show_roles).setOnClickListener(this);

        imageViewAvatar = (ImageView) view.findViewById(R.id.imageview_avatar);
        Picasso.with(getActivity()).load(currentUser.getAvatarUrl()).into(imageViewAvatar);

        textViewName = (TextView) view.findViewById(R.id.textview_name);
        String name = currentUser.getFirstName() + " " + currentUser.getLastName();
        textViewName.setText(name);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        getCurrentEvent();

        return view;
    }

    private void getCurrentEvent() {
        matineeService.getCurrentEvent(eventId, new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {
                textViewEventCode.setText("Код события:" + eventDto.getCode());

                List<ParticipantDto> participantDtoList = eventDto.getParticipants();
                ArrayList<User> participants = new ArrayList<>();
                for (ParticipantDto participantDto : participantDtoList) {
                    UserDto userDto = participantDto.getUser();
                    User user = new User(userDto.getName(), userDto.getSurname(), userDto.getAvatarUrl());
                    participants.add(user);
                }
                ParticipantRecyclerAdapter adapter = new ParticipantRecyclerAdapter(getActivity(), participants);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void showRoles() {
        matineeService.revealRoles(eventId, new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_show_roles:
                showRoles();
                break;
        }
    }
}
