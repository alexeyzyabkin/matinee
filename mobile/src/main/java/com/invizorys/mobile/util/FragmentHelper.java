package com.invizorys.mobile.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public class FragmentHelper {

    public static void add(FragmentManager fragmentManager, Fragment fragment, int container) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null).replace(container,
                fragment).commit();
    }

    public static boolean pop(FragmentManager fragmentManager) {
        fragmentManager.popBackStack();
        return getStackCount(fragmentManager) == 0;
    }

    public static int getStackCount(FragmentManager fragmentManager) {
        return fragmentManager.getBackStackEntryCount();
    }
}
