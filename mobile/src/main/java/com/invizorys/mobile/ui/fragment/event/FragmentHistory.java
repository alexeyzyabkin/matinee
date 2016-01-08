package com.invizorys.mobile.ui.fragment.event;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.invizorys.mobile.R;
import com.invizorys.mobile.model.Event;
import com.invizorys.mobile.model.HistoryRecord;
import com.invizorys.mobile.model.Participant;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.letionik.matinee.TaskProgressDto;

import java.util.List;

public class FragmentHistory extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeLayout;
    private MatineeService matineeService;
    private Event currentEvent;

    private static final String EVENT = "event";

    public static FragmentHistory newInstance(Event event) {
        FragmentHistory fragmentHistory = new FragmentHistory();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT, event);
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
            currentEvent = (Event) getArguments().getSerializable(EVENT);
        }

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.md_red_500));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_history);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        getHistory();

        return view;
    }

    private void getHistory() {
        List<Participant> participants = currentEvent.getParticipants();
        for (Participant participant : participants) {
            List<TaskProgressDto> taskProgressDtos = participant.getTasks();
            if (taskProgressDtos == null) {
                return;
            }
            for (TaskProgressDto taskProgressDto : taskProgressDtos) {
                HistoryRecord historyRecord = new HistoryRecord(participant.getUser().getName(),
                        taskProgressDto.getTask().getName(), taskProgressDto.getDoneDate());
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
