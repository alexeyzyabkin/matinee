package com.invizorys.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.invizorys.mobile.R;
import com.invizorys.mobile.api.MatineeService;
import com.invizorys.mobile.api.RetrofitCallback;
import com.invizorys.mobile.api.ServiceGenerator;
import com.invizorys.mobile.callback.SocialNetworkCallback;
import com.invizorys.mobile.model.User;
import com.invizorys.mobile.util.Settings;
import com.invizorys.mobile.util.social.VkSocialNetwork;
import com.letionik.matinee.Sex;
import com.letionik.matinee.UserDto;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

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

        matineeService = ServiceGenerator.createService(MatineeService.class, MatineeService.BASE_URL);

        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        Log.e("Vk", fingerprints[0]);

    }

    private void vkLogin() {
        VkSocialNetwork vkSocialNetwork = new VkSocialNetwork(this);
        vkSocialNetwork.login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
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
//                vkLogin();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onUserReceive(User user) {
        Settings.saveUser(this, user);

        matineeService.register(new UserDto(user.getSocialId(), user.getFirstName(),
                user.getLastName(), Sex.parseSex(user.getSex()),
                user.getAvatarUrl()), new RetrofitCallback<UserDto>(LoginActivity.this) {
            @Override
            public void success(UserDto userDto, Response response) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFriendsReceive(ArrayList<User> friends) {

    }
}

