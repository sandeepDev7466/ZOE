/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ztp.app.SendBird.fcm;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.InsertFirebaseIdRequest;
import com.ztp.app.Data.Remote.Model.Response.InsertFirebaseIdResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Viewmodel.InsertFirebaseIdViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    SharedPref sharedPref;
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sharedPref = SharedPref.getInstance(this);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        SendBird.registerPushTokenForCurrentUser(token, new SendBird.RegisterPushTokenWithStatusHandler() {
            @Override
            public void onRegistered(SendBird.PushTokenRegistrationStatus pushTokenRegistrationStatus, SendBirdException e) {
                if (e != null) {
                    Toast.makeText(MyFirebaseInstanceIDService.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pushTokenRegistrationStatus == SendBird.PushTokenRegistrationStatus.PENDING) {
                    //Toast.makeText(MyFirebaseInstanceIDService.this, "Connection required to register push token.", Toast.LENGTH_SHORT).show();
                }
            }
        });

       /* InsertFirebaseIdRequest insertFirebaseIdRequest = new InsertFirebaseIdRequest();
        insertFirebaseIdRequest.setUserDevice(FirebaseInstanceId.getInstance().getToken());
        insertFirebaseIdRequest.setUserEmail(sharedPref.getEmail());
        insertFirebaseIdRequest.setUserId(sharedPref.getUserId());
        insertFirebaseIdRequest.setUserType(sharedPref.getUserType());

        Call<InsertFirebaseIdResponse> call = Api.getClient().insertFirebaseId(insertFirebaseIdRequest);

        call.enqueue(new Callback<InsertFirebaseIdResponse>() {
            @Override
            public void onResponse(Call<InsertFirebaseIdResponse> call, Response<InsertFirebaseIdResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if(response.body().getResStatus().equalsIgnoreCase("200"))
                        Log.i("", "Firebase register successful");
                        else if(response.body().getResStatus().equalsIgnoreCase("401"))
                            Log.i("", "Firebase register failed");
                    }
                } else {
                   Log.i("", "Firebase register failed");
                }
            }

            @Override
            public void onFailure(Call<InsertFirebaseIdResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }
}