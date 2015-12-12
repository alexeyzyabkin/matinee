package com.invizorys.mobile.callback;

import com.invizorys.mobile.model.User;

import java.util.ArrayList;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public interface SocialNetworkCallback {
    void onUserReceive(User user);
    void onFriendsReceive(ArrayList<User> friends);
}
