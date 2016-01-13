package com.invizorys.mobile.ui.fragment.event;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.invizorys.mobile.R;
import com.invizorys.mobile.adapter.TabPagerAdapter;
import com.invizorys.mobile.data.EventDataSource;
import com.invizorys.mobile.model.EventUpdated;
import com.invizorys.mobile.model.realm.Event;
import com.invizorys.mobile.model.realm.User;
import com.invizorys.mobile.ui.activity.MainActivity;
import com.invizorys.mobile.ui.fragment.FragmentEventSettings;
import com.invizorys.mobile.util.FragmentHelper;

import de.greenrobot.event.EventBus;

public class FragmentEvent extends Fragment {
    private FragmentManager fragmentManager;
    private Event currentEvent;
    private EventDataSource eventDataSource;

    private static final String EVENT_ID = "eventId";

    //NotSerializableException: io.realm.RealmList
    //RealmList isn't serializable that's why need to send eventId instead event
    //Passing primary keys is probably the best option
    public static FragmentEvent newInstance(long eventId) {
        FragmentEvent fragmentEvent = new FragmentEvent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT_ID, eventId);
        fragmentEvent.setArguments(bundle);
        return fragmentEvent;
    }

    public FragmentEvent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        fragmentManager = getActivity().getFragmentManager();
        eventDataSource = new EventDataSource(getActivity());

        if (getArguments() != null) {
            long currentEventId = getArguments().getLong(EVENT_ID);
            EventDataSource eventDataSource = new EventDataSource(getActivity());
            currentEvent = eventDataSource.getEventById(currentEventId);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(currentEvent.getName());
        }

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(TabPagerAdapter.PAGE_COUNT);
        viewPager.setAdapter(new TabPagerAdapter(getFragmentManager(), getActivity(), currentEvent.getId()));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tabAssignment = tabLayout.getTabAt(TabPagerAdapter.MY_TASK_TAB);
        tabAssignment.setIcon(R.drawable.selector_assignment);
        TabLayout.Tab tabPeople = tabLayout.getTabAt(TabPagerAdapter.PARTICIPANTS_TAB);
        tabPeople.setIcon(R.drawable.selector_people);
        TabLayout.Tab tabHistory = tabLayout.getTabAt(TabPagerAdapter.HISTORY_TAB);
        tabHistory.setIcon(R.drawable.selector_history);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_event, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void showEventInfo() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_event_info);

        TextView textViewEventCode = (TextView) dialog.findViewById(R.id.textview_event_code);
        textViewEventCode.setText(getString(R.string.event_code, currentEvent.getCode()));
        TextView textViewEventAdmin = (TextView) dialog.findViewById(R.id.textview_event_admin);
        User admin = Event.getAdmin(currentEvent.getParticipants()).getUser();
        String adminFullName = admin.getName() + " " + admin.getSurname();
        textViewEventAdmin.setText(getString(R.string.event_admin, adminFullName));

        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                FragmentHelper.add(fragmentManager, FragmentEventSettings.newInstance(),
                        MainActivity.FRAME_CONTAINER);
                break;
            case R.id.action_info:
                showEventInfo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEvent(EventUpdated eventUpdated) {
        currentEvent = eventDataSource.getEventById(eventUpdated.getEventId());
    }
}
