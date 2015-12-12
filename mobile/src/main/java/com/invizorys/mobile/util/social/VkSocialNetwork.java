package com.invizorys.mobile.util.social;

import android.app.Activity;

import com.invizorys.mobile.callback.SocialNetworkCallback;
import com.invizorys.mobile.model.User;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Paryshkura Roman on 12.12.2015.
 */
public class VkSocialNetwork {
    private Activity activity;
    private final String FIELD_PHOTO_NAME = "photo_200";
    private static final String[] scopeVk = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.MESSAGES
    };

    public VkSocialNetwork(Activity activity) {
        this.activity = activity;
    }

    public void login() {
        VKSdk.login(activity, scopeVk);
    }

    public void logout() {
        VKSdk.logout();
    }

    public boolean isConnected() {
        return VKSdk.isLoggedIn();
    }

    public void getUserData(final SocialNetworkCallback socialNetworkCallback) {

        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "id,first_name,last_name,sex,bdate," + FIELD_PHOTO_NAME
        ));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONObject jsonResponse = response.json.getJSONArray("response").getJSONObject(0);
                    User socialUser = getUserData(jsonResponse);
                    socialNetworkCallback.onUserReceive(socialUser);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }

    private User getUserData(JSONObject jsonResponse) throws JSONException {
        User socialUser = new User();
        if (jsonResponse.has("id")) {
            socialUser.setSocialId(jsonResponse.getString("id"));
        }
        if (jsonResponse.has("first_name")) {
            socialUser.setFirstName(jsonResponse.getString("first_name"));
        }
        if (jsonResponse.has("last_name")) {
            socialUser.setLastName(jsonResponse.getString("last_name"));
        }
        if (jsonResponse.has(FIELD_PHOTO_NAME)) {
            socialUser.setPhotoUrl(jsonResponse.getString(FIELD_PHOTO_NAME));
        }
        if (jsonResponse.has("sex")) {
            socialUser.setSex(jsonResponse.getString("sex"));
        }
        if (jsonResponse.has("bdate")) {
            socialUser.setBirthDate(jsonResponse.getString("bdate"));
        }
        return socialUser;
    }

    public void wallPost(String userId, String message) {
        VKRequest request;
        if (userId != null) {
            request = VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID, userId,
                    VKApiConst.MESSAGE, message));
        } else {
            request = VKApi.wall().post(VKParameters.from(VKApiConst.MESSAGE, message));
        }
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }

    public void getFriends(final SocialNetworkCallback socialNetworkCallback) {
        VKRequest request = new VKRequest("friends.get",
                VKParameters.from(VKApiConst.FIELDS, "id,first_name,last_name,sex," + FIELD_PHOTO_NAME));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONArray jsonResponse = response.json.getJSONObject("response").getJSONArray("items");
                    ArrayList<User> friends = new ArrayList<>();
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        friends.add(getUserData((JSONObject) jsonResponse.get(i)));
                    }
                    socialNetworkCallback.onFriendsReceive(friends);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }
}
