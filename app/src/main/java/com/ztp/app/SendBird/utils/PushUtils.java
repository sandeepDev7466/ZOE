package com.ztp.app.SendBird.utils;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sendbird.android.SendBird;

public class PushUtils {

    public static void registerPushTokenForCurrentUser(final Context context, SendBird.RegisterPushTokenWithStatusHandler handler,String newToken) {
        SendBird.registerPushTokenForCurrentUser(newToken, handler);
    }

    public static void unregisterPushTokenForCurrentUser(final Context context, SendBird.UnregisterPushTokenHandler handler,String newToken) {
        SendBird.unregisterPushTokenForCurrentUser(newToken, handler);
    }

}
