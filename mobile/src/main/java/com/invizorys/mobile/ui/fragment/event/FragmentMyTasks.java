package com.invizorys.mobile.ui.fragment.event;

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

import com.invizorys.mobile.R;
import com.invizorys.mobile.adapter.MyTaskRecyclerAdapter;
import com.invizorys.mobile.data.EventDataSource;
import com.invizorys.mobile.data.UserDataSource;
import com.invizorys.mobile.model.EventUpdated;
import com.invizorys.mobile.model.realm.Event;
import com.invizorys.mobile.model.realm.Participant;
import com.invizorys.mobile.model.realm.Task;
import com.invizorys.mobile.model.realm.TaskProgress;
import com.invizorys.mobile.model.realm.User;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.invizorys.mobile.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class FragmentMyTasks extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String EVENT_ID = "eventId";
    private RecyclerView recyclerView;
    private Event currentEvent;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EventDataSource eventDataSource;

    //see FragmentEvent newInstance() method description
    public static FragmentMyTasks newInstance(long currentEventId) {
        FragmentMyTasks fragmentMyTasks = new FragmentMyTasks();
        Bundle args = new Bundle();
        args.putLong(EVENT_ID, currentEventId);
        fragmentMyTasks.setArguments(args);
        return fragmentMyTasks;
    }

    public FragmentMyTasks() {
        // Required empty public constructor
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventDataSource = new EventDataSource(getActivity());
        if (getArguments() != null) {
            long currentEventId = getArguments().getLong(EVENT_ID);
            currentEvent = eventDataSource.getEventById(currentEventId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tasks, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_task);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.md_red_500));

        showTasks();

        return view;
    }

    private void showTasks() {
        UserDataSource userDataSource = new UserDataSource(getActivity());
        User currentUser = userDataSource.getUser();

        List<Participant> participants = currentEvent.getParticipants();
        for (Participant participant : participants) {
            if (participant.getUser().getSocialId().equals(currentUser.getSocialId())) {
                List<TaskProgress> taskProgresses = participant.getTasks();
                if (taskProgresses == null) {
                    return;
                }
                ArrayList<Task> tasks = new ArrayList<>();
                for (TaskProgress taskProgress : taskProgresses) {
                    Task task = taskProgress.getTask();
                    tasks.add(new Task(task.getName(), task.getDescription()));
                }
                recyclerView.setAdapter(new MyTaskRecyclerAdapter(tasks));
            }
        }
    }

    public void onEvent(EventUpdated eventUpdated) {
        if (eventUpdated.isSuccessful()) {
            currentEvent = eventDataSource.getEventById(eventUpdated.getEventId());
            showTasks();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        ((MainActivity) getActivity()).updateEvent(currentEvent.getId());
    }
}
