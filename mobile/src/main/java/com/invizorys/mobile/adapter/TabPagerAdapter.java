package com.invizorys.mobile.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.invizorys.mobile.R;
import com.invizorys.mobile.ui.fragment.event.FragmentHistory;
import com.invizorys.mobile.ui.fragment.event.FragmentMyTasks;
import com.invizorys.mobile.ui.fragment.event.FragmentParticipants;

/**
 * Created by Paryshkura Roman on 15.12.2015.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
    public static final int PAGE_COUNT = 3;
    private Context context;
    private FragmentMyTasks fragmentMyTasks;
    private FragmentParticipants fragmentParticipants;
    private FragmentHistory fragmentHistory;

    public TabPagerAdapter(FragmentManager fm, Context context, long currentEventId) {
        super(fm);
        this.context = context;
        fragmentMyTasks = FragmentMyTasks.newInstance(currentEventId);
        fragmentParticipants = FragmentParticipants.newInstance(currentEventId);
        fragmentHistory = FragmentHistory.newInstance(currentEventId);
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
