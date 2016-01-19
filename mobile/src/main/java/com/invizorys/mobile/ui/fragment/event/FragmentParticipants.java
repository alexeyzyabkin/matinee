package com.invizorys.mobile.ui.fragment.event;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
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
import com.invizorys.mobile.model.EventUpdated;
import com.invizorys.mobile.model.realm.Event;
import com.invizorys.mobile.model.realm.Participant;
import com.invizorys.mobile.model.realm.User;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.RetrofitCallback;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.invizorys.mobile.ui.activity.MainActivity;
import com.letionik.matinee.EventDto;
import com.letionik.matinee.EventStatus;
import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.TaskDto;
import com.letionik.matinee.TaskProgressDto;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentParticipants extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private User currentUser;
    private Button buttonShowRoles;
    private MatineeService matineeService;
    private RecyclerView recyclerView;
    private ParticipantRecyclerAdapter adapter;
    private EventStatus eventStatus;
    private SwipeRefreshLayout swipeLayout;
    private Event currentEvent;
    private EventDataSource eventDataSource;

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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_participants, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.md_red_500));

        eventDataSource = new EventDataSource(getActivity());
        if (getArguments() != null) {
            long currentEventId = getArguments().getLong(EVENT_ID);
            currentEvent = eventDataSource.getEventById(currentEventId);
        }

        matineeService = ServiceGenerator.createService(MatineeService.class,
                MatineeService.BASE_URL, getActivity());
        buttonShowRoles = (Button) view.findViewById(R.id.button_show_roles);
        buttonShowRoles.setOnClickListener(this);

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
        Participant admin = Event.getAdmin(currentEvent.getParticipants());
        //TODO admin = null???
        if (admin == null) {
            Toast.makeText(getActivity(), "admin not found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentUser.getSocialId().equals(admin.getUser().getSocialId()) &&
                (currentEvent.getEventStatus().equals(EventStatus.NEW.toString()) ||
                        currentEvent.getEventStatus().equals(EventStatus.ROLES_REVEALED.toString()))) {
            buttonShowRoles.setVisibility(View.VISIBLE);
        }
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
                    buttonShowRoles.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    public void onEvent(EventUpdated eventUpdated) {
        if (eventUpdated.isSuccessful()) {
            currentEvent = eventDataSource.getEventById(eventUpdated.getEventId());
            initCurrentEvent();
        }
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_show_roles:
                if (eventStatus.equals(EventStatus.NEW)) {
                    revealRoles();
                } else if (eventStatus.equals(EventStatus.ROLES_REVEALED)) {
                    revealTasks();
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        ((MainActivity) getActivity()).updateEvent(currentEvent.getId());
    }
}
