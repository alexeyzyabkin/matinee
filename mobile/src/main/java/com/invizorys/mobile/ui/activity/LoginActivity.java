package com.invizorys.mobile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.invizorys.mobile.R;
import com.invizorys.mobile.callback.SocialNetworkCallback;
import com.invizorys.mobile.data.UserDataSource;
import com.invizorys.mobile.model.realm.User;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.RetrofitCallback;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.invizorys.mobile.util.Settings;
import com.invizorys.mobile.util.Utils;
import com.invizorys.mobile.util.social.VkSocialNetwork;
import com.letionik.matinee.UserDto;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, SocialNetworkCallback {
    private MatineeService matineeService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.button_login_vk).setOnClickListener(this);

        matineeService = ServiceGenerator.createService(MatineeService.class,
                MatineeService.BASE_URL, this);

//        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
//        Log.e("Vk", fingerprints[0]);
    }

    private void vkLogin() {
        if (Utils.isInternetAvailable()) {
            VkSocialNetwork vkSocialNetwork = new VkSocialNetwork(LoginActivity.this);
            vkSocialNetwork.login();
        } else {
            Toast.makeText(this, R.string.internet_is_unavailable, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                String accessToken = res.accessToken;
                Settings.saveToken(LoginActivity.this, accessToken);

                // User passed Authorization
                new VkSocialNetwork(LoginActivity.this).getUserData(LoginActivity.this);
            }

            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
                Log.e("LoginActivity", "VKError: " + error.toString());
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login_vk:
                vkLogin();
                break;
        }
    }

    @Override
    public void onUserReceive(User user) {
        UserDataSource userDataSource = new UserDataSource(this);
        userDataSource.saveUser(user);

        matineeService.register(new UserDto(user.getSocialId(), user.getName(),
                user.getSurname(), User.getSexEnum(user),
                user.getAvatarUrl()), new RetrofitCallback<UserDto>(LoginActivity.this) {
            @Override
            public void success(UserDto userDto, Response response) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    @Override
    public void onFriendsReceive(ArrayList<User> friends) {

    }
}

