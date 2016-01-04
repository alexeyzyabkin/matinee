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
import com.invizorys.mobile.model.Event;
import com.invizorys.mobile.ui.activity.MainActivity;
import com.invizorys.mobile.util.FragmentHelper;

public class FragmentEvent extends Fragment {
    private FragmentManager fragmentManager;
    private Event currentEvent;

    private static final String EVENT = "event";

    public static FragmentEvent newInstance() {
        return new FragmentEvent();
    }

    public static FragmentEvent newInstance(Event event) {
        FragmentEvent fragmentEvent = new FragmentEvent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EVENT, event);
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
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New Year Party");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManager = getActivity().getFragmentManager();

        if (getArguments() != null) {
            currentEvent = (Event) getArguments().getSerializable(EVENT);
        }

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new TabPagerAdapter(getFragmentManager(), getActivity(), currentEvent));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

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

    public Event getCurrentEvent() {
        return currentEvent;
    }
}
