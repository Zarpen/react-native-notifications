package com.wix.reactnativenotifications.core;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wix.reactnativenotifications.core.notification.PushNotificationProps;
import android.os.Build;

public class NotificationIntentAdapter {
    private static final String PUSH_NOTIFICATION_EXTRA_NAME = "pushNotification";

    public static PendingIntent createPendingNotificationIntent(Context appContext, Intent intent, PushNotificationProps notification) {
        intent.putExtra(PUSH_NOTIFICATION_EXTRA_NAME, notification.asBundle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            return PendingIntent.getService(appContext, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_MUTABLE);
        }else{
            return PendingIntent.getService(appContext, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT);
        }
    }

    public static Bundle extractPendingNotificationDataFromIntent(Intent intent) {
        return intent.getBundleExtra(PUSH_NOTIFICATION_EXTRA_NAME);
    }

    public static boolean canHandleIntent(Intent intent) {
        if (intent != null) {
            Bundle notificationData = intent.getExtras();
            if (notificationData != null &&
                    (intent.hasExtra(PUSH_NOTIFICATION_EXTRA_NAME) ||
                            notificationData.getString("google.message_id", null) != null)) {
                return true;
            }
        }

        return false;
    }
}
