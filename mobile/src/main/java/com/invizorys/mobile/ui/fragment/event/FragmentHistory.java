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
import com.invizorys.mobile.data.EventDataSource;
import com.invizorys.mobile.model.EventUpdated;
import com.invizorys.mobile.model.HistoryRecord;
import com.invizorys.mobile.model.realm.Event;
import com.invizorys.mobile.model.realm.Participant;
import com.invizorys.mobile.model.realm.TaskProgress;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.invizorys.mobile.ui.activity.MainActivity;

import java.util.List;

import de.greenrobot.event.EventBus;

public class FragmentHistory extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeLayout;
    private Event currentEvent;
    private EventDataSource eventDataSource;

    private static final String EVENT_ID = "eventId";

    //see FragmentEvent newInstance() method description
    public static FragmentHistory newInstance(long eventId) {
        FragmentHistory fragmentHistory = new FragmentHistory();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT_ID, eventId);
        fragmentHistory.setArguments(bundle);
        return fragmentHistory;
    }

    public FragmentHistory() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        eventDataSource = new EventDataSource(getActivity());
        if (getArguments() != null) {
            long currentEventId = getArguments().getLong(EVENT_ID);
            currentEvent = eventDataSource.getEventById(currentEventId);
        }

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.md_red_500));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_history);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        getHistory();

        return view;
    }

    private void getHistory() {
        List<Participant> participants = currentEvent.getParticipants();
        for (Participant participant : participants) {
            List<TaskProgress> taskProgresses = participant.getTasks();
            if (taskProgresses == null) {
                return;
            }
            for (TaskProgress taskProgress : taskProgresses) {
                HistoryRecord historyRecord = new HistoryRecord(participant.getUser().getName(),
                        taskProgress.getTask().getName(), taskProgress.getDoneDate());
            }
        }
    }

    public void onEvent(EventUpdated eventUpdated) {
        if (eventUpdated.isSuccessful()) {
            currentEvent = eventDataSource.getEventById(eventUpdated.getEventId());
            getHistory();
        }
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        ((MainActivity) getActivity()).updateEvent(currentEvent.getId());
    }
}
