package com.invizorys.mobile.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.invizorys.mobile.R;
import com.invizorys.mobile.model.Event;
import com.invizorys.mobile.ui.fragment.event.FragmentHistory;
import com.invizorys.mobile.ui.fragment.event.FragmentMyTasks;
import com.invizorys.mobile.ui.fragment.event.FragmentParticipants;

/**
 * Created by Paryshkura Roman on 15.12.2015.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private final int PAGE_COUNT = 3;
    private FragmentMyTasks fragmentMyTasks = FragmentMyTasks.newInstance();
    private FragmentParticipants fragmentParticipants;
    private FragmentHistory fragmentHistory = FragmentHistory.newInstance();

    public TabPagerAdapter(FragmentManager fm, Context context, Event event) {
        super(fm);
        this.context = context;
        fragmentParticipants = FragmentParticipants.newInstance(event);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragmentMyTasks;
            case 1:
                return fragmentParticipants;
            case 2:
                return fragmentHistory;
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.my_tasks);
            case 1:
                return context.getString(R.string.participants);
            case 2:
                return context.getString(R.string.history);
            default:
                return null;
        }
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
//    }
}
