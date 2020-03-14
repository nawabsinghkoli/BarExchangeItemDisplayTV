package com.romiotech.barexchangeitemdisplaytv;

import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class ExampleNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {


    static String notificationID = "";

    @Override
    public void notificationReceived(OSNotification notification) {
        JSONObject data = notification.payload.additionalData;
         notificationID = notification.payload.notificationID;
        String title = notification.payload.title;
        String body = notification.payload.body;
        String smallIcon = notification.payload.smallIcon;
        String largeIcon = notification.payload.largeIcon;
        String bigPicture = notification.payload.bigPicture;
        String smallIconAccentColor = notification.payload.smallIconAccentColor;
        String sound = notification.payload.sound;
        String ledColor = notification.payload.ledColor;
        int lockScreenVisibility = notification.payload.lockScreenVisibility;
        String groupKey = notification.payload.groupKey;
        String groupMessage = notification.payload.groupMessage;
        String fromProjectNumber = notification.payload.fromProjectNumber;
        String rawPayload = notification.payload.rawPayload;

        String customKey;

        System.out.println("Received data: " + data);
        System.out.println("Received notificationID: " + notificationID);
        System.out.println("Received title: " + title);
        System.out.println("Received body: " + body);
        System.out.println("Received smallIcon: " + smallIcon);
        System.out.println("Received largeIcon: " + largeIcon);
        System.out.println("Received bigPicture: " + bigPicture);
        System.out.println("Received smallIconAccentColor: " + smallIconAccentColor);
        System.out.println("Received sound: " + sound);
        System.out.println("Received ledColor: " + ledColor);
        System.out.println("Received lockScreenVisibility: " + lockScreenVisibility);
        System.out.println("Received groupKey: " + groupKey);
        System.out.println("Received groupMessage: " + groupMessage);
        System.out.println("Received fromProjectNumber: " + fromProjectNumber);
        System.out.println("Received rawPayload: " + rawPayload);

        Log.i("OneSignalExample", "NotificationID received: " + notificationID);

        if (data != null) {
            customKey = data.optString("customkey", null);
            if (customKey != null)

                System.out.println("Received customKey: " + customKey);
                Log.i("OneSignalExample", "customkey set with value: " + customKey);
        }
    }
}
