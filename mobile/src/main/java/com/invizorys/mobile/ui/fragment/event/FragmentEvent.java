package com.invizorys.mobile.ui.fragment.event;

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

import com.invizorys.mobile.R;
import com.invizorys.mobile.adapter.TabPagerAdapter;
import com.invizorys.mobile.data.EventDataSource;
import com.invizorys.mobile.model.realm.Event;
import com.invizorys.mobile.ui.activity.MainActivity;
import com.invizorys.mobile.ui.fragment.FragmentEventSettings;
import com.invizorys.mobile.util.FragmentHelper;

public class FragmentEvent extends Fragment {
    private FragmentManager fragmentManager;
    private Event currentEvent;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManager = getActivity().getFragmentManager();

        if (getArguments() != null) {
            long currentEventId = getArguments().getLong(EVENT_ID);
            EventDataSource eventDataSource = new EventDataSource(getActivity());
            currentEvent = eventDataSource.getEventById(currentEventId);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(currentEvent.getName()
                    + "(" + currentEvent.getCode() + ")");
        }

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(TabPagerAdapter.PAGE_COUNT);
        viewPager.setAdapter(new TabPagerAdapter(getFragmentManager(), getActivity(), currentEvent.getId()));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tabAssignment = tabLayout.getTabAt(0);
        tabAssignment.setIcon(R.drawable.selector_assignment);
        TabLayout.Tab tabPeople = tabLayout.getTabAt(1);
        tabPeople.setIcon(R.drawable.selector_people);
        TabLayout.Tab tabHistory = tabLayout.getTabAt(2);
        tabHistory.setIcon(R.drawable.selector_history);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_event, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                FragmentHelper.add(fragmentManager, FragmentEventSettings.newInstance(),
                        MainActivity.FRAME_CONTAINER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
