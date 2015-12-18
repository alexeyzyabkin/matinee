package com.invizorys.mobile.ui.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.invizorys.mobile.R;
import com.invizorys.mobile.ui.fragment.FragmentEvents;
import com.invizorys.mobile.ui.fragment.event.FragmentEvent;
import com.invizorys.mobile.model.UpdateParticipants;
import com.invizorys.mobile.model.User;
import com.invizorys.mobile.util.FragmentHelper;
import com.invizorys.mobile.util.Settings;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    public static final int FRAME_CONTAINER = R.id.activity_main_container;
    public static final String EVENTS = "Events";
    private Drawer drawerResult;
    private Toolbar toolbar;
    private User user;
    private FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentHelper.add(fragmentManager, FragmentEvent.newInstance(), FRAME_CONTAINER);

//        Long currentEventId = Settings.fetchCurrentEventId(this);
//        if (currentEventId > -1) {
//            FragmentHelper.add(fragmentManager, FragmentEventParticipants.newInstance(currentEventId), FRAME_CONTAINER);
//        } else {
//            FragmentHelper.add(fragmentManager, FragmentCreateFindEvent.newInstance(), FRAME_CONTAINER);
//        }
//        toolbar.setTitle(CREATE_EVENT);

        user = Settings.fetchUser(this);
        drawerInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            EventBus.getDefault().post(new UpdateParticipants());
        } else if (id == R.id.action_logout) {
            Settings.saveCurrentEventId(this, (long) -1);
        }

        return super.onOptionsItemSelected(item);
    }

    private void drawerInit() {
        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem().withName(user.getFirstName()
                + " " + user.getLastName()).withIcon(user.getAvatarUrl()).withEnabled(false);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(EVENTS);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("Feedback");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("About");

        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        profileDrawerItem, item1, item2, item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            switch (((Nameable) drawerItem).getName().toString()) {
                                case EVENTS:
                                    FragmentHelper.add(fragmentManager, FragmentEvents.newInstance(), FRAME_CONTAINER);
                                    toolbar.setTitle(EVENTS);
                                    break;
                            }
                        }
                        return false;
                    }
                }).build();
        drawerResult.setStatusBarColor(getResources().getColor(R.color.md_red_900));

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
            }

            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
                Log.e("MainActivity", "VKError: " + error.toString());
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }
}