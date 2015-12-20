package com.invizorys.mobile.ui.fragment.event;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.invizorys.mobile.R;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.ServiceGenerator;

public class FragmentMyTasks extends Fragment {
    private static final String EVENT_ID = "eventId";
    private MatineeService matineeService;
    private RecyclerView recyclerView;
    private Long eventId;

    public static FragmentMyTasks newInstance() {
        return new FragmentMyTasks();
    }

    public static FragmentMyTasks newInstance(Long eventId) {
        FragmentMyTasks fragmentMyTasks = new FragmentMyTasks();
        Bundle args = new Bundle();
        args.putLong(EVENT_ID, eventId);
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
            eventId = getArguments().getLong(EVENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        matineeService = ServiceGenerator.createService(MatineeService.class, MatineeService.BASE_URL);

        View view = inflater.inflate(R.layout.fragment_my_tasks, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_task);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

//        showTask(eventId);

        return view;
    }

//    private void showTask(Long eventId) {
//        matineeService.getCurrentEvent(eventId, new RetrofitCallback<EventDto>(getActivity()) {
//            @Override
//            public void success(EventDto eventDto, Response response) {
//                List<ParticipantDto> participantDtos = eventDto.getParticipants();
//                for (ParticipantDto participantDto : participantDtos) {
//                    if (participantDto.getUser().getLogin().equals(currentUser.getSocialId())) {
//                        List<TaskProgressDto> taskProgressDtos = participantDto.getTasks();
//                        StringBuilder taskStringBuilder = new StringBuilder();
//                        for (TaskProgressDto taskProgressDto : taskProgressDtos) {
//                            TaskDto taskDto = taskProgressDto.getTask();
//                            taskStringBuilder.append(taskDto.getName());
//                            taskStringBuilder.append("\n");
//                            taskStringBuilder.append(taskDto.getDescription());
//                            taskStringBuilder.append("\n\n");
//                        }
//                        Dialog dialog = new Dialog(getActivity());
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog.setContentView(R.layout.dialog_tasks);
//                        ((TextView) dialog.findViewById(R.id.textview_tasks)).setText(taskStringBuilder);
//                        dialog.show();
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                super.failure(error);
//            }
//        });
//    }
}
