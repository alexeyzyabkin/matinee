package com.invizorys.mobile.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.invizorys.mobile.R;
import com.invizorys.mobile.fragment.FragmentCreateEvent;
import com.invizorys.mobile.util.FragmentHelper;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainActivity extends AppCompatActivity {

    public static final int FRAME_CONTAINER = R.id.activity_main_container;
    public static final String CREATE_EVENT = "Create event";
    private Drawer drawerResult;
    private Toolbar toolbar;
    private FragmentCreateEvent fragmentCreateEvent;
    private FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentCreateEvent = FragmentCreateEvent.newInstance();
        FragmentHelper.add(fragmentManager, fragmentCreateEvent, FRAME_CONTAINER);
        toolbar.setTitle(CREATE_EVENT);

        drawerInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void drawerInit() {
        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem().withName("User Name");
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Create event");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("item2");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("item3");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName("item4");

        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        profileDrawerItem, item1, item2, item2, item3, item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            switch (((Nameable) drawerItem).getName().toString()) {
                                case CREATE_EVENT:
                                    FragmentHelper.add(fragmentManager, fragmentCreateEvent, FRAME_CONTAINER);
                                    toolbar.setTitle(CREATE_EVENT);
                                    break;
                            }
                        }
                        return false;
                    }
                }).build();
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
    public void onBackPressed(){
        if(drawerResult.isDrawerOpen()){
            drawerResult.closeDrawer();
        }
        else{
            super.onBackPressed();
        }
    }
}
