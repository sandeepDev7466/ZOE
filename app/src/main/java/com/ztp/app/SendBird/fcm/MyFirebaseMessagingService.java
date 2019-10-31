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

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.R;
import com.ztp.app.SendBird.utils.PreferenceUtils;
import com.ztp.app.Utils.Constants;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.View.Activity.Common.LoginActivity;
import com.ztp.app.View.Activity.Volunteer.VolunteerDashboardActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    SharedPref sharedPref;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
            sharedPref = SharedPref.getInstance(this);

            Log.d(TAG, "From: " + remoteMessage.getFrom());

            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }

            if (remoteMessage.getData() != null && remoteMessage.getData().get("sendbird") != null) {
                String channelUrl = null;
                try {
                    JSONObject sendBird = new JSONObject(remoteMessage.getData().get("sendbird"));
                    JSONObject channel = (JSONObject) sendBird.get("channel");
                    channelUrl = (String) channel.get("channel_url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (sharedPref.getPushNotification())
                    sendNotification(this, remoteMessage.getData().get("message"), channelUrl);

            } else {
                if (remoteMessage.getData().size() > 0) {
                    Map<String, String> params = remoteMessage.getData();
                    JSONObject dataJson = new JSONObject(params.get("data"));
                    sharedPref.setUnreadPushNotif(String.valueOf(Integer.parseInt(sharedPref.getUnreadPushNotif()) + 1));
                    if (sharedPref.getPushNotification())
                        sendNotificationOtherThanSendbird(this, dataJson.getString("title"), dataJson.getString("message"));
                } else {
                    sharedPref.setUnreadPushNotif(String.valueOf(Integer.parseInt(sharedPref.getUnreadPushNotif()) + 1));
                    if (sharedPref.getPushNotification())
                        sendNotificationOtherThanSendbird(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendNotification(Context context, String messageBody, String channelUrl) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        final String CHANNEL_ID = "CHANNEL_ID";
        if (Build.VERSION.SDK_INT >= 26) {  // Build.VERSION_CODES.O
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        Intent intent = null;
        SharedPref sharedPref = SharedPref.getInstance(context);
        if (!sharedPref.getUserType().equalsIgnoreCase(""))
            if (sharedPref.getUserType().equalsIgnoreCase("vol")) {
                intent = new Intent(context, VolunteerDashboardActivity.class);

            } else {
                intent = new Intent(context, CsoDashboardActivity.class);
            }
        else
            intent = new Intent(context, LoginActivity.class);
        intent.putExtra("groupChannelUrl", channelUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.thumbprints)
                .setColor(Color.parseColor("#7469C4"))  // small icon background color
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.thumbprints))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        if (PreferenceUtils.getNotificationsShowPreviews()) {
            notificationBuilder.setContentText(messageBody);
        } else {
            notificationBuilder.setContentText("Somebody sent you a message.");
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void sendNotificationOtherThanSendbird(Context context, String title, String body) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        final String CHANNEL_ID = "CHANNEL_ID";
        if (Build.VERSION.SDK_INT >= 26) {  // Build.VERSION_CODES.O
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        Intent intent = null;
        SharedPref sharedPref = SharedPref.getInstance(context);
        if (!sharedPref.getUserType().equalsIgnoreCase("")) {
            if (sharedPref.getUserType().equalsIgnoreCase("vol")) {
                intent = new Intent(context, VolunteerDashboardActivity.class);

            } else {
                intent = new Intent(context, CsoDashboardActivity.class);
            }
        } else
            intent = new Intent(context, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (body == null || body.equalsIgnoreCase("null"))
            body = "";
        if (title == null || title.equalsIgnoreCase("null"))
            title = "";

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.thumbprints)
                .setColor(Color.parseColor("#7469C4"))  // small icon background color
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.thumbprints))
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setContentText(body);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN", s);
    }
}