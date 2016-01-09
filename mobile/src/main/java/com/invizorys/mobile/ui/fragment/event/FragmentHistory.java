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
import com.invizorys.mobile.model.HistoryRecord;
import com.invizorys.mobile.model.realm.Event;
import com.invizorys.mobile.model.realm.Participant;
import com.invizorys.mobile.model.realm.TaskProgress;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.ServiceGenerator;

import java.util.List;

public class FragmentHistory extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeLayout;
    private MatineeService matineeService;
    private Event currentEvent;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        matineeService = ServiceGenerator.createService(MatineeService.class,
                MatineeService.BASE_URL, getActivity());

        if (getArguments() != null) {
            long currentEventId = getArguments().getLong(EVENT_ID);
            EventDataSource eventDataSource = new EventDataSource(getActivity());
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
