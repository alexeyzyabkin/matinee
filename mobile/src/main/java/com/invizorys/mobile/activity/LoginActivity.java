package com.invizorys.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.invizorys.mobile.R;
import com.invizorys.mobile.util.social.VkSocialNetwork;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.button_login_vk).setOnClickListener(this);

//        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
//        Log.e("Vk", fingerprints[0]);

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
}

