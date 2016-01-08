package com.invizorys.mobile.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invizorys.mobile.R;
import com.invizorys.mobile.adapter.EventRecyclerAdapter;
import com.invizorys.mobile.data.EventDataSource;
import com.invizorys.mobile.model.Event;
import com.invizorys.mobile.model.EventsUpdated;
import com.invizorys.mobile.network.NetworkService;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.RetrofitCallback;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.invizorys.mobile.ui.activity.MainActivity;
import com.invizorys.mobile.ui.fragment.event.FragmentEvent;
import com.invizorys.mobile.util.FragmentHelper;
import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentEvents extends Fragment implements View.OnClickListener,
        EventRecyclerAdapter.EventListener, SwipeRefreshLayout.OnRefreshListener {
    private MatineeService matineeService;
    private FragmentManager fragmentManager;
    private RecyclerView recyclerViewEvents;
    private EventDataSource eventDataSource;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static FragmentEvents newInstance() {
        return new FragmentEvents();
    }

    public FragmentEvents() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.events);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        matineeService = ServiceGenerator.createService(MatineeService.class,
                MatineeService.BASE_URL, getActivity());
        eventDataSource = new EventDataSource(getActivity());

        View view = inflater.inflate(R.layout.fragment_events, container, false);
        view.findViewById(R.id.button_create_event).setOnClickListener(this);
        view.findViewById(R.id.button_find_event).setOnClickListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerViewEvents = (RecyclerView) view.findViewById(R.id.recyclerview_events);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewEvents.setLayoutManager(linearLayoutManager);

        fragmentManager = getActivity().getFragmentManager();

        loadEvents();
        getEvents();

        return view;
    }

    private void loadEvents() {
        Intent intent = new Intent(getActivity(), NetworkService.class);
        intent.putExtra(NetworkService.NETWORK_REQUEST, NetworkService.NetworkRequest.GET_EVENTS);
        getActivity().startService(intent);
    }

    private void createEvent(String eventName, Date startDate) {
        matineeService.createEvent(new CreateEventRequestDto(eventName, startDate), new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {
                if (eventDto.getId() == null) {
                    Toast.makeText(getActivity(), "eventDto null", Toast.LENGTH_SHORT).show();
                    return;
                }
                eventDataSource.beginTransaction();
                eventDataSource.saveEvent(eventDto);
                eventDataSource.endTransaction();
                FragmentHelper.add(fragmentManager, FragmentEvent.newInstance(eventDataSource.findEventById(eventDto.getId())),
                        MainActivity.FRAME_CONTAINER);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void enrollToEvent(String code) {
        matineeService.enroll(code, new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {
                String s = "d";
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void getEvents() {
        List<Event> events = eventDataSource.getAllEvents();
        EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(events, this);
        recyclerViewEvents.setAdapter(eventRecyclerAdapter);
    }

    //TODO implement handling empty fields
    public void showCreateEventDialog() {
        boolean wrapInScrollView = true;
        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .title("Новое событие")
                .customView(R.layout.dialog_create_event, wrapInScrollView)
                .positiveText("Создать")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        EditText editText = (EditText) materialDialog.findViewById(R.id.edittext_event_name);
                        MaterialCalendarView calendarView =
                                (MaterialCalendarView) materialDialog.findViewById(R.id.calendarView);
                        createEvent(editText.getText().toString(), calendarView.getSelectedDate().getDate());
                    }
                })
                .negativeText("Отмена")
                .show();
    }

    //TODO implement handling empty fields
    public void showEnrollToEventDialog() {
        boolean wrapInScrollView = true;
        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .title("Поиск события")
                .customView(R.layout.dialog_find_event, wrapInScrollView)
                .positiveText("Найти")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        EditText editText = (EditText) materialDialog.findViewById(R.id.edittext_event_code);
                        enrollToEvent(editText.getText().toString());
                    }
                })
                .negativeText("Отмена")
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_event:
                showCreateEventDialog();
                break;
            case R.id.button_find_event:
                showEnrollToEventDialog();
                break;
        }
    }

    public void onEvent(EventsUpdated eventsUpdated) {
        getEvents();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSelected(Event event) {
        FragmentHelper.add(fragmentManager, FragmentEvent.newInstance(event),
                MainActivity.FRAME_CONTAINER);
    }

    @Override
    public void onRefresh() {
        loadEvents();
    }
}
