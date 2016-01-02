package com.invizorys.mobile.data;

import android.content.Context;

import com.invizorys.mobile.model.User;

import io.realm.Realm;

/**
 * Created by Paryshkura Roman on 02.01.2016.
 */
public class UserDataSource {
    private Realm realm;

    public UserDataSource(Context context) {
        this.realm = Realm.getInstance(context);
    }

    public void saveUser(User user) {
        beginTransaction();
        realm.copyToRealmOrUpdate(user);
        endTransaction();
    }

    public User getUser() {
        return realm.where(User.class).findFirst();
    }

    public void beginTransaction() {
        realm.beginTransaction();
    }

    public void endTransaction() {
        realm.commitTransaction();
    }
}
