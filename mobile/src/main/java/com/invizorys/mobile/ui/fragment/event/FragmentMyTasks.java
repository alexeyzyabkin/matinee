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

import com.invizorys.mobile.R;
import com.invizorys.mobile.adapter.MyTaskRecyclerAdapter;
import com.invizorys.mobile.data.UserDataSource;
import com.invizorys.mobile.model.Event;
import com.invizorys.mobile.model.Participant;
import com.invizorys.mobile.model.Task;
import com.invizorys.mobile.model.User;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.letionik.matinee.TaskDto;
import com.letionik.matinee.TaskProgressDto;

import java.util.ArrayList;
import java.util.List;

public class FragmentMyTasks extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String EVENT = "event";
    private MatineeService matineeService;
    private RecyclerView recyclerView;
    private Event currentEvent;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static FragmentMyTasks newInstance(Event currentEvent) {
        FragmentMyTasks fragmentMyTasks = new FragmentMyTasks();
        Bundle args = new Bundle();
        args.putSerializable(EVENT, currentEvent);
        fragmentMyTasks.setArguments(args);
        return fragmentMyTasks;
    }

    public FragmentMyTasks() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentEvent = (Event) getArguments().getSerializable(EVENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        matineeService = ServiceGenerator.createService(MatineeService.class,
                MatineeService.BASE_URL, getActivity());

        View view = inflater.inflate(R.layout.fragment_my_tasks, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_task);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.md_red_500));

        showTasks();

        return view;
    }

    private void showTasks() {
        UserDataSource userDataSource = new UserDataSource(getActivity());
        User currentUser = userDataSource.getUser();

        List<Participant> participants = currentEvent.getParticipants();
        for (Participant participantDto : participants) {
            if (participantDto.getUser().getSocialId().equals(currentUser.getSocialId())) {
                List<TaskProgressDto> taskProgressDtos = participantDto.getTasks();
                if (taskProgressDtos == null) {
                    return;
                }
                ArrayList<Task> tasks = new ArrayList<>();
                for (TaskProgressDto taskProgressDto : taskProgressDtos) {
                    TaskDto taskDto = taskProgressDto.getTask();
                    tasks.add(new Task(taskDto.getName(), taskDto.getDescription()));
                }
                recyclerView.setAdapter(new MyTaskRecyclerAdapter(tasks));
            }
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 5000);
    }
}
