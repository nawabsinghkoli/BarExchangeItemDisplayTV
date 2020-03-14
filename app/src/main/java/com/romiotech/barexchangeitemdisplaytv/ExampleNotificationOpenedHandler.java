package com.romiotech.barexchangeitemdisplaytv;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

// This fires when a notification is opened by tapping on it.
public class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler
{
    Context context;
    String userType;
    String pageCode, requestId, name,title,body;


    ExampleNotificationOpenedHandler(Context context) {
        this.context = context;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;
        String launchUrl = result.notification.payload.launchURL; // update docs launchUrl

       //  title = result.notification.payload.title;
       //  body = result.notification.payload.body;

        String customKey;
        String openURL = null;
        Object activityToLaunch = Received_Notification_Detail.class;

        System.out.println("Opened actionType: " + actionType);
        System.out.println("Opened additionalData: " + data);
        System.out.println("Opened launchUrl: " + launchUrl);
       // System.out.println("title: " + title);
       // System.out.println("body: " + body);

        if (data != null) {
            customKey = data.optString("customkey", null);
            openURL = data.optString("openURL", null);

            System.out.println("Opened customKey: " + customKey);
            System.out.println("Opened openURL: " + openURL);

            if (customKey != null)
                Log.e("OneSignalExample", "customkey set with value: " + customKey);

            if (openURL != null)
                Log.i("OneSignalExample", "openURL to webview with URL value: " + openURL);
        }


        try {
            title = data.getString("Title");
         //   requestId = data.getString("RequestId");
            body = data.getString("Body");

        //    Static_Variable.pageCode = pageCode;

            System.out.println("title : " + title);
            //System.out.println("requestId : " + requestId);
            System.out.println("body : " + body);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        if (actionType == OSNotificationAction.ActionType.ActionTaken) {

            System.out.println("Opened Button pressed with id: " + result.action.actionID);
            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);

            if (result.action.actionID.equals("1")) {
                System.out.println("Opened button id called: Check Detail : " + result.action.actionID);
                Log.i("OneSignalExample", "button id called: " + result.action.actionID);

                //if(pageCode.equals("LR")) {

                activityToLaunch = Received_Notification_Detail.class;

                Intent intent = new Intent(context, (Class<?>) activityToLaunch);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("openURL", openURL);
                Log.i("OneSignalExample", "openURL = " + openURL);
                context.startActivity(intent);
                //}

            }

            if (result.action.actionID.equals("2")) {
                System.out.println("Opened button id called: Confirm & Display Route : " + result.action.actionID);
                Log.i("OneSignalExample", "button id called: " + result.action.actionID);

                //*activityToLaunch = Home.class;

                Intent intent = new Intent(context, (Class<?>) activityToLaunch);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("openURL", openURL);
                Log.i("OneSignalExample", "openURL = " + openURL);
                context.startActivity(intent);//*
            }
        }

        // The following can be used to open an Activity of your choice.
        // Replace - getApplicationContext() - with any Android Context.
        // Intent intent = new Intent(getApplicationContext(), YourActivity.class);
        Intent intent = new Intent(context, (Class<?>) activityToLaunch);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("openURL", openURL);
      //  intent.putExtra("title", title);
     //   intent.putExtra("body", body);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        Log.i("OneSignalExample", "openURL = " + openURL);
        context.startActivity(intent);

    }
}
