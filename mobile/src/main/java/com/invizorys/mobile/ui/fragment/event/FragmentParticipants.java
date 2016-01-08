package com.invizorys.mobile.ui.fragment.event;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.invizorys.mobile.R;
import com.invizorys.mobile.adapter.ParticipantRecyclerAdapter;
import com.invizorys.mobile.data.EventDataSource;
import com.invizorys.mobile.data.UserDataSource;
import com.invizorys.mobile.model.realm.Event;
import com.invizorys.mobile.model.EventStatus;
import com.invizorys.mobile.model.realm.Participant;
import com.invizorys.mobile.model.realm.User;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.RetrofitCallback;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.TaskDto;
import com.letionik.matinee.TaskProgressDto;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentParticipants extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private User currentUser;
    private Button buttonShowRoles;
    private ImageView imageViewAvatar;
    private TextView textViewName, textViewEventCode;
    private MatineeService matineeService;
    private RecyclerView recyclerView;
    private ParticipantRecyclerAdapter adapter;
    private EventStatus eventStatus;
    private SwipeRefreshLayout swipeLayout;
    private Event currentEvent;

    private static final String EVENT_ID = "eventId";

    //see FragmentEvent newInstance() method description
    public static FragmentParticipants newInstance(long event) {
        FragmentParticipants fragment = new FragmentParticipants();
        Bundle args = new Bundle();
        args.putSerializable(EVENT_ID, event);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentParticipants() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserDataSource userDataSource = new UserDataSource(getActivity());
        currentUser = userDataSource.getUser();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_participants, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.md_red_500));

        if (getArguments() != null) {
            long currentEventId = getArguments().getLong(EVENT_ID);
            EventDataSource eventDataSource = new EventDataSource(getActivity());
            currentEvent = eventDataSource.getEventById(currentEventId);
        }

        matineeService = ServiceGenerator.createService(MatineeService.class,
                MatineeService.BASE_URL, getActivity());
        textViewEventCode = (TextView) view.findViewById(R.id.textview_event_code);
        buttonShowRoles = (Button) view.findViewById(R.id.button_show_roles);
        buttonShowRoles.setOnClickListener(this);

        imageViewAvatar = (ImageView) view.findViewById(R.id.imageview_avatar);
        Picasso.with(getActivity()).load(currentUser.getAvatarUrl()).into(imageViewAvatar);

        textViewName = (TextView) view.findViewById(R.id.textview_name);
        String name = currentUser.getName() + " " + currentUser.getSurname();
        textViewName.setText(name);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        initCurrentEvent();

        return view;
    }

    private void initCurrentEvent() {
        List<Participant> participants = currentEvent.getParticipants();
        adapter = new ParticipantRecyclerAdapter(getActivity(), participants);
        recyclerView.setAdapter(adapter);

        eventStatus = Event.getEventStatusEnum(currentEvent);
        //TODO admin = null???
        if (Event.getAdmin(currentEvent.getParticipants()) == null) {
            Toast.makeText(getActivity(), "admin not found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (eventStatus.equals(EventStatus.TASKS_REVEALED)) {
            buttonShowRoles.setVisibility(View.VISIBLE);
            buttonShowRoles.setText(getActivity().getString(R.string.show_tasks));
        } else if (Event.getAdmin(currentEvent.getParticipants()).getUser().getSocialId().equals(currentUser.getSocialId())) {
            buttonShowRoles.setVisibility(View.VISIBLE);
        }
        textViewEventCode.setText(getActivity().getString(R.string.event_code) + currentEvent.getCode());
    }

    private void revealRoles() {
        matineeService.revealRoles(currentEvent.getId(), new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {
                eventStatus = Event.getEventStatusEnum(currentEvent);
                if (eventStatus.equals(EventStatus.ROLES_REVEALED)) {
                    buttonShowRoles.setText(R.string.send_tasks);
                }
                List<Participant> participants = currentEvent.getParticipants();
                adapter.updateParticipants(participants);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void revealTasks() {
        matineeService.revealTasks(currentEvent.getId(), new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {
                eventStatus = Event.getEventStatusEnum(currentEvent);
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
                eventStatus = Event.getEventStatusEnum(currentEvent);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_show_roles:
                if (eventStatus.equals(EventStatus.NEW)) {
                    revealRoles();
                } else if (eventStatus.equals(EventStatus.ROLES_REVEALED)) {
                    revealTasks();
                } else if (eventStatus.equals(EventStatus.TASKS_REVEALED)) {
                    showTask(currentEvent.getId());
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 5000);
    }
}
