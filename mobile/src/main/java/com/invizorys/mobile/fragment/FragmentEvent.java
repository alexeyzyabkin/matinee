package com.invizorys.mobile.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.invizorys.mobile.R;
import com.invizorys.mobile.activity.MainActivity;
import com.invizorys.mobile.adapter.ParticipantRecyclerAdapter;
import com.invizorys.mobile.api.MatineeService;
import com.invizorys.mobile.api.RetrofitCallback;
import com.invizorys.mobile.api.ServiceGenerator;
import com.invizorys.mobile.model.UpdateParticipants;
import com.invizorys.mobile.model.User;
import com.invizorys.mobile.util.Settings;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.EventStatus;
import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.TaskDto;
import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.UserDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentEvent extends Fragment implements View.OnClickListener {
    private User currentUser;
    private Button buttonShowRoles;
    private ImageView imageViewAvatar;
    private TextView textViewName, textViewEventCode;
    private MatineeService matineeService;
    private Long eventId;
    private RecyclerView recyclerView;
    private ParticipantRecyclerAdapter adapter;
    private EventStatus eventStatus;

    private static final String EVENT_ID = "event_id";

    public static FragmentEvent newInstance(Long eventId) {
        FragmentEvent fragment = new FragmentEvent();
        Bundle args = new Bundle();
        args.putLong(EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentEvent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = Settings.fetchUser(getActivity());
        ((MainActivity) getActivity()).setToolbarTitle("New Year Party");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            eventId = getArguments().getLong(EVENT_ID);
        }

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        matineeService = ServiceGenerator.createService(MatineeService.class, MatineeService.BASE_URL);
        textViewEventCode = (TextView) view.findViewById(R.id.textview_event_code);
        buttonShowRoles = (Button) view.findViewById(R.id.button_show_roles);
        buttonShowRoles.setOnClickListener(this);

        imageViewAvatar = (ImageView) view.findViewById(R.id.imageview_avatar);
        Picasso.with(getActivity()).load(currentUser.getAvatarUrl()).into(imageViewAvatar);

        textViewName = (TextView) view.findViewById(R.id.textview_name);
        String name = currentUser.getFirstName() + " " + currentUser.getLastName();
        textViewName.setText(name);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        getCurrentEvent(eventId);

        return view;
    }

    private void getCurrentEvent(Long eventId) {
        matineeService.getCurrentEvent(eventId, new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {
                eventStatus = eventDto.getEventStatus();
                if (eventStatus.equals(EventStatus.TASKS_REVEALED)) {
                    buttonShowRoles.setVisibility(View.VISIBLE);
                    buttonShowRoles.setText(getActivity().getString(R.string.show_tasks));
                } else if (eventDto.getAdmin().getLogin().equals(currentUser.getSocialId())) {
                    buttonShowRoles.setVisibility(View.VISIBLE);
                }
                textViewEventCode.setText(getActivity().getString(R.string.event_code) + eventDto.getCode());

                ArrayList<User> participants = getParticipants(eventDto);
                adapter = new ParticipantRecyclerAdapter(getActivity(), participants);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void revealRoles() {
        matineeService.revealRoles(eventId, new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {
                eventStatus = eventDto.getEventStatus();
                if (eventStatus.equals(EventStatus.ROLES_REVEALED)) {
                    buttonShowRoles.setText(R.string.send_tasks);
                }
                ArrayList<User> participants = getParticipants(eventDto);
                adapter.updateParticipants(participants);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void revealTasks() {
        matineeService.revealTasks(eventId, new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {
                eventStatus = eventDto.getEventStatus();
                if (eventStatus.equals(EventStatus.TASKS_REVEALED)) {
                    buttonShowRoles.setText(R.string.show_tasks);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void showTask(Long eventId) {
        matineeService.getCurrentEvent(eventId, new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {
                eventStatus = eventDto.getEventStatus();
                List<ParticipantDto> participantDtos = eventDto.getParticipants();
                for (ParticipantDto participantDto : participantDtos) {
                    if (participantDto.getUser().getLogin().equals(currentUser.getSocialId())) {
                        List<TaskProgressDto> taskProgressDtos = participantDto.getTasks();
                        StringBuilder taskStringBuilder = new StringBuilder();
                        for (TaskProgressDto taskProgressDto : taskProgressDtos) {
                            TaskDto taskDto = taskProgressDto.getTask();
                            taskStringBuilder.append(taskDto.getName());
                            taskStringBuilder.append("\n");
                            taskStringBuilder.append(taskDto.getDescription());
                            taskStringBuilder.append("\n\n");
                        }
                        Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_tasks);
                        ((TextView) dialog.findViewById(R.id.textview_tasks)).setText(taskStringBuilder);
                        dialog.show();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private ArrayList<User> getParticipants(EventDto eventDto) {
        List<ParticipantDto> participantDtoList = eventDto.getParticipants();
        ArrayList<User> participants = new ArrayList<>();
        for (ParticipantDto participantDto : participantDtoList) {
            UserDto userDto = participantDto.getUser();
            User user = new User(userDto.getName(), userDto.getSurname(), userDto.getAvatarUrl());
            if (participantDto.getRole() != null) {
                user.setRole(participantDto.getRole().getName());
            }
            participants.add(user);
        }
        return participants;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_show_roles:
                if (eventStatus.equals(EventStatus.NEW)) {
                    revealRoles();
                } else if (eventStatus.equals(EventStatus.ROLES_REVEALED)) {
                    revealTasks();
                } else if (eventStatus.equals(EventStatus.TASKS_REVEALED)) {
                    showTask(eventId);
                }
                break;
        }
    }

    public void onEvent(UpdateParticipants updateParticipants) {
        getCurrentEvent(eventId);
    }
}
